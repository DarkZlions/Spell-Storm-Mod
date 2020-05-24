package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaContainerSize;
import ch.darklions888.SpellStorm.enums.ManaPower;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class PageOfTheWithers extends BasePageItem
{
	private ItemStack stack = new ItemStack(this);
	public PageOfTheWithers(ManaContainerSize size, MagicSource source, ManaPower mana, TextFormatting format, boolean hasEffect, Properties properties) 
	{
		super(size, source, mana, format, hasEffect, properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		if(worldIn.isRemote)
		{
			if(this.getMana(stack) > 0)
			{
				worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 1.0f, 0.1f);
			}
			
			return ActionResult.resultPass(stack);
		}
		else
		{
			if(this.getMana(stack) > 0)
			{
				double speed = 1.2d;
				
				double xD = playerIn.getLookVec().getX() * speed;
				double yD = playerIn.getLookVec().getY() * speed;
				double zD = playerIn.getLookVec().getZ() * speed;
				
				WitherSkullEntity skull = new WitherSkullEntity(worldIn, playerIn.getPosX(), playerIn.getPosY() + 1.5, playerIn.getPosZ(), xD, yD, zD);
				
				this.addMana(stack, -1);
				
				worldIn.addEntity(skull);
				
			}
			
			return ActionResult.resultSuccess(stack);
		}
	}
	
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) 
	{
		tooltip.add(new StringTextComponent(String.valueOf(this.getMana(stack)) + "/" + this.size + " Mana left"));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
