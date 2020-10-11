package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaPower;

public class EndGatewayFragmentItem extends BaseItem {

	public EndGatewayFragmentItem(Properties properties) {
		super(MagicSource.UNKNOWNMAGIC, ManaPower.VERYHIGH, null, false, properties);
	}
}
