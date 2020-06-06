package ch.darklions888.SpellStorm.objects.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class BaseInventory implements IInventory	{

	private final NonNullList<ItemStack> stackList;
	private final int width, height;
	private final Container eventHandler;
	
	public BaseInventory(Container evenHandlerIn, int width, int height) {
		
		this.stackList = NonNullList.withSize(width * height, ItemStack.EMPTY);
		this.eventHandler = evenHandlerIn;
		this.width = width;
		this.height = height;
		
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSizeInventory() {
		
		return this.stackList.size();
		
	}

	@Override
	public boolean isEmpty() {
		
		for (ItemStack stack : this.stackList) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		
		return true;
		
	}

	@Override
	public ItemStack getStackInSlot(int index) {

		return index >= this.getInventoryStackLimit() ? ItemStack.EMPTY :
			this.stackList.get(index);
		
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		
		ItemStack stack = ItemStackHelper.getAndSplit(
				this.stackList
				, index
				,count
				);
		
		if(!stack.isEmpty()) {
			this.eventHandler.onCraftMatrixChanged(this);
		}
		
		return stack;
		
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {

		return ItemStackHelper.getAndRemove(this.stackList, index);
		
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		
		this.stackList.set(index, stack);
		this.eventHandler.onCraftMatrixChanged(this);
		
	}

	@Override
	public void markDirty() {
		
		
		
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {

		return true;
		
	}
	
	public int getHeight() {
		
		return this.height;
		
	}
	
	public int getWidth() {
		
		return this.width;
		
	}

}
