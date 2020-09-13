package ch.darklions888.SpellStorm.objects.items;

import net.minecraft.item.ItemStack;

public interface IInfusable extends IStoreMana {
	
	default boolean canInfuse(ItemStack infusableStack, ItemStack storeManaStack) {
		if (!(infusableStack.getItem() instanceof IInfusable) || !(storeManaStack.getItem() instanceof IStoreMana)) {
			return false;
		} else if (!(infusableStack.getItem() instanceof IInfusable) || !(storeManaStack.getItem() instanceof IHasMagic)) {
			return false;
		}
		
		if (storeManaStack.isEmpty() || infusableStack.isEmpty()) {
			return false;
		}
		
		return true;
	}
}
