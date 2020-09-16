package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerType;
import ch.darklions888.SpellStorm.util.helpers.ItemNBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public interface IStoreMana {
	
	default int getManaValue(ItemStack stack, String key) {
		return ItemNBTHelper.getInt(stack, key, 0);
	}

	default void setManaValue(ItemStack stack, String key, int manaAmount) {
		ItemNBTHelper.setInt(stack, key, manaAmount);
	}

	default void addManaValue(ItemStack stack, String key, int manaAmount) {
		setManaValue(stack, key, MathHelper.clamp(getManaValue(stack, key) + manaAmount, 0, this.getManaContainer().size));
	}
	
	default MagicSource getDefaultSource() {
		return this.getMagigSourceList().get(0) != null ? this.getMagigSourceList().get(0) : MagicSource.NEUTRALMAGIC;
	}
	
	ManaContainerType getManaContainer();

	public List<MagicSource> getMagigSourceList();

	public boolean hasMagicSource(MagicSource sourceIn);
}
