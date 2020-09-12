package ch.darklions888.SpellStorm.objects.items.pages;

import java.util.List;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerSize;
import ch.darklions888.SpellStorm.lib.ManaPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PageOfThunder extends AbstractPageItem
{
	
	public PageOfThunder(Properties properties) {
		super(ManaContainerSize.SMALL, MagicSource.LIGHTMAGIC, ManaPower.HIGH, 2, TextFormatting.GOLD, true, properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		return getAbilities(worldIn, playerIn, handIn, playerIn.getHeldItem(handIn), null);
	}
	
	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stack, ItemStack bookIn) {

		if (worldIn.isRemote) {
			return ActionResult.resultPass(stack);
			
		} else {
			
			if (playerIn.isCreative() || this.getMana(stack) >= this.manaConsumption) {
				List<LivingEntity> entityList = worldIn.getEntitiesWithinAABB(LivingEntity.class,
						new AxisAlignedBB(playerIn.getPosX() - 10, playerIn.getPosY() - 10, playerIn.getPosZ() - 10,
								playerIn.getPosX() + 10, playerIn.getPosY() + 10, playerIn.getPosZ() + 10));

				ServerWorld serverworld = (ServerWorld) worldIn;
				
				if (entityList.size() > 0) {
					
					for (Entity e : entityList) {
						if (e != null && e != playerIn && (this.getMana(stack) >= this.manaConsumption || playerIn.isCreative())) {
							
							LightningBoltEntity bolt = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, serverworld);
							bolt.setPosition(e.getPosX(), e.getPosY(), e.getPosZ());						
							serverworld.addEntity(bolt);
							
							if (!playerIn.isCreative()) this.addMana(stack, -this.manaConsumption);
						}
					}
				
					return ActionResult.resultSuccess(stack);
				} else {
					return ActionResult.resultPass(stack);
				}
			} else {
				return ActionResult.resultPass(stack);
			}
		}
	}

	@Override
	public int getInkColor() {
		return 0x9accff;
	}
}
