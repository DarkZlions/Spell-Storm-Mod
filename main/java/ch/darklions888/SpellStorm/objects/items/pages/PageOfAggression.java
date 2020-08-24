package ch.darklions888.SpellStorm.objects.items.pages;

import java.util.List;

import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaContainerSize;
import ch.darklions888.SpellStorm.enums.ManaPower;
import ch.darklions888.SpellStorm.objects.items.BasePageItem;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PageOfAggression extends BasePageItem
{

	public PageOfAggression(ManaContainerSize size, MagicSource source, ManaPower mana, TextFormatting format, boolean hasEffect, Properties properties) 
	{
		super(size, source, mana, format, hasEffect, properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		return this.getAbilities(worldIn, playerIn, handIn, playerIn.getHeldItem(handIn));
	}
	
	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stackIn) {
		
		if (worldIn.isRemote) {
			
			return ActionResult.resultPass(stackIn);
			
		} else {
			
			ServerWorld serverWorld = (ServerWorld) worldIn;
			
			if (this.getMana(stackIn) > 0) {
				List<MobEntity> entityList;
				double x = playerIn.getPosX();
				double y = playerIn.getPosY();
				double z = playerIn.getPosZ();

				entityList = serverWorld.getEntitiesWithinAABB(MobEntity.class,
						new AxisAlignedBB(x + 7, y + 5, z + 7, x - 7, y - 2, z - 7));

				for (MobEntity entity : entityList) {
					
					/* Later a packet handler will handle the particles
					 * This will happen if I figure out how to use networking
					 * 
					 * serverWorld.addParticle(ParticleTypes.ANGRY_VILLAGER, true, entity.getPosX(),
					 * entity.getPosY() + 1.2d, entity.getPosZ(), 1, 1, 1);
					 */
					
					entity.targetSelector.addGoal(-1,
							new NearestAttackableTargetGoal<>(entity, MobEntity.class, false, true));

					if (entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) == null
							|| !(entity instanceof SlimeEntity)) {
						try {
							entity.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)
									.setBaseValue(entity.getMaxHealth() / 3);

							if (entity instanceof CreatureEntity) {
								entity.targetSelector.addGoal(-1, new MeleeAttackGoal((CreatureEntity) entity,
										entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() * 5,
										false));
							}
						} catch (Exception e) {
							System.out.println(e);
						}
					}

				}

				serverWorld.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ENDER_EYE_DEATH,
						SoundCategory.PLAYERS, 1.0f, 0.1f);

				if (entityList.size() > 0) {
					this.addMana(stackIn, -1);
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
