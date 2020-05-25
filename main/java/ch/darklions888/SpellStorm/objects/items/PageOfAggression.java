package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaContainerSize;
import ch.darklions888.SpellStorm.enums.ManaPower;
import net.minecraft.util.text.TextFormatting;

public class PageOfAggression extends BasePageItem
{

	public PageOfAggression(ManaContainerSize size, MagicSource source, ManaPower mana, TextFormatting format, boolean hasEffect, Properties properties) {
		super(size, source, mana, format, hasEffect, properties);
	}

}
