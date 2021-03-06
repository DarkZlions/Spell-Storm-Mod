package ch.darklions888.SpellStorm.objects.items.spells;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.config.ConfigHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PageOfDragonBall extends AbstractPageItem {



	public PageOfDragonBall(Properties properties) {
		super(ConfigHandler.COMMON.pageOfDragonBall_maxMana.get(), MagicSource.UNKNOWNMAGIC, ConfigHandler.COMMON.pageOfDragonBall_manaConsumption.get(), TextFormatting.DARK_PURPLE, true, properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		return getAbilities(worldIn, playerIn, handIn, playerIn.getHeldItem(handIn), null);
	}

	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stackIn, ItemStack bookIn) {

		if (worldIn.isRemote) {
			return ActionResult.resultPass(stackIn);

		} else {

			if (playerIn.isCreative() || this.getManaValue(stackIn, this.defaultManaSource.getId()) >= this.manaConsumption) {

				ServerWorld serverWorld = (ServerWorld) worldIn;

				double x = playerIn.getPosX();
				double y = playerIn.getPosY();
				double z = playerIn.getPosZ();

				double xD = playerIn.getLookVec().getX();
				double yD = playerIn.getLookVec().getY();
				double zD = playerIn.getLookVec().getZ();

				serverWorld.playSound(null, x, y, z, SoundEvents.ENTITY_ENDER_DRAGON_SHOOT, SoundCategory.PLAYERS, 1.0f,
						1.0f);

				DragonFireballEntity ball = new DragonFireballEntity(worldIn, playerIn, 0, 0, 0);

				ball.setPosition(x + xD, y + 1.5, z + zD);
				ball.accelerationX = xD * .1;
				ball.accelerationY = yD * .1;
				ball.accelerationZ = zD * .1;
				serverWorld.addEntity(ball);

				if (!playerIn.isCreative())
					this.consumMana(stackIn, defaultManaSource);
				return ActionResult.resultSuccess(stackIn);
			} else {
				return ActionResult.resultPass(stackIn);
			}
		}
	}

	@Override
	public int getInkColor() {
		return 0x9c49af;
	}
}
