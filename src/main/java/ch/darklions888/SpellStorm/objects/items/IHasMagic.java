package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaPower;

public interface IHasMagic {
	MagicSource getMagicSource();
	ManaPower getManaPower();
}
