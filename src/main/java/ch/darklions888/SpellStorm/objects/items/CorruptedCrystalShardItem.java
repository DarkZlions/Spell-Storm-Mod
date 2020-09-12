package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaPower;
import net.minecraft.util.text.TextFormatting;

public class CorruptedCrystalShardItem extends CrystalShardItem {

	public CorruptedCrystalShardItem(MagicSource source, ManaPower mana, TextFormatting format, boolean hasEffect,
			Properties properties) {
		super(source, mana, format, hasEffect, properties);
	}

}
