package ch.darklions888.SpellStorm.objects.items.pages;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerSize;
import ch.darklions888.SpellStorm.lib.ManaPower;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class PageOfHealing extends AbstractPageItem {

	public PageOfHealing(ManaContainerSize size, MagicSource source, ManaPower mana, int manaConsumption, TextFormatting format, boolean hasEffect, Properties properties) {
		super(size, source, mana, manaConsumption, format, hasEffect, properties);
	}

	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stackIn, ItemStack bookIn) {
		
		return null;
	}

	@Override
	public int getInkColor() {
		// TODO Auto-generated method stub
		return 0;
	}
}
