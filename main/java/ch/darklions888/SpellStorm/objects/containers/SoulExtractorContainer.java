package ch.darklions888.SpellStorm.objects.containers;

import ch.darklions888.SpellStorm.init.BlockInit;
import ch.darklions888.SpellStorm.init.ContainerTypesInit;
import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.objects.blocks.SoulExtractorBlock;
import ch.darklions888.SpellStorm.objects.items.IMagicalContainer;
import ch.darklions888.SpellStorm.objects.items.IMagicalPageItem;
import ch.darklions888.SpellStorm.objects.items.SoulCatcherItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
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

public class SoulExtractorContainer extends Container {
	private final IInventory outputslots = new CraftResultInventory();
	private final IInventory inputslots = new Inventory(2) {
		public void markDirty() {
			super.markDirty();
			SoulExtractorContainer.this.onCraftMatrixChanged(this);
		}
	};

	private final IWorldPosCallable posCal;

	public SoulExtractorContainer(ContainerType<?> type, int id, PlayerInventory inventoryIn,
			IWorldPosCallable posCalIn) {
		super(type, id);
		this.posCal = posCalIn;

		this.addSlot(new Slot(this.inputslots, 0, 48, 49) {

			public boolean isItemValid(ItemStack stackIn) {
				return stackIn.getItem() instanceof SoulCatcherItem;
			}
		});

		this.addSlot(new Slot(this.inputslots, 1, 48, 13) {

			public boolean isItemValid(ItemStack stackIn) {
				return stackIn.getItem() instanceof IMagicalContainer || stackIn.getItem() instanceof IMagicalPageItem;
			}
		});

		this.addSlot(new Slot(this.outputslots, 2, 127, 27) {

			public boolean isItemValid(ItemStack stackIn) {
				return false;
			}

			public boolean canTakeStack(PlayerEntity player) {
				return true;
			}

			public ItemStack onTake(PlayerEntity playerIn, ItemStack stackIn) {
				SoulExtractorContainer.this.inputslots.setInventorySlotContents(1, ItemStack.EMPTY);

				ItemStack catcher = SoulExtractorContainer.this.inputslots.getStackInSlot(0);

				if (catcher.getItem() instanceof SoulCatcherItem) {
					SoulCatcherItem soulItem = (SoulCatcherItem) catcher.getItem();

					soulItem.clearEntity(catcher);
				}

				return stackIn;
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

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		if (inventoryIn == this.inputslots) {
			this.updateOutput();
		}
		super.onCraftMatrixChanged(inventoryIn);
	}

	private void updateOutput() {
		try {
			ItemStack catchStack = this.inputslots.getStackInSlot(0);
			ItemStack containerStack = this.inputslots.getStackInSlot(1);

			if (catchStack.getItem() instanceof SoulCatcherItem
					&& containerStack.getItem() instanceof IMagicalContainer) {
				ItemStack containerCopy = containerStack.copy();

				SoulCatcherItem soulCatcher = (SoulCatcherItem) catchStack.getItem();
				IMagicalContainer containerItem = (IMagicalContainer) containerStack.getItem();

				EntityType<?> entityType = soulCatcher.getEntity(catchStack);

				if (entityType != null) {
					Entity entity = entityType.create(SoulExtractorBlock.getWorld());

					MobEntity mob = (MobEntity) entity;

					MagicSource mobSource = getSourceFromEntity(mob);
					if (containerItem.hasMagicSource(mobSource)) {
						containerItem.addManaValue(containerCopy, getSourceFromEntity(mob).sourceId,
								(int) mob.getMaxHealth());
						this.outputslots.setInventorySlotContents(2, containerCopy);
						this.detectAndSendChanges();
					} else {
						this.outputslots.setInventorySlotContents(2, ItemStack.EMPTY);
					}
				} else {
					this.outputslots.setInventorySlotContents(2, ItemStack.EMPTY);
				}
			} else if (catchStack.getItem() instanceof SoulCatcherItem && containerStack.getItem() instanceof IMagicalPageItem) {
				ItemStack containerCopy = containerStack.copy();

				SoulCatcherItem soulCatcher = (SoulCatcherItem) catchStack.getItem();
				IMagicalPageItem page = (IMagicalPageItem) containerStack.getItem();

				EntityType<?> entityType = soulCatcher.getEntity(catchStack);

				Entity entity = entityType.create(SoulExtractorBlock.getWorld());

				if (entity != null && entity instanceof MobEntity) {
					MobEntity mob = (MobEntity) entity;

					if (page.magicSource() == getSourceFromEntity(mob)) {
						page.addMana(containerCopy, (int) mob.getMaxHealth());
						this.outputslots.setInventorySlotContents(2, containerCopy);
						this.detectAndSendChanges();
					} else {
						this.outputslots.setInventorySlotContents(2, ItemStack.EMPTY);
					}
				} else {
					this.outputslots.setInventorySlotContents(2, ItemStack.EMPTY);
				}
			} else {
				this.outputslots.setInventorySlotContents(2, ItemStack.EMPTY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MagicSource getSourceFromEntity(Entity entityIn) {
		if (entityIn instanceof MonsterEntity && !(entityIn instanceof EndermanEntity)
				|| entityIn instanceof SlimeEntity && !(entityIn instanceof EndermanEntity)) {
			return MagicSource.DARKMAGIC;
		} else if (entityIn instanceof AnimalEntity) {
			return MagicSource.LIGHTMAGIC;
		} else if (entityIn instanceof EndermanEntity) {
			return MagicSource.UNKNOWNMAGIC;
		} else {
			return MagicSource.NEUTRALMAGIC;
		}
	}

	@Override
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);

		this.posCal.consume((worldIn, pos) -> {
			this.clearContainer(playerIn, worldIn, this.inputslots);
		});
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(this.posCal, playerIn, BlockInit.SOUL_EXTRACTOR.get());
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

	public SoulExtractorContainer(ContainerType<?> containerType, int windowId, PlayerInventory playerInventory) {
		this(containerType, windowId, playerInventory, IWorldPosCallable.DUMMY);
	}

	public static SoulExtractorContainer create(int windowId, PlayerInventory playerInventory) {
		return new SoulExtractorContainer((ContainerType<?>) ContainerTypesInit.SOUL_EXTRACTOR.get(), windowId,
				playerInventory);
	}

}
