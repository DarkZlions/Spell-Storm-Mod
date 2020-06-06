package ch.darklions888.SpellStorm.objects.containers;

import ch.darklions888.SpellStorm.init.ContainerTypesInit;
import ch.darklions888.SpellStorm.interfaces.IMagicalPageItem;
import ch.darklions888.SpellStorm.objects.items.BookOfSpellsItem;
import ch.darklions888.SpellStorm.util.helpers.ItemNBTHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class BookOfSpellsContainer extends Container {

	private final PlayerInventory playerInventory;
	private final IInventory inputSlots = new Inventory(6);
	private ItemStack stack;
	private NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);

	private final Slot[] slots = new Slot[6];
	private final int[][] slotsCoordinates = {
			// X-Coordinates
			{ 61, 96, 107, 96, 61, 50 },
			// Y-Coordinates
			{ 27, 27, 49, 71, 71, 49 } };

	protected BookOfSpellsContainer(ContainerType<?> containerIn, int id, PlayerInventory playerInventory,
			PlayerEntity playerIn) {

		super(containerIn, id);

		this.playerInventory = playerInventory;

		playerInventory.openInventory(playerIn);
		
		if(stack != null) {
			ItemStackHelper.loadAllItems(ItemNBTHelper.getCompound(stack, BookOfSpellsItem.COMPOUND_TAG, false), items);
			
			ItemStack[] stackArray = (ItemStack[]) items.toArray();
			
			for(int i = 0; i < stackArray.length; i++) {
				inputSlots.setInventorySlotContents(i, stackArray[i]);
			}
		}

		for (int i = 0; i < slots.length; i++) {

			slots[i] = this.addSlot(new Slot(this.inputSlots, i, slotsCoordinates[0][i], slotsCoordinates[1][i]) {

				public boolean isItemValid(ItemStack stack) {

					return stack.getItem() instanceof IMagicalPageItem;

				}

				public int getSlotStackLimit() {

					return 1;

				}

			});
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 106 + i * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 164));
		}

	}
	
	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		super.onCraftMatrixChanged(inventoryIn);
		
		items.clear();
		
		for(int i = 0; i < inputSlots.getSizeInventory(); i++)
		{
			items.add(inputSlots.getStackInSlot(i));
		}
	}

	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index < this.inputSlots.getSizeInventory()) {
				if (!this.mergeItemStack(itemstack1, this.inputSlots.getSizeInventory(), this.inventorySlots.size(),
						true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, this.inputSlots.getSizeInventory(), false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {

		return true;

	}

	@Override
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		
		System.out.println(stack == null);
		
		if(this.stack != null)
			ItemStackHelper.saveAllItems(ItemNBTHelper.getCompound(this.stack, BookOfSpellsItem.COMPOUND_TAG, false), items);
		
		this.playerInventory.closeInventory(playerIn);
	}

	public BookOfSpellsContainer(ContainerType<?> type, int windowId, PlayerInventory playerInventory, ItemStack stack) {
		this(type, windowId, playerInventory, playerInventory.player);
		
		this.stack = stack;
	}
	
	public BookOfSpellsContainer(ContainerType<?> type, int windowId, PlayerInventory playerInventory) {
		this(type, windowId, playerInventory, playerInventory.player);
	}

	public static BookOfSpellsContainer create(int windowId, PlayerInventory playerInventory) {
		return new BookOfSpellsContainer((ContainerType<?>) ContainerTypesInit.BOOK_OF_SPELLS.get(), windowId,
				playerInventory);
	}

}
