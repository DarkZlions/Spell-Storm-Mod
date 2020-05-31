package ch.darklions888.SpellStorm.util.helpers.mathhelpers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RayTraceHelper 
{
	   public static RayTraceResult CustomrayTrace(World worldIn, PlayerEntity player, RayTraceContext.FluidMode fluidMode, double range) 
	   {
		      float f = player.rotationPitch;
		      float f1 = player.rotationYaw;
		      Vec3d vec3d = player.getEyePosition(1.0F);
		      float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
		      float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
		      float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
		      float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
		      float f6 = f3 * f4;
		      float f7 = f2 * f4;
		      Vec3d vec3d1 = vec3d.add((double)f6 * range, (double)f5 * range, (double)f7 * range);
		      return worldIn.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
	   }
}
