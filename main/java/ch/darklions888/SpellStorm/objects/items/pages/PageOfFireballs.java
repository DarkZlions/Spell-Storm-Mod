package ch.darklions888.SpellStorm.objects.items.pages;

import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaContainerSize;
import ch.darklions888.SpellStorm.enums.ManaPower;
import ch.darklions888.SpellStorm.objects.items.BasePageItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class PageOfFireballs extends BasePageItem
{

	public PageOfFireballs(ManaContainerSize size, MagicSource source, ManaPower mana, TextFormatting format, boolean hasEffect, Properties properties) 
	{
		super(size, source, mana, format, hasEffect, properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		return getAbilities(worldIn, playerIn, handIn, playerIn.getHeldItem(handIn));
	}
	
	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stack) 
	{

		if(playerIn.isCreative())
		{
			this.setMana(stack, this.getMaxContainerSize(stack));
		}
		
		if(worldIn.isRemote)
		{
			if(this.getMana(stack) > 0)
			{
				worldIn.playSound(playerIn,playerIn.getPosition(), SoundEvents.ITEM_FIRECHARGE_USE,  SoundCategory.PLAYERS, 1.0f, .1f);
				for(int i = 0; i < 10; i++)
				{
					worldIn.addParticle(ParticleTypes.LAVA, playerIn.getPosXRandom(.6d), playerIn.getPosYRandom(), playerIn.getPosZRandom(.6d), playerIn.getLookVec().getX(), playerIn.getLookVec().getY(), playerIn.getLookVec().getZ());
				}
				
				return ActionResult.resultSuccess(stack);				
			}
			else
			{
				return ActionResult.resultFail(stack);
			}

		}
		else
		{	if(this.getMana(stack) > 0)
			{
				double accel = 1.5d;	//Bonus acceleration for more momentum or something like this
				
				for(int i = 0; i < 3; i++)
				{
					SmallFireballEntity entity = new SmallFireballEntity
							(
							worldIn,
							playerIn.getPosX() + playerIn.getLookVec().x,
							playerIn.getPosY() + 1.2d,
							playerIn.getPosZ() + playerIn.getLookVec().z,
							playerIn.getLookVec().x * accel,
							playerIn.getLookVec().y * accel,
							playerIn.getLookVec().z * accel
							);
					
					worldIn.addEntity(entity);
				}
				
				this.addMana(stack, -1);
				
				return ActionResult.resultSuccess(stack);
			}
			else
			{
				return ActionResult.resultFail(stack);
			}
		}
	}
}
