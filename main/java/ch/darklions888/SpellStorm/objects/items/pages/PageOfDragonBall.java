package ch.darklions888.SpellStorm.objects.items.pages;

import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaContainerSize;
import ch.darklions888.SpellStorm.enums.ManaPower;
import ch.darklions888.SpellStorm.objects.items.BasePageItem;
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

public class PageOfDragonBall extends BasePageItem {

	public PageOfDragonBall(ManaContainerSize size, MagicSource source, ManaPower mana, TextFormatting format,
			boolean hasEffect, Properties properties) {
		super(size, source, mana, format, hasEffect, properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		return getAbilities(worldIn, playerIn, handIn, playerIn.getHeldItem(handIn));
	}

	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stackIn) {

		if (worldIn.isRemote) {
			return ActionResult.resultPass(stackIn);
			
		} else {

			if (playerIn.isCreative() || this.getMana(stackIn) > 0) {

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

				if (!playerIn.isCreative())
					this.addMana(stackIn, -2);
				ball.setPosition(x + xD, y + 1.5, z + zD);
				ball.accelerationX = xD * .1;
				ball.accelerationY = yD * .1;
				ball.accelerationZ = zD * .1;
				serverWorld.addEntity(ball);

				this.addMana(stackIn, -2);
				return ActionResult.resultSuccess(stackIn);
			} else {
				return ActionResult.resultPass(stackIn);
			}
		}
	}
}
