package ch.darklions888.SpellStorm.objects.blocks;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CrystalOreBlock extends CustomOreBlock
{

	public CrystalOreBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) 
	{
	      worldIn.addParticle(ParticleTypes.PORTAL, (double)pos.getX() + (double)rand.nextFloat(), (double)pos.getY() - .3 +  (double)rand.nextFloat(), (double)pos.getZ() + (double)rand.nextFloat(), 0.0D, 0.0D, 0.0D);
	}
}
