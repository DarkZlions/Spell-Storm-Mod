package ch.darklions888.SpellStorm.objects.items.pages;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerSize;
import ch.darklions888.SpellStorm.lib.ManaPower;
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
import net.minecraft.world.server.ServerWorld;

public class PageOfFireballs extends BasePageItem {

	public PageOfFireballs(ManaContainerSize size, MagicSource source, ManaPower mana, TextFormatting format,
			boolean hasEffect, Properties properties) {
		super(size, source, mana, format, hasEffect, properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		return getAbilities(worldIn, playerIn, handIn, playerIn.getHeldItem(handIn));
	}

	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stack) {

		if (worldIn.isRemote) {
			return ActionResult.resultFail(stack);
			
		} else {
			if (playerIn.isCreative() || this.getMana(stack) > 0) {
				
				double x = playerIn.getPosX();
				double y = playerIn.getPosY();
				double z = playerIn.getPosZ();				
				((ServerWorld)worldIn).playSound(null, x, y, z, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0f, 1.0f);
				
				for (int i = 0; i < 10; i++) {
					((ServerWorld)worldIn).spawnParticle(ParticleTypes.LAVA, playerIn.getPosXRandom(0.6d), playerIn.getPosYRandom(), playerIn.getPosZRandom(0.6d), 1,
							playerIn.getLookVec().x, playerIn.getLookVec().y, playerIn.getLookVec().z, 1.0d);
				}

				
				double accel = 1.5d; // Bonus acceleration for more momentum or something like this

				for (int i = 0; i < 3; i++) {
					SmallFireballEntity entity = new SmallFireballEntity(worldIn,
							x + playerIn.getLookVec().x, playerIn.getPosY() + 1.2d,
							z + playerIn.getLookVec().z, playerIn.getLookVec().x * accel,
							playerIn.getLookVec().y * accel, playerIn.getLookVec().z * accel);

					worldIn.addEntity(entity);
				}
				if (!playerIn.isCreative())
					this.addMana(stack, -1);

				return ActionResult.resultSuccess(stack);
			} else {
				return ActionResult.resultFail(stack);
			}
		}
	}
}
