package ch.darklions888.SpellStorm.objects.containers;

import java.util.Random;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.objects.items.IHasMagic;
import ch.darklions888.SpellStorm.objects.items.IMagicalContainer;
import ch.darklions888.SpellStorm.objects.items.IMagicalPageItem;
import ch.darklions888.SpellStorm.objects.items.pages.MagicalInkItem;
import ch.darklions888.SpellStorm.registries.BlockInit;
import ch.darklions888.SpellStorm.registries.ContainerTypesInit;
import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
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
	private int manaCost; // How much mana i need to take away from the container-item

	public ManaInfuserContainer(ContainerType<?> type, int id, PlayerInventory inventoryIn,
			final IWorldPosCallable worldPosCallableIn) {
		super(type, id);

		this.worldPosCallable = worldPosCallableIn;
		this.addSlot(new Slot(this.inputSlots, 0, 58, 51) {
			public boolean isItemValid(ItemStack stack) {
				if (stack.getItem() instanceof IMagicalPageItem || stack.getItem() instanceof IMagicalContainer || stack.getItem() instanceof MagicalInkItem) {
					return true;
				} else {
					return false;
				}
			}
		});

		this.addSlot(new Slot(this.inputSlots, 1, 102, 51) {
			public boolean isItemValid(ItemStack stack) {
				if (stack.getItem() instanceof IHasMagic || stack.getItem() instanceof IMagicalContainer) {
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
					ItemStack input = ManaInfuserContainer.this.inputSlots.getStackInSlot(1);
					ItemStack input2 = ManaInfuserContainer.this.inputSlots.getStackInSlot(0);
					if (input.getItem() instanceof IHasMagic) {
						int slotCount = input.getCount();

						input.setCount(slotCount - itemCost);
						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(1, input);

						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);
					} else if (input.getItem() instanceof IMagicalContainer && input2.getItem() instanceof IMagicalPageItem) {
						IMagicalContainer container = (IMagicalContainer) input.getItem();
						IMagicalPageItem page = (IMagicalPageItem) ManaInfuserContainer.this.inputSlots
								.getStackInSlot(0).getItem();

						container.addManaValue(input, page.magicSource().sourceId, -manaCost);

						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(1, input);

						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);
						
					} else if (input2.getItem() instanceof MagicalInkItem && input.getItem() instanceof IHasMagic) {
						
						int slotCount = input.getCount();
						input.setCount(slotCount -itemCost);
						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);
						
					} else if (input.getItem() instanceof IMagicalContainer && stack.getItem() instanceof MagicalInkItem) {
						IMagicalContainer container = (IMagicalContainer) input.getItem();
						MagicalInkItem ink = (MagicalInkItem) stack.getItem();

						container.addManaValue(input, ink.getMagicSource().sourceId, -manaCost);

						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(1, input);

						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);
					} 
				}

				itemCost = 0;
				manaCost = 0;
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
			ItemStack infusedStack = this.inputSlots.getStackInSlot(0);
			ItemStack manaStack = this.inputSlots.getStackInSlot(1);

			if (infusedStack.getItem() instanceof IMagicalPageItem && manaStack.getItem() instanceof IHasMagic) {

				ItemStack stack1 = infusedStack.copy();

				IMagicalPageItem page = (IMagicalPageItem) infusedStack.getItem();
				IHasMagic base = (IHasMagic) manaStack.getItem();

				if (page.canReceiveManaFromtItem(infusedStack, manaStack)) {
					int manaIn = page.getMana(infusedStack);
					int containerSize = page.getMaxContainerSize(infusedStack);
					int fillSize = containerSize - manaIn;
					int cost = fillSize / base.getManaPower().mana;

					if (cost <= manaStack.getCount()) {
						this.itemCost = cost;
						page.addMana(stack1, base.getManaPower().mana * cost);
						this.outputSlots.setInventorySlotContents(2, stack1);
						this.detectAndSendChanges();
					} else {
						this.itemCost = manaStack.getCount();
						page.addMana(stack1, base.getManaPower().mana * manaStack.getCount());
						this.outputSlots.setInventorySlotContents(2, stack1);
						this.detectAndSendChanges();
					}
				}
			} else if (infusedStack.getItem() instanceof IMagicalPageItem && manaStack.getItem() instanceof IMagicalContainer) {

				ItemStack stack1 = infusedStack.copy();

				IMagicalPageItem page = (IMagicalPageItem) infusedStack.getItem();
				IMagicalContainer container = (IMagicalContainer) manaStack.getItem();

				if (page.canReceiveManaFromtItem(infusedStack, manaStack)) {
					String sourceId = page.magicSource().sourceId;
					int fillSize = page.getMaxContainerSize(infusedStack) - page.getMana(infusedStack);

					if (container.getManaValue(manaStack, sourceId) >= fillSize) {
						this.manaCost = fillSize;
						page.addMana(stack1, manaCost);
						this.outputSlots.setInventorySlotContents(2, stack1);
						this.detectAndSendChanges();
					} else {
						this.manaCost = container.getManaValue(manaStack, sourceId);
						page.addMana(stack1, manaCost);
						this.outputSlots.setInventorySlotContents(2, stack1);
						this.detectAndSendChanges();
					}
				}
			} else if (infusedStack.getItem() instanceof IMagicalContainer && manaStack.getItem() instanceof IHasMagic) {
				ItemStack stack1 = infusedStack.copy();

				IMagicalContainer container = (IMagicalContainer) infusedStack.getItem();
				IHasMagic item = (IHasMagic) manaStack.getItem();

				if (container.hasMagicSource(item.getMagicSource())) {
					int manaIn = container.getManaValue(infusedStack, item.getMagicSource().sourceId);
					int containerSize = container.getContainerSize();
					int fillSize = containerSize - manaIn;
					int cost = fillSize / item.getManaPower().mana;

					if (cost <= manaStack.getCount()) {
						this.itemCost = cost;
						container.addManaValue(stack1, item.getMagicSource().sourceId, item.getManaPower().mana * cost);
						this.outputSlots.setInventorySlotContents(2, stack1);
						this.detectAndSendChanges();
					} else {
						this.itemCost = manaStack.getCount();
						container.addManaValue(stack1, item.getMagicSource().sourceId,
								item.getManaPower().mana * manaStack.getCount());
						this.outputSlots.setInventorySlotContents(2, stack1);
						this.detectAndSendChanges();
					}
				}
			} else if (infusedStack.getItem() instanceof MagicalInkItem && (manaStack.getItem() instanceof IHasMagic || manaStack.getItem() instanceof IMagicalContainer)) {
				
				Item magicItem = manaStack.getItem();
				
				if (magicItem instanceof IMagicalContainer) {
					MagicSource[] sources = ((IMagicalContainer)magicItem).getMagigSource();

					Random rand = new Random();
					int randSource = rand.nextInt(sources.length);
					MagicSource source = sources[randSource];
					int manaIn = ((IMagicalContainer)magicItem).getManaValue(manaStack, source.sourceId);
				
					if (manaIn >= 5) {
						this.manaCost = 5;
			
						if (source == MagicSource.DARKMAGIC) {
							this.outputSlots.setInventorySlotContents(2, new ItemStack(ItemInit.MAGICAL_INK_DARK.get()));
						} else if (source == MagicSource.LIGHTMAGIC) {
							this.outputSlots.setInventorySlotContents(2, new ItemStack(ItemInit.MAGICAL_INK_LIGHT.get()));
						} else if (source == MagicSource.NEUTRALMAGIC) {
							this.outputSlots.setInventorySlotContents(2, new ItemStack(ItemInit.MAGICAL_INK_NEUTRAL.get()));
						} else if (source == MagicSource.UNKNOWNMAGIC) {
							this.outputSlots.setInventorySlotContents(2, new ItemStack(ItemInit.MAGICAL_INK_UNKNOWN.get()));
						} else {
							this.outputSlots.setInventorySlotContents(2, ItemStack.EMPTY);
						}
					}					
					this.detectAndSendChanges();
				} else if (magicItem instanceof IHasMagic) {
					MagicSource source = ((IHasMagic) magicItem).getMagicSource();
					this.itemCost = 1;
					if (source == MagicSource.DARKMAGIC) {
						this.outputSlots.setInventorySlotContents(2, new ItemStack(ItemInit.MAGICAL_INK_DARK.get()));
					} else if (source == MagicSource.LIGHTMAGIC) {
						this.outputSlots.setInventorySlotContents(2, new ItemStack(ItemInit.MAGICAL_INK_LIGHT.get()));
					} else if (source == MagicSource.NEUTRALMAGIC) {
						this.outputSlots.setInventorySlotContents(2, new ItemStack(ItemInit.MAGICAL_INK_NEUTRAL.get()));
					} else if (source == MagicSource.UNKNOWNMAGIC) {
						this.outputSlots.setInventorySlotContents(2, new ItemStack(ItemInit.MAGICAL_INK_UNKNOWN.get()));
					} else {
						this.outputSlots.setInventorySlotContents(2, ItemStack.EMPTY);
					}
				}
			} else {
				this.outputSlots.setInventorySlotContents(2, ItemStack.EMPTY);
			}

		} catch (Exception e) {
			System.out.println(e);
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
