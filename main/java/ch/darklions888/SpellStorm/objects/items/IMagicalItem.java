package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaContainerSize;
import ch.darklions888.SpellStorm.enums.ManaPower;

public interface IMagicalItem {
	
	public MagicSource magicSource();

	public ManaContainerSize manaContainer();

	public ManaPower manaPower();

}
