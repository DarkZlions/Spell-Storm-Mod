package ch.darklions888.SpellStorm.objects.entities.projectiles;

import ch.darklions888.SpellStorm.registries.EntityInit;
import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class MagicalFireballEntity extends DamagingProjectileEntity implements IRendersAsItem {
	
	
	public MagicalFireballEntity(EntityType<? extends MagicalFireballEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public MagicalFireballEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(EntityInit.MAGICAL_FIREBALL.get(), shooter, accelX, accelY, accelZ, worldIn);
		this.shooter = shooter;
	}
	
	public MagicalFireballEntity(World worldIn, LivingEntity shooter) {
		super(EntityInit.MAGICAL_FIREBALL.get(), shooter, 0, 0, 0, worldIn);
		this.shooter = shooter;
	}

	public MagicalFireballEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(EntityInit.MAGICAL_FIREBALL.get(), x, y, z, accelX, accelY, accelZ, worldIn);
	}
	
	public MagicalFireballEntity(World worldIn, LivingEntity shooter, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(EntityInit.MAGICAL_FIREBALL.get(), x, y, z, accelX, accelY, accelZ, worldIn);
		this.shooter = shooter;
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(ItemInit.MAGICAL_FIREBALL.get());
	}
	
	LivingEntity shooter = null;
	
	@Override
	protected void onEntityHit(EntityRayTraceResult result) {
		super.onEntityHit(result);
		
		float damageValue = 8.0f;
		
		if (!this.world.isRemote()) {
			Entity entity = result.getEntity();

			Entity projectileE = this.func_234616_v_();
			int fireTimer = entity.getFireTimer();
			entity.setFire(7);
			boolean canAttack = false;
			if (this.shooter != null) {
				if (this.shooter instanceof PlayerEntity)
					entity.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity)this.shooter), damageValue);
				else
					entity.attackEntityFrom(DamageSource.causeMobDamage(this.shooter), damageValue);
			}
			else	
				canAttack= entity.attackEntityFrom(DamageSource.MAGIC, damageValue);
			
			if (!canAttack) {
				entity.forceFireTicks(fireTimer);
			}
			else if (projectileE instanceof LivingEntity) {
				this.applyEnchantments((LivingEntity) projectileE, entity);
			}
		}
	}
	
	@Override
	protected void func_230299_a_(BlockRayTraceResult result) {
		super.func_230299_a_(result);
		if (!this.world.isRemote) {
			Entity entity = this.func_234616_v_();
			if (entity == null || !(entity instanceof MobEntity) || this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)|| ForgeEventFactory.getMobGriefingEvent(this.world, this.getEntity())) {
				BlockPos blockpos = result.getPos().offset(result.getFace());
				if (this.world.isAirBlock(blockpos)) {
					this.world.setBlockState(blockpos, AbstractFireBlock.getFireForPlacement(this.world, blockpos));
				}
			}
		}
	}
	
	@Override
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		if (!this.world.isRemote) {				
			for (int i = 0; i < 3; i++) {
				((ServerWorld)this.getEntityWorld()).spawnParticle(ParticleTypes.LAVA, this.getPosXRandom(0.6d), this.getPosYEye(), this.getPosZRandom(0.6d), 1,
						this.getLookVec().x, this.getLookVec().y, this.getLookVec().z, 1.0d);
			}
			
			double x = this.getPosX();
			double y = this.getPosY();
			double z = this.getPosZ();	
			
			((ServerWorld)this.getEntityWorld()).playSound(null, x, y, z, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.NEUTRAL, 1.0f, 1.0f);
		
			this.remove();
		}

	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
