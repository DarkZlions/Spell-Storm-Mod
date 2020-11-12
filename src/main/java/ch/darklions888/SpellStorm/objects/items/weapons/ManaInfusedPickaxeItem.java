package ch.darklions888.SpellStorm.objects.items.weapons;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaPower;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ManaInfusedPickaxeItem extends BasePickaxeItem {

	public ManaInfusedPickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, MagicSource sourceIn, ManaPower powerIn, int maxMana, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, sourceIn, powerIn, maxMana, builderIn);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if (worldIn.isRemote()) {
			return ActionResult.resultPass(stack);
		} else {
			
			if (this.getManaValue(stack, this.defaultMagicSource.getId()) > 0 || playerIn.isCreative()) {
				
				
				return ActionResult.resultSuccess(stack);
			}
		}
		return ActionResult.resultPass(stack);
	}
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		return new StringTextComponent(super.getDisplayName(stack).getString()).mergeStyle(TextFormatting.AQUA);
	}
}
