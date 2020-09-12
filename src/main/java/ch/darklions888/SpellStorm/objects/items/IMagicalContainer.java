package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.lib.MagicSource;
import net.minecraft.item.ItemStack;

public interface IMagicalContainer {
	
	public int getManaValue(ItemStack stack, String key);

	public void setManaValue(ItemStack stack, String key, int manaAmount);

	public void addManaValue(ItemStack stack, String key, int manaAmount);

	public int getContainerSize();

	public MagicSource[] getMagigSource();

	public boolean hasMagicSource(MagicSource sourceIn);

}
