package ch.darklions888.SpellStorm.objects.containers;

import ch.darklions888.SpellStorm.objects.items.IHasSoul;
import ch.darklions888.SpellStorm.objects.items.SoulCatcherItem;
import ch.darklions888.SpellStorm.registries.BlockInit;
import ch.darklions888.SpellStorm.registries.ContainerTypesInit;
import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IWorldPosCallable;

public class SoulExtractorContainer extends Container {
	private final IInventory outputslots = new CraftResultInventory();
	private final IInventory inputslots = new Inventory(2) {
		public void markDirty() {
			super.markDirty();
			SoulExtractorContainer.this.onCraftMatrixChanged(this);
		}
	};
	private final PlayerInventory playerInventory;

	private final IWorldPosCallable posCal;

	public SoulExtractorContainer(ContainerType<?> type, int id, PlayerInventory inventoryIn, IWorldPosCallable posCalIn) {
		super(type, id);
		this.posCal = posCalIn;
		this.playerInventory = inventoryIn;

		this.addSlot(new Slot(this.inputslots, 0, 48, 49) {

			public boolean isItemValid(ItemStack stackIn) {
				return stackIn.getItem() instanceof SoulCatcherItem;
			}
		});

		this.addSlot(new Slot(this.inputslots, 1, 48, 13) {

			public boolean isItemValid(ItemStack stackIn) {
				return true;
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
				SoulExtractorContainer.this.inputslots.getStackInSlot(1).shrink(1);;

				ItemStack catcher = SoulExtractorContainer.this.inputslots.getStackInSlot(0);

				if (catcher.getItem() instanceof SoulCatcherItem) {
					SoulCatcherItem soulItem = (SoulCatcherItem) catcher.getItem();

					soulItem.clearSoul(catcher);
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
		ItemStack catchStack = this.inputslots.getStackInSlot(0);
		ItemStack bottleStack = this.inputslots.getStackInSlot(1);
		
		if (catchStack.getItem() instanceof SoulCatcherItem && bottleStack.getItem() == Items.GLASS_BOTTLE) {
			
			SoulCatcherItem soulCatcher = (SoulCatcherItem) catchStack.getItem();
			
			EntityType<?> entityType = soulCatcher.getEntityType(catchStack);
			
			if (entityType != null) {
				Entity entity = entityType.create(this.playerInventory.player.getEntityWorld());

					ItemStack soulBottle = new ItemStack(ItemInit.BOTTLED_SOULS.get());
					((IHasSoul)soulBottle.getItem()).setSoulAndId(soulBottle, entity);
					this.outputslots.setInventorySlotContents(2, soulBottle);
					this.detectAndSendChanges();
					return;
					
				} else {
					this.outputslots.setInventorySlotContents(2, ItemStack.EMPTY);
				}
			} else {
				this.outputslots.setInventorySlotContents(2, ItemStack.EMPTY);
		}
		
		this.detectAndSendChanges();
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
