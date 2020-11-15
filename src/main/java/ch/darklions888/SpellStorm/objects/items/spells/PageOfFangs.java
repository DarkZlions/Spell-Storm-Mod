package ch.darklions888.SpellStorm.objects.items.spells;

import java.util.List;

import ch.darklions888.SpellStorm.lib.MagicSource;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EvokerFangsEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class PageOfFangs extends AbstractPageItem {

	public PageOfFangs(Properties properties) {
		super(400, MagicSource.DARKMAGIC, 4, TextFormatting.DARK_GRAY, true, properties);
	}

	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stackIn, ItemStack bookIn) {

		if (worldIn.isRemote()) {
			return ActionResult.resultPass(stackIn);
		} else {
			
			if (this.canCast(stackIn, playerIn)) {
				List<LivingEntity> entityList = worldIn.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(playerIn.getPosX() - 10, playerIn.getPosY() - 1, playerIn.getPosZ() - 10, playerIn.getPosX() + 10, playerIn.getPosY() + 3, playerIn.getPosZ() + 10));
				
				if (entityList.size() > 1) {
					
					for (LivingEntity le : entityList) {
						if (le != playerIn)
							castSpell(playerIn, le);
					}
					if (!playerIn.isCreative()) this.consumMana(stackIn, defaultManaSource);
					
					this.setCooldown(playerIn, 30, stackIn, bookIn);
					return ActionResult.resultSuccess(stackIn);
				} else {
					return ActionResult.resultPass(stackIn);
				}

			}
		}
		
		return ActionResult.resultPass(stackIn);
	}
	
	private void castSpell(PlayerEntity playerIn, LivingEntity target) {
		LivingEntity livingentity = target;
		double d0 = Math.min(livingentity.getPosY(), playerIn.getPosY());
		double d1 = Math.max(livingentity.getPosY(), playerIn.getPosY()) + 1.0D;
		float f = (float) MathHelper.atan2(livingentity.getPosZ() - playerIn.getPosZ(), livingentity.getPosX() - playerIn.getPosX());
		
		if (playerIn.getDistanceSq(livingentity) < 9.0D) {
			for (int i = 0; i < 5; ++i) {
				float f1 = f + (float) i * (float) Math.PI * 0.4F;
				this.spawnFangs(playerIn.getPosX() + (double) MathHelper.cos(f1) * 1.5D, playerIn.getPosZ() + (double) MathHelper.sin(f1) * 1.5D, d0, d1, f1, 0, playerIn);
			}

			for (int k = 0; k < 8; ++k) {
				float f2 = f + (float) k * (float) Math.PI * 2.0F / 8.0F + 1.2566371F;
				this.spawnFangs(playerIn.getPosX() + (double) MathHelper.cos(f2) * 2.5D, playerIn.getPosZ() + (double) MathHelper.sin(f2) * 2.5D, d0, d1, f2, 3, playerIn);
			}
		} else {
			for (int l = 0; l < 16; ++l) {
				double d2 = 1.25D * (double) (l + 1);
				int j = 1 * l;
				this.spawnFangs(playerIn.getPosX() + (double) MathHelper.cos(f) * d2, playerIn.getPosZ() + (double) MathHelper.sin(f) * d2, d0, d1, f, j, playerIn);
			}
		}

	}

	private void spawnFangs(double posX, double posZ, double randomDouble, double posY, float rotation, int warmUpDelay, PlayerEntity playerIn) {
		BlockPos blockpos = new BlockPos(posX, posY, posZ);
		boolean flag = false;
		double d0 = 0.0D;

		do {
			BlockPos blockpos1 = blockpos.down();
			BlockState blockstate = playerIn.world.getBlockState(blockpos1);
			if (blockstate.isSolidSide(playerIn.world, blockpos1, Direction.UP)) {
				if (!playerIn.world.isAirBlock(blockpos)) {
					BlockState blockstate1 = playerIn.world.getBlockState(blockpos);
					VoxelShape voxelshape = blockstate1.getCollisionShape(playerIn.world, blockpos);
					if (!voxelshape.isEmpty()) {
						d0 = voxelshape.getEnd(Direction.Axis.Y);
					}
				}

				flag = true;
				break;
			}

			blockpos = blockpos.down();
		} while (blockpos.getY() >= MathHelper.floor(randomDouble) - 1);

		if (flag) {
			playerIn.world.addEntity(new EvokerFangsEntity(playerIn.world, posX, (double) blockpos.getY() + d0, posZ, rotation, warmUpDelay, playerIn));
		}
	}

	@Override
	public int getInkColor() {
		return 0x6E6E6E;
	}

}
