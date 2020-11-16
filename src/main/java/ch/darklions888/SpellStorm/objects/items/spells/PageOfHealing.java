package ch.darklions888.SpellStorm.objects.items.spells;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.config.ConfigHandler;
import ch.darklions888.SpellStorm.util.helpers.mathhelpers.MathHelpers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class PageOfHealing extends AbstractPageItem {

	private float healAmount = ConfigHandler.COMMON.pageOfHealing_healAmount.get().floatValue();
	private int coolDownTicks = 60;
	
	public PageOfHealing(Properties properties) {
		super(ConfigHandler.COMMON.pageOfHealing_maxMana.get(), MagicSource.LIGHTMAGIC, ConfigHandler.COMMON.pageOfHealing_manaConsumption.get(), TextFormatting.RED, true, properties);
	}

	
	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stackIn, ItemStack bookIn) {
		
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if (worldIn.isRemote()) {
			return ActionResult.resultPass(stack);
		} else {
			if (this.canCast(stack, playerIn)) {
				
				if (playerIn.getHealth() < playerIn.getMaxHealth()) {
					
					playerIn.setHealth((float) MathHelpers.Clamp(playerIn.getHealth() + this.healAmount, 0, playerIn.getMaxHealth()));
					
					this.setCooldown(playerIn, coolDownTicks, stack, bookIn);

						
					this.consumMana(stack, this.defaultManaSource);
					return ActionResult.resultSuccess(stack);
				} else {
					return ActionResult.resultPass(stack);
				}
			}
			return ActionResult.resultPass(stack);
		}
	}

	@Override
	public int getInkColor() {
		return 0xFF2B2B;
	}
}
