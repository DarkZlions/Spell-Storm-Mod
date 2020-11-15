package ch.darklions888.SpellStorm.objects.containers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.objects.items.IHasMagic;
import ch.darklions888.SpellStorm.objects.items.IInfusable;
import ch.darklions888.SpellStorm.objects.items.IStoreMana;
import ch.darklions888.SpellStorm.registries.BlockInit;
import ch.darklions888.SpellStorm.registries.ContainerTypesInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;

public class ManaInfuserContainer extends Container {
	private final IInventory outputSlots = new CraftResultInventory();
	private final IInventory inputSlots = new Inventory(2) {
		public void markDirty() {
			super.markDirty();
			ManaInfuserContainer.this.onCraftMatrixChanged(this);
		}
	};

	private final IWorldPosCallable worldPosCallable;
	private int itemCost; // How much item it need to fully charge the page
	private Map<MagicSource, Integer> manaCost; // How much mana i need to take away from the container-item

	public ManaInfuserContainer(ContainerType<?> type, int id, PlayerInventory inventoryIn,
			final IWorldPosCallable worldPosCallableIn) {
		super(type, id);

		this.worldPosCallable = worldPosCallableIn;
		this.addSlot(new Slot(this.inputSlots, 0, 58, 51) {
			public boolean isItemValid(ItemStack stack) {
				if (stack.getItem() instanceof IStoreMana || stack.getItem() instanceof IInfusable) {
					return true;
				} else {
					return false;
				}
			}
		});

		this.addSlot(new Slot(this.inputSlots, 1, 102, 51) {
			public boolean isItemValid(ItemStack stack) {
				if (stack.getItem() instanceof IHasMagic || stack.getItem() instanceof IStoreMana) {
					return true;
				} else {
					return false;
				}
			}
		});

		this.addSlot(new Slot(this.outputSlots, 2, 80, 10) {
			public boolean isItemValid(ItemStack stack) {
				return false;
			}

			public boolean canTakeStack(PlayerEntity player) {
				return true;
			}

			public ItemStack onTake(PlayerEntity playerIn, ItemStack stack) {

				if (ManaInfuserContainer.this.inputSlots.getStackInSlot(1).isEmpty()) {
					ManaInfuserContainer.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);
					ManaInfuserContainer.this.inputSlots.setInventorySlotContents(1, ItemStack.EMPTY);
				} else {
					ItemStack storeManaStack = ManaInfuserContainer.this.inputSlots.getStackInSlot(1);
					ItemStack chargeableStack = ManaInfuserContainer.this.inputSlots.getStackInSlot(0);
					
					if (storeManaStack.getItem() instanceof IHasMagic && chargeableStack.getItem() instanceof IStoreMana) {
						int slotCount = storeManaStack.getCount();

						storeManaStack.setCount(slotCount - itemCost);
						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(1, storeManaStack);

						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);
					} else if (storeManaStack.getItem() instanceof IStoreMana && chargeableStack.getItem() instanceof IStoreMana) {
						IStoreMana container = (IStoreMana) storeManaStack.getItem();
						ItemStack sm = ManaInfuserContainer.this.inputSlots.getStackInSlot(0);
						IStoreMana storeMana = (IStoreMana) sm.getItem();

						storeMana.getMagicSourceList(storeManaStack).forEach((ms) -> {
							if (container.hasMagicSource(storeManaStack, ms))
								container.addManaValue(storeManaStack, ms.getId(), -manaCost.get(ms));
						});

						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(1, storeManaStack);
						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);
						
					} else if (storeManaStack.getItem() instanceof IStoreMana && chargeableStack.getItem() instanceof IInfusable) {
						IInfusable infusableItem = (IInfusable) chargeableStack.getItem();
						IStoreMana storeMana = (IStoreMana) storeManaStack.getItem();
						
						if (storeMana.hasMagicSource(storeManaStack, infusableItem.getMagicSource())) {
							storeMana.addManaValue(storeManaStack, infusableItem.getMagicSource().getKey(), -manaCost.get(infusableItem.getMagicSource()));
						}
						
						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(1, storeManaStack);
						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);
						
					} else if (storeManaStack.getItem() instanceof IHasMagic && chargeableStack.getItem() instanceof IInfusable) {
						int slotCount = storeManaStack.getCount();

						storeManaStack.setCount(slotCount - itemCost);
						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(1, storeManaStack);

						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);
					}
					
					if (chargeableStack.getItem() instanceof IStoreMana) {
						IStoreMana storeItem = (IStoreMana) chargeableStack.getItem();
						if (storeItem.canChangeToEmpty(chargeableStack)) {
							ManaInfuserContainer.this.inputSlots.setInventorySlotContents(1, storeItem.getChangedEmptyItem(chargeableStack));
						}
					}
					if (storeManaStack.getItem() instanceof IStoreMana) {
						IStoreMana storeItem = (IStoreMana) storeManaStack.getItem();
						if (storeItem.canChangeToEmpty(storeManaStack)) {
							ManaInfuserContainer.this.inputSlots.setInventorySlotContents(1, storeItem.getChangedEmptyItem(storeManaStack));
						}
					}
				}
				
				itemCost = 0;
				ManaInfuserContainer.this.outputSlots.setInventorySlotContents(2, ItemStack.EMPTY);
				return stack;
			}
		});

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlot(new Slot(inventoryIn, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			this.addSlot(new Slot(inventoryIn, k, 8 + k * 18, 142));
		}

		manaCost = new HashMap<>();
	}

	public ManaInfuserContainer(ContainerType<?> containerType, int windowId, PlayerInventory playerInventory) {
		this(containerType, windowId, playerInventory, IWorldPosCallable.DUMMY);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		if (inventoryIn == this.inputSlots) {
			this.updateOutPut();
		}
		super.onCraftMatrixChanged(inventoryIn);
	}

	public void updateOutPut() {
		try {
			if (manaCost == null) manaCost = new HashMap<>();
			this.manaCost.clear();
			ItemStack chargeableStack = this.inputSlots.getStackInSlot(0);
			ItemStack storeManaStack = this.inputSlots.getStackInSlot(1);
			
			if (!(chargeableStack.getItem() instanceof IStoreMana) || !(storeManaStack.getItem() instanceof IStoreMana)) {
				this.outputSlots.setInventorySlotContents(2, ItemStack.EMPTY);
				this.detectAndSendChanges();
			} else if (!(chargeableStack.getItem() instanceof IStoreMana) || !(storeManaStack.getItem() instanceof IHasMagic)) {
				this.outputSlots.setInventorySlotContents(2, ItemStack.EMPTY);
				this.detectAndSendChanges();
			} else if (!(chargeableStack.getItem() instanceof IInfusable) || !(storeManaStack.getItem() instanceof IHasMagic)) {
				this.outputSlots.setInventorySlotContents(2, ItemStack.EMPTY);
				this.detectAndSendChanges();
			}
			
			if (chargeableStack.getItem() instanceof IStoreMana && storeManaStack.getItem() instanceof IStoreMana) {
				IStoreMana infusableItem = (IStoreMana) chargeableStack.getItem();
				IStoreMana storeManaItem = (IStoreMana) storeManaStack.getItem();
				
				if (!infusableItem.canInfuse(chargeableStack, storeManaStack)) {
					this.outputSlots.setInventorySlotContents(2, ItemStack.EMPTY);
					this.detectAndSendChanges();
				}
				
				List<MagicSource> storeManaSourceList = storeManaItem.getMagicSourceList(storeManaStack);
				ItemStack infusableStackCopy = chargeableStack.copy();
				for (MagicSource magicSource : storeManaSourceList) {
					
					if (infusableItem.hasMagicSource(chargeableStack, magicSource)) {
						int storeManaValue = storeManaItem.getManaValue(storeManaStack, magicSource.getId());
						int infusableManaValue = infusableItem.getManaValue(chargeableStack, magicSource.getId());
						int fillAmount = infusableItem.getMaxMana(chargeableStack) - infusableManaValue;
						if (storeManaValue >= fillAmount) {

							((IStoreMana)infusableStackCopy.getItem()).addManaValue(infusableStackCopy, magicSource.getId(), fillAmount);
							
							this.manaCost.put(magicSource, new Integer(fillAmount));
						}
						else {
							((IStoreMana)infusableStackCopy.getItem()).addManaValue(infusableStackCopy, magicSource.getId(), storeManaValue);
							this.manaCost.put(magicSource, new Integer(storeManaItem.getManaValue(storeManaStack, magicSource.getId())));
						}
					}
				}			
				this.outputSlots.setInventorySlotContents(2, infusableStackCopy);
				this.detectAndSendChanges();
				
			} else if (chargeableStack.getItem() instanceof IStoreMana && storeManaStack.getItem() instanceof IHasMagic) {
				IStoreMana infusableItem = (IStoreMana) chargeableStack.getItem();
				IHasMagic storeManaItem = (IHasMagic) storeManaStack.getItem();
				MagicSource storeManaSource = storeManaItem.getMagicSource(storeManaStack);
				
				if (infusableItem.hasMagicSource(chargeableStack, storeManaSource)) {
					int infusableManaValue = infusableItem.getManaValue(chargeableStack, storeManaSource.getId());
					int containerSize = infusableItem.getMaxMana(chargeableStack);
					int fillAmount = containerSize - infusableManaValue;
					int decrAmount = fillAmount / storeManaItem.getManaPower(storeManaStack).mana;
					
					if (decrAmount <= storeManaStack.getCount()) {
						ItemStack infusableStackCopy = chargeableStack.copy();
						((IStoreMana)infusableStackCopy.getItem()).addManaValue(infusableStackCopy, storeManaSource.getId(), storeManaItem.getManaPower(storeManaStack).mana * decrAmount);
						this.itemCost = decrAmount;
						this.outputSlots.setInventorySlotContents(2, infusableStackCopy);
						this.detectAndSendChanges();
					} else {
						ItemStack infusableStackCopy = chargeableStack.copy();
						((IStoreMana)infusableStackCopy.getItem()).addManaValue(infusableStackCopy, storeManaSource.getId(), storeManaItem.getManaPower(storeManaStack).mana * storeManaStack.getCount());
						this.itemCost = storeManaStack.getCount();
						this.outputSlots.setInventorySlotContents(2, infusableStackCopy);
						this.detectAndSendChanges();
					}
				}
			} else if (chargeableStack.getItem() instanceof IInfusable && storeManaStack.getItem() != null && !storeManaStack.isEmpty()) {
				IInfusable infusableItem = (IInfusable) chargeableStack.getItem();
				
				if (storeManaStack.getItem() instanceof IStoreMana) {
					IStoreMana storeManaItem = (IStoreMana) storeManaStack.getItem();
					List<MagicSource> sourceList = storeManaItem.getMagicSourceList(storeManaStack);
					
					int numberOfEmptySources = 0;
					for (MagicSource ms : sourceList) {
						if (storeManaItem.getManaValue(storeManaStack, ms.getKey()) <= 0) {
							numberOfEmptySources++;
						}
					}
					
					if (numberOfEmptySources == sourceList.size()) {
						this.outputSlots.setInventorySlotContents(2, ItemStack.EMPTY);
						this.detectAndSendChanges();
					}
					
					ItemStack outputStack = infusableItem.getOutputItemStack(chargeableStack, storeManaStack);
					
					if (outputStack == chargeableStack) {
						this.outputSlots.setInventorySlotContents(2, ItemStack.EMPTY);
						this.detectAndSendChanges();
					}
					
					int manaValueFromSource = storeManaItem.getManaValue(storeManaStack, ((IInfusable)outputStack.getItem()).getMagicSource().getId());
					
					if (infusableItem.getInfusionCost() <= manaValueFromSource) {
						this.manaCost.put(infusableItem.getMagicSource(), manaValueFromSource);
						this.outputSlots.setInventorySlotContents(2, outputStack.copy());
						this.detectAndSendChanges();
						
					} else {
						this.outputSlots.setInventorySlotContents(2, ItemStack.EMPTY);
						this.detectAndSendChanges();
					}
					
				} else if (storeManaStack.getItem() instanceof IHasMagic) {
					IHasMagic magicItem = (IHasMagic) storeManaStack.getItem();
					int infusionCost = infusableItem.getInfusionCost();
					int decrAmount = (infusionCost / magicItem.getManaPower(storeManaStack).mana);
					decrAmount = decrAmount < 1 ? 1 : decrAmount;
					
					LogManager.getLogger().debug(decrAmount);
					
					if (decrAmount <= storeManaStack.getCount()) {
						this.itemCost = decrAmount;
						this.outputSlots.setInventorySlotContents(2, infusableItem.getOutputItemStack(chargeableStack, storeManaStack).copy());
						this.detectAndSendChanges();
					} else {
						this.itemCost = storeManaStack.getCount();
						this.outputSlots.setInventorySlotContents(2, infusableItem.getOutputItemStack(chargeableStack, storeManaStack).copy());
						this.detectAndSendChanges();
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		this.worldPosCallable.consume((world, pos) -> {
			this.clearContainer(playerIn, world, this.inputSlots);
		});
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(this.worldPosCallable, playerIn, BlockInit.MANAINFUSER.get());
	}

	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index == 2) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index != 0 && index != 1) {
				if (index >= 3 && index < 39 && !this.mergeItemStack(itemstack1, 0, 2, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	public static ManaInfuserContainer create(int windowId, PlayerInventory playerInventory) {
		return new ManaInfuserContainer((ContainerType<?>) ContainerTypesInit.MANA_INFUSER.get(), windowId,
				playerInventory);
	}
}
