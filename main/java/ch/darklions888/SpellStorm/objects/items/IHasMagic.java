package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerSize;
import ch.darklions888.SpellStorm.lib.ManaPower;

public interface IHasMagic {
	ManaContainerSize getManaContainer();
	MagicSource getMagicSource();
	ManaPower getManaPower();
}
