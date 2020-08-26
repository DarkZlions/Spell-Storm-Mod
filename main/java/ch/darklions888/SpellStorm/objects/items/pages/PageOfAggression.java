package ch.darklions888.SpellStorm.objects.items.pages;

import java.util.List;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerSize;
import ch.darklions888.SpellStorm.lib.ManaPower;
import ch.darklions888.SpellStorm.objects.items.BasePageItem;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PageOfAggression extends BasePageItem {

	private static final String MOB_TAG = "spellstrom_aggressive_mob_tag";

	public PageOfAggression(ManaContainerSize size, MagicSource source, ManaPower mana, TextFormatting format,
			boolean hasEffect, Properties properties) {
		super(size, source, mana, format, hasEffect, properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		return this.getAbilities(worldIn, playerIn, handIn, playerIn.getHeldItem(handIn));
	}

	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stackIn) {

		if (worldIn.isRemote) {

			return ActionResult.resultPass(stackIn);

		} else {

			ServerWorld serverWorld = (ServerWorld) worldIn;

			if (playerIn.isCreative() || this.getMana(stackIn) > 0) {
				List<MobEntity> entityList;
				double x = playerIn.getPosX();
				double y = playerIn.getPosY();
				double z = playerIn.getPosZ();

				entityList = serverWorld.getEntitiesWithinAABB(MobEntity.class,
						new AxisAlignedBB(x + 7, y + 5, z + 7, x - 7, y - 2, z - 7));
				boolean hasTag = false;
				for (MobEntity e : entityList) {
					hasTag = e.getTags().contains(MOB_TAG);
					if (!hasTag) {
						for (int i = 0; i < 4; i++) {
							((ServerWorld) worldIn).spawnParticle(ParticleTypes.ANGRY_VILLAGER, e.getPosXRandom(0.6d),
									e.getPosYRandom(), e.getPosZRandom(0.6d), 1, 0, 0, 0, 1.0d);
						}

						e.targetSelector.addGoal(-1,
								new NearestAttackableTargetGoal<>(e, MobEntity.class, false, true));
						e.addTag(MOB_TAG);
						if (e.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) == null
								|| !(e instanceof SlimeEntity)) {
							try {
								e.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)
										.setBaseValue(e.getMaxHealth() / 3);

								if (e instanceof CreatureEntity) {
									e.targetSelector.addGoal(-1, new MeleeAttackGoal((CreatureEntity) e,
											e.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() * 5,
											false));
								}
							} catch (Exception exception) {
							}
						}
						this.addMana(stackIn, -1);
					}
				}
				serverWorld.playSound(null, x, y, z, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.PLAYERS, 1.0f, 1.0f);

				if (entityList.size() > 0) {
					return ActionResult.resultSuccess(stackIn);
				} else {
					return ActionResult.resultFail(stackIn);
				}
			} else {
				return ActionResult.resultFail(stackIn);
			}
		}
	}
}
