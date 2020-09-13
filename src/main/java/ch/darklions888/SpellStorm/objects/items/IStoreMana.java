package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerType;
import ch.darklions888.SpellStorm.util.helpers.ItemNBTHelper;
import net.minecraft.item.ItemStack;

public interface IStoreMana {
	
	default int getManaValue(ItemStack stack, String key) {
		return ItemNBTHelper.getInt(stack, key, 0);
	}

	default void setManaValue(ItemStack stack, String key, int manaAmount) {
		ItemNBTHelper.setInt(stack, key, manaAmount);
	}

	default void addManaValue(ItemStack stack, String key, int manaAmount) {
		setManaValue(stack, key, getManaValue(stack, key) + manaAmount);
	}
	
	ManaContainerType getManaContainer();

	public List<MagicSource> getMagigSourceList();

	public boolean hasMagicSource(MagicSource sourceIn);
}
