package ch.darklions888.SpellStorm.objects.items.weapons;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerType;
import ch.darklions888.SpellStorm.lib.ManaPower;
import ch.darklions888.SpellStorm.objects.items.IInfusable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ManaInfusedSwordItem extends BaseSwordItem implements IInfusable {

	public ManaInfusedSwordItem(MagicSource sourceIn, ManaPower powerIn, ManaContainerType containerIn, Properties builderIn) {
		super(ItemTier.MANA_INFUSED_TIER, 3, -2.2F, MagicSource.NEUTRALMAGIC, powerIn, containerIn, builderIn);
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
