package ch.darklions888.SpellStorm.objects.items.weapons;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerType;
import ch.darklions888.SpellStorm.lib.ManaPower;
import net.minecraft.item.IItemTier;

public class ManaInfusedPickaxeItem extends BasePickaxeItem {

	public ManaInfusedPickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, MagicSource sourceIn, ManaPower powerIn, ManaContainerType containerIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, sourceIn, powerIn, containerIn, builderIn);
	}

}
