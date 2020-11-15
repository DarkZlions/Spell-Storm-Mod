package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaPower;
import net.minecraft.item.ItemStack;

public interface IHasMagic {
	MagicSource getMagicSource(ItemStack stackIn);
	ManaPower getManaPower(ItemStack stackIn);
}
