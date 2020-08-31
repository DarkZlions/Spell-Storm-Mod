package ch.darklions888.SpellStorm.objects.items.pages;

import java.util.List;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerSize;
import ch.darklions888.SpellStorm.lib.ManaPower;
import ch.darklions888.SpellStorm.objects.items.BasePageItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PageOfFallingRocks extends BasePageItem {

	public PageOfFallingRocks(ManaContainerSize size, MagicSource source, ManaPower mana, TextFormatting format,
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
			return ActionResult.resultPass(stack);
		} else {
			double x = playerIn.getPosX();
			double y = 260.0d;
			double z = playerIn.getPosZ();

			if (playerIn.isCreative() || this.getMana(stack) >= 60) {
				
				((ServerWorld)worldIn).playSound(null, x, playerIn.getPosY(), z, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f);
				((ServerWorld)worldIn).spawnParticle(ParticleTypes.ASH, x, playerIn.getPosY(), y, 9, 0, 0, 0, .5f);
				
				FireballEntity entity = new FireballEntity(worldIn, playerIn, 0.0d, -2.5d, 0.0d);
				entity.setPosition(x, y, z);
				entity.accelerationX = 0;
				entity.accelerationY = -0.15d;
				entity.accelerationZ = 0;
				entity.explosionPower = 16;

				worldIn.addEntity(entity);

				if (!playerIn.isCreative())
					this.setMana(stack, -60);

				return ActionResult.resultSuccess(stack);
			} else {
				return ActionResult.resultPass(stack);
			}
		}
	}

	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new StringTextComponent("This page need full mana to use."));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
