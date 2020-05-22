package ch.darklions888.SpellStorm.objects.items;

import javax.annotation.Nullable;

import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaPower;
import net.minecraft.util.text.TextFormatting;

public class CrytalShardItem extends BaseItem
{

	public CrytalShardItem(
			MagicSource source,
			ManaPower mana,
			@Nullable TextFormatting format,
			@Nullable boolean hasEffect,
			Properties properties) 
	{
		super(source, mana, format, hasEffect, properties);
	}
}
