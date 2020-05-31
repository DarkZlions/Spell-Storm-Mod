package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaContainerSize;
import ch.darklions888.SpellStorm.enums.ManaPower;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class PageOfTheWithers extends BasePageItem
{
	public PageOfTheWithers(ManaContainerSize size, MagicSource source, ManaPower mana, TextFormatting format, boolean hasEffect, Properties properties) 
	{
		super(size, source, mana, format, hasEffect, properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		return this.getAbilities(worldIn, playerIn, handIn);
	}
	
	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		if(worldIn.isRemote)
		{
			if(this.getMana(stack) > 0)
			{
				worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 1, 1);
				return ActionResult.resultSuccess(stack);
			}
			else
			{
				return ActionResult.resultPass(stack);				
			}

		}
		else
		{
			if(this.getMana(stack) > 0)
			{
				worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 1, 1);
				
				double speed = 1.2d;
				
				double xD = playerIn.getLookVec().getX() * speed;
				double yD = playerIn.getLookVec().getY() * speed;
				double zD = playerIn.getLookVec().getZ() * speed;
				
				WitherSkullEntity skull = new WitherSkullEntity(worldIn, playerIn.getPosX() + xD, playerIn.getPosY() + 1.5, playerIn.getPosZ() + zD, xD, yD, zD);
				
				this.addMana(stack, -1);
				
				worldIn.addEntity(skull);
				
			}
			
			return ActionResult.resultSuccess(stack);
		}
	}
}
