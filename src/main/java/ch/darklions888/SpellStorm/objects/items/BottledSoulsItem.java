package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaPower;
import net.minecraft.item.Item;

public class BottledSoulsItem extends Item implements IHasMagic {

	public BottledSoulsItem(Properties properties) {
		super(properties);
	}

	@Override
	public MagicSource getMagicSource() {
		return null;
	}

	@Override
	public ManaPower getManaPower() {
		return null;
	}

}
