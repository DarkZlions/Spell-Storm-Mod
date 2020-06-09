package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaContainerSize;
import ch.darklions888.SpellStorm.enums.ManaPower;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PageOfDragonBall extends BasePageItem{

	public PageOfDragonBall(ManaContainerSize size, MagicSource source, ManaPower mana, TextFormatting format, boolean hasEffect, Properties properties) {
		super(size, source, mana, format, hasEffect, properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		return getAbilities(worldIn, playerIn, handIn, playerIn.getHeldItem(handIn));
	}
	
	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stackIn) {
		
		if (worldIn.isRemote) {
			if (this.getMana(stackIn) > 0) {
				return ActionResult.resultSuccess(stackIn);
			} else {
				return ActionResult.resultPass(stackIn);
			}
		}else {
			
			if (this.getMana(stackIn) > 0) {
				
				ServerWorld serverWorld = (ServerWorld) worldIn;
				
				double x = playerIn.getPosX();
				double y = playerIn.getPosY();
				double z = playerIn.getPosZ();
				
				double lx = playerIn.getLookVec().getX();
				double ly = playerIn.getLookVec().getY();
				double lz = playerIn.getLookVec().getZ();
				
				double speed = 1.4d;
				
				DragonFireballEntity ball = new DragonFireballEntity(
						serverWorld,
						x + lx,
						(y + 1.2d) + ly,
						z + lz,
						lx * speed,
						ly * speed,
						lz * speed);
				
				serverWorld.addEntity(ball);
				
				this.addMana(stackIn, -2);
				
				return ActionResult.resultSuccess(stackIn);
			} else {
				return ActionResult.resultPass(stackIn);
			}
			
		}

		
	}

}
