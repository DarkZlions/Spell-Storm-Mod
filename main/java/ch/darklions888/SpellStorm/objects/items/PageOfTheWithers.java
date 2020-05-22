package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class PageOfTheWithers extends BaseBookItem
{
	
	public PageOfTheWithers(MagicSource source, ManaPower mana, TextFormatting format, boolean hasEffect, Properties properties) 
	{
		super(source, mana, format, hasEffect, properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if(worldIn.isRemote)
		{
			return ActionResult.resultPass(stack);
		}
		else
		{
			double x = playerIn.getLookVec().x;
			double y = playerIn.getLookVec().y;
			double z = playerIn.getLookVec().z;
			
			WitherSkullEntity skull = new WitherSkullEntity(worldIn, playerIn.getPosX(), playerIn.getPosY() + 1, playerIn.getPosZ(), x, y, z);
			
			worldIn.addEntity(skull);
			
			return ActionResult.resultSuccess(stack);
		}
	}
}
