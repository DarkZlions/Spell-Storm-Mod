package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerSize;
import ch.darklions888.SpellStorm.lib.ManaPower;

public interface IMagicalItem {
	
	public MagicSource magicSource();

	public ManaContainerSize manaContainer();

	public ManaPower manaPower();

}
