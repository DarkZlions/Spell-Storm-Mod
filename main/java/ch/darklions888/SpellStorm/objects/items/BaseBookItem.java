package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaPower;
import net.minecraft.util.text.TextFormatting;

public class BaseBookItem extends BaseItem 
{

	public BaseBookItem(MagicSource source, ManaPower mana, TextFormatting format, boolean hasEffect, Properties properties)
	{
		super(source, mana, format, hasEffect, properties);
	}
}
