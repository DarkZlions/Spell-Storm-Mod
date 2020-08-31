package ch.darklions888.SpellStorm.objects.items.pages;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerSize;
import ch.darklions888.SpellStorm.lib.ManaPower;
import ch.darklions888.SpellStorm.objects.items.BasePageItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PageOfTheWithers extends BasePageItem {
	public PageOfTheWithers(ManaContainerSize size, MagicSource source, ManaPower mana, TextFormatting format,
			boolean hasEffect, Properties properties) {
		super(size, source, mana, format, hasEffect, properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		return this.getAbilities(worldIn, playerIn, handIn, playerIn.getHeldItem(handIn));
	}

	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stack) {

		if (worldIn.isRemote) {
			if (this.getMana(stack) > 0) {
				return ActionResult.resultSuccess(stack);
			} else {
				return ActionResult.resultPass(stack);
			}

		} else {
			if (playerIn.isCreative() || this.getMana(stack) > 0) {
				ServerWorld serverWorld = (ServerWorld) worldIn;
				
				double x = playerIn.getPosX();
				double y = playerIn.getPosY();
				double z = playerIn.getPosZ();
				
				serverWorld.playSound(null, x, y, z, SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f);

				double speed = 1.2d;

				double xD = playerIn.getLookVec().getX() * speed;
				double yD = playerIn.getLookVec().getY() * speed;
				double zD = playerIn.getLookVec().getZ() * speed;

				WitherSkullEntity skull = new WitherSkullEntity(worldIn, playerIn, 0, 0, 0);

				skull.setPosition(playerIn.getPosX() + xD, playerIn.getPosY() + 1.5, playerIn.getPosZ() + zD);
				skull.accelerationX = xD * .1;
				skull.accelerationY = yD * .1;
				skull.accelerationZ = zD * .1;
				serverWorld.addEntity(skull);
				
				this.addMana(stack, -1);
			}

			return ActionResult.resultSuccess(stack);
		}
	}
}
