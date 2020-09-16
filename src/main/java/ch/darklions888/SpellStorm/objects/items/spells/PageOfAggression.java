package ch.darklions888.SpellStorm.objects.items.spells;

import java.util.List;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerType;
import ch.darklions888.SpellStorm.lib.config.MagicalPagesConfig;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PageOfAggression extends AbstractPageItem {

	private static final String MOB_TAG = "spellstrom_aggressive_mob_tag";

	public PageOfAggression(Properties properties) {
		super(ManaContainerType.MEDIUM, MagicSource.DARKMAGIC, MagicalPagesConfig.manaConsumption_pageOfAggression.get(), TextFormatting.DARK_RED, true, properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		return this.getAbilities(worldIn, playerIn, handIn, playerIn.getHeldItem(handIn), null);
	}

	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stackIn, ItemStack bookIn) {

		if (worldIn.isRemote) {

			return ActionResult.resultPass(stackIn);

		} else {

			ServerWorld serverWorld = (ServerWorld) worldIn;

			if (playerIn.isCreative() || this.getManaValue(stackIn, this.defaultManaSource.getId()) >= this.manaConsumption) {
				List<MobEntity> entityList;
				double x = playerIn.getPosX();
				double y = playerIn.getPosY();
				double z = playerIn.getPosZ();

				entityList = serverWorld.getEntitiesWithinAABB(MobEntity.class,	new AxisAlignedBB(x + 7, y + 5, z + 7, x - 7, y - 2, z - 7));
				
				for (MobEntity e : entityList) {
					if (!e.getTags().contains(MOB_TAG) && e.getAttributeManager().hasAttributeInstance(Attributes.ATTACK_DAMAGE)) {

						for (int i = 0; i < 4; i++) {
							((ServerWorld) worldIn).spawnParticle(ParticleTypes.ANGRY_VILLAGER, e.getPosXRandom(0.6d), e.getPosYRandom(), e.getPosZRandom(0.6d), 1, 0, 0, 0, 1.0d);
						}
						e.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(e, MobEntity.class, true, true));
						e.addTag(MOB_TAG);
						/*
						if (e.getAttribute(Attributes.ATTACK_DAMAGE) == null || !(e instanceof SlimeEntity)) {
							try {
								Multimap<Attribute, AttributeModifier> attriMap = HashMultimap.create();
							     Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
							     builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Spell modifier", (double)e.getHealth()/3, AttributeModifier.Operation.ADDITION));
							     builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Spell modifier", (double)1.0d, AttributeModifier.Operation.ADDITION));
							     attriMap = builder.build();
							     e.getAttributeManager().reapplyModifiers(attriMap);

								e.targetSelector.addGoal(0, new MeleeAttackGoal((CreatureEntity)e, 2.0d, false));
							} catch (Exception exception) {exception.printStackTrace();}
						}
						*/
						if (!playerIn.isCreative())
							this.consumMana(stackIn, this.defaultManaSource);
					}
					
					//serverWorld.playSound(null, x, y, z, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.PLAYERS, 1.0f, 1.0f);
				}


				if (entityList.size() > 0) {
					return ActionResult.resultSuccess(stackIn);
				} else {
					return ActionResult.resultPass(stackIn);
				}
			} else {
				return ActionResult.resultPass(stackIn);
			}
		}
	}

	@Override
	public int getInkColor() {
		return 0x8d3535;
	}
}
