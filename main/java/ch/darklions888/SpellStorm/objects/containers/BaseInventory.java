package ch.darklions888.SpellStorm.objects.containers;

import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

public class BaseInventory extends Inventory	{

	private final ItemStack stack;
	
	public BaseInventory(ItemStack stackIn, int count) {
		super(count);
		this.stack = stackIn;
		readItemStack();
	}
	
	public ItemStack getItemStack() {
		return this.stack;
	}
	
	public void readItemStack() {
		if(this.stack.getTag() != null) {
			readNBT(this.stack.getTag());
		}
	}
	
	public void writeItemStack() {
		if (isEmpty()) {
			stack.removeChildTag("Items");
		} else {
			writeNBT(stack.getOrCreateTag());
		}
	}
	
	private void readNBT(CompoundNBT nbtIn) {
		final NonNullList<ItemStack> stackList = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		
		ItemStackHelper.loadAllItems(nbtIn, stackList);
		
		for(int i = 0; i < stackList.size(); i++) {
			setInventorySlotContents(i, stackList.get(i));
		}
	}
	
	private void writeNBT(CompoundNBT nbtIn) {
		final NonNullList<ItemStack> stackList = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		
		for(int i = 0; i < stackList.size(); i++) {
			stackList.set(i, getStackInSlot(i));
		}
		
		ItemStackHelper.saveAllItems(nbtIn, stackList);
	}
	
}
