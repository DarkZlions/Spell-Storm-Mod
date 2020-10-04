package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.lib.MagicSource;
import net.minecraft.item.ItemStack;

public interface IInfusable {
	
	MagicSource getMagicSource();
	ItemStack getOutputItemStack(ItemStack infusableStack, ItemStack hasMana);
	int getInfusionCost();
}
