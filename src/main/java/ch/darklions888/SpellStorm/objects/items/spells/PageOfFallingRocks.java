package ch.darklions888.SpellStorm.objects.items.spells;

import java.util.List;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.config.ConfigHandler;
import ch.darklions888.SpellStorm.util.helpers.mathhelpers.MathHelpers;
import ch.darklions888.SpellStorm.util.helpers.mathhelpers.Vec3;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PageOfFallingRocks extends AbstractPageItem {

	public PageOfFallingRocks(Properties properties) {
		super(ConfigHandler.COMMON.pageOfFallingRocks_maxMana.get(), MagicSource.UNKNOWNMAGIC, ConfigHandler.COMMON.pageOfFallingRocks_manaConsumption.get(), TextFormatting.BLACK, true, properties);
		this.coolDownTick = ConfigHandler.COMMON.pageOfFallingRocks_coolDownDuration.get();
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		return getAbilities(worldIn, playerIn, handIn, playerIn.getHeldItem(handIn), null);
	}

	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stack, ItemStack bookIn) {
		if (worldIn.isRemote) {
			
			return ActionResult.resultPass(stack);
		} else {
			double x = playerIn.getPosX();
			double y = 260.0d;
			double z = playerIn.getPosZ();

			if (playerIn.isCreative() || this.getManaValue(stack, this.defaultManaSource.getId()) >= this.manaConsumption) {
				
				((ServerWorld)worldIn).playSound(null, x, playerIn.getPosY(), z, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f);
				
				List<Vec3> coords = MathHelpers.getCircleCoordinates(1.2, new Vec3(x, playerIn.getPosY(), z), 24, false, false);
				for (Vec3 v : coords) {
					((ServerWorld)worldIn).spawnParticle(ParticleTypes.SMOKE, v.X(), v.Y(), v.Z(), 1, 0, 0, 0, 0.001f);
				}
				
				FireballEntity entity = new FireballEntity(worldIn, playerIn, 0.0d, -2.5d, 0.0d);
				entity.setPosition(x, y, z);
				entity.accelerationX = 0;
				entity.accelerationY = -0.15d;
				entity.accelerationZ = 0;
				entity.explosionPower = ConfigHandler.COMMON.pageOfFallingRocks_explosionPower.get();

				worldIn.addEntity(entity);

				if (!playerIn.isCreative())
					this.consumMana(stack, defaultManaSource);
				
				this.setCooldown(playerIn, stack, bookIn);

				return ActionResult.resultSuccess(stack);
			} else {
				return ActionResult.resultPass(stack);
			}
		}
	}

	@Override
	public int getInkColor() {
		return 0x1f1f1f;
	}
}
