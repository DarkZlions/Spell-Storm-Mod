package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaContainerSize;
import ch.darklions888.SpellStorm.enums.ManaPower;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class PageOfFallingRocks extends BasePageItem
{

	public PageOfFallingRocks(ManaContainerSize size, MagicSource source, ManaPower mana, TextFormatting format, boolean hasEffect, Properties properties) 
	{
		super(size, source, mana, format, hasEffect, properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		return getAbilities(worldIn, playerIn, handIn);
	}
	
	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if(playerIn.isCreative())
		{
			this.setMana(stack, this.getMaxContainerSize(stack));
		}
		
		double x = playerIn.getPosX();
		double y = 260.0d;
		double z = playerIn.getPosZ();

		if(this.getMana(stack) == this.getMaxContainerSize(stack))
		{
			worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0f, 0.1f);
			
			for(int i = 0; i < 10; i++)
			{
				worldIn.addParticle(ParticleTypes.LAVA, true, playerIn.getPosX() + playerIn.getRNG().nextDouble(), playerIn.getPosY() + playerIn.getRNG().nextDouble() + .1d, playerIn.getPosZ() + playerIn.getRNG().nextDouble(), 0, 0, 0);
			}
			
			FireballEntity entity = new FireballEntity(worldIn, x, y, z, 0.0d, -2.5d, 0.0d);
			entity.explosionPower = 14;
	
			worldIn.addEntity(entity);
		
			this.setMana(stack, 0);
			
			return ActionResult.resultSuccess(stack);
		}
		else
		{
			return ActionResult.resultPass(stack);
		}
	}

	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) 
	{
		tooltip.add(new StringTextComponent("This page need full mana to use."));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
