package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaPower;
import net.minecraft.util.text.TextFormatting;

public class BlueCrystalItem extends BaseItem {

	public BlueCrystalItem(Properties properties) {
		super(MagicSource.NEUTRALMAGIC, ManaPower.VERYLOW, TextFormatting.BLUE, false, properties);
	}
}
