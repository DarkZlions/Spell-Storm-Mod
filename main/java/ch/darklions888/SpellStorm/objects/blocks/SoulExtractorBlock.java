package ch.darklions888.SpellStorm.objects.blocks;

import java.util.Random;
import java.util.stream.Stream;

import ch.darklions888.SpellStorm.init.ParticlesInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SoulExtractorBlock extends BaseBlock
{
	private static final VoxelShape SHAPE = Stream.of(
			Block.makeCuboidShape(4, 14, 12, 12, 21, 13),
			Block.makeCuboidShape(12, 14, 12, 13, 21, 13),
			Block.makeCuboidShape(12, 14, 3, 13, 21, 4),
			Block.makeCuboidShape(3, 14, 3, 4, 21, 4),
			Block.makeCuboidShape(3, 14, 12, 4, 21, 13),
			Block.makeCuboidShape(4, 14, 3, 12, 21, 4),
			Block.makeCuboidShape(3, 14, 4, 4, 21, 12),
			Block.makeCuboidShape(12, 14, 4, 13, 21, 12),
			Block.makeCuboidShape(0, 0, 0, 16, 12, 16),
			Block.makeCuboidShape(3, 21, 3, 13, 22, 13),
			Block.makeCuboidShape(3, 13, 3, 13, 14, 13),
			Block.makeCuboidShape(1, 12, 1, 15, 13, 15)
			).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
	
	public SoulExtractorBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return SHAPE;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) 
	{
	      for(int i = 0; i < 2; ++i) 
	      {
	          int j = rand.nextInt(2) * 2 - 1;
	          int k = rand.nextInt(2) * 2 - 1;
	          double d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
	          double d1 = (double)((float)pos.getY() + rand.nextFloat()) + .5d;
	          double d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)k;
	          double d3 = (double)(rand.nextFloat() * (float)j);
	          double d4 = ((double)rand.nextFloat() - 0.5D) * 0.140D;
	          double d5 = (double)(rand.nextFloat() * (float)k);
	          worldIn.addParticle(ParticlesInit.SOULS_PARTICLE.get(), d0, d1, d2, d3, d4, d5);
	      }
	}
}
