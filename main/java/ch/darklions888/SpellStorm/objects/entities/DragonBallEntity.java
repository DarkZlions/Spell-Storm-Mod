package ch.darklions888.SpellStorm.objects.entities;

import java.util.List;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class DragonBallEntity extends DamagingProjectileEntity {
	
	public DragonBallEntity(EntityType<? extends DamagingProjectileEntity> entityIn, World worldIn) {
		super(entityIn, worldIn);
	}

	public DragonBallEntity(EntityType<? extends DamagingProjectileEntity> entityIn, double posX, double posY,
			double posZ, double rotX, double rotY, double rotZ, World worldIn) {
		
		super(entityIn, posX, posY, posZ, rotX, rotY, rotZ, worldIn);
	}

	@Override
	   protected void onImpact(RayTraceResult result) {
	      super.onImpact(result);
	      if (result.getType() != RayTraceResult.Type.ENTITY || !((EntityRayTraceResult)result).getEntity().isEntityEqual(this.shootingEntity)) {
	         if (!this.world.isRemote) {
	            List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(4.0D, 2.0D, 4.0D));
	            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ());
	            areaeffectcloudentity.setOwner(this.shootingEntity);
	            areaeffectcloudentity.setParticleData(ParticleTypes.DRAGON_BREATH);
	            areaeffectcloudentity.setRadius(4.0F);
	            areaeffectcloudentity.setDuration(800);
	            areaeffectcloudentity.setRadiusPerTick((7.0F - areaeffectcloudentity.getRadius()) / (float)areaeffectcloudentity.getDuration());
	            areaeffectcloudentity.addEffect(new EffectInstance(Effects.INSTANT_DAMAGE, 1, 2));
	            if (!list.isEmpty()) {
	               for(LivingEntity livingentity : list) {
	                  double d0 = this.getDistanceSq(livingentity);
	                  if (d0 < 16.0D) {
	                     areaeffectcloudentity.setPosition(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ());
	                     break;
	                  }
	               }
	            }

	            this.world.playEvent(2006, new BlockPos(this), 0);
	            this.world.addEntity(areaeffectcloudentity);
	            this.remove();
	         }

	      }
	   }
		

		@Override
	   public boolean canBeCollidedWith() {
	      return false;
	   }
	   
	   @Override
	   public boolean attackEntityFrom(DamageSource source, float amount) {
	      return false;
	   }

	   @Override
	   protected IParticleData getParticle() {
	      return ParticleTypes.DRAGON_BREATH;
	   }

	   @Override
	   protected boolean isFireballFiery() {
	      return false;
	   }
	
}
