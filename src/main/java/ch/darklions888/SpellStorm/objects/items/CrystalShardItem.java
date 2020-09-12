package ch.darklions888.SpellStorm.objects.items;

import javax.annotation.Nullable;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaPower;
import net.minecraft.util.text.TextFormatting;

public class CrystalShardItem extends BaseItem
{

	public CrystalShardItem(
			MagicSource source,
			ManaPower mana,
			@Nullable TextFormatting format,
			@Nullable boolean hasEffect,
			Properties properties) 
	{
		super(source, mana, format, hasEffect, properties);
	}
}
