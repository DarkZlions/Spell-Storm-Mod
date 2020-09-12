/*
 *  This slot is used, only for magical items that have mana.
 */
package ch.darklions888.SpellStorm.objects.containers.slots;

import ch.darklions888.SpellStorm.objects.items.IHasMagic;
import ch.darklions888.SpellStorm.objects.items.IMagicalPageItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class MagicalItemSlot extends Slot {
	
	public MagicalItemSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof IHasMagic;
	}
	
	public int getItemStackLimit(ItemStack stack) {
		if (stack.getItem() instanceof IMagicalPageItem) {
			return 1;
		} else {
			return super.getItemStackLimit(stack);
		}
	}
}
