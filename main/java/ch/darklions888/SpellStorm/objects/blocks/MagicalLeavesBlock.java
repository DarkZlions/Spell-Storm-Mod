package ch.darklions888.SpellStorm.objects.blocks;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MagicalLeavesBlock extends LeavesBlock {
	public static final IntegerProperty DISTANCE_1_10 = IntegerProperty.create("distance", 1, 10);

	public MagicalLeavesBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		for (int i = 0; i < 1; i++) {
			double x = (double) pos.getX() + rand.nextDouble() * (double) 0.1F;
			double y = (double) pos.getY() + rand.nextDouble();
			double z = (double) pos.getZ() + rand.nextDouble();
			worldIn.addParticle(ParticleTypes.ENCHANT, x, y, z, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		worldIn.setBlockState(pos, updateDistance(state, worldIn, pos), 3);
	}

	private static BlockState updateDistance(BlockState state, IWorld worldIn, BlockPos pos) {
		int i = 7;

		try (BlockPos.PooledMutable blockpos$pooledmutable = BlockPos.PooledMutable.retain()) {
			for (Direction direction : Direction.values()) {
				blockpos$pooledmutable.setPos(pos).move(direction);
				i = Math.min(i, getDistance(worldIn.getBlockState(blockpos$pooledmutable)) + 1);
				if (i == 1) {
					break;
				}
			}
		}

		return state.with(DISTANCE, Integer.valueOf(i));
	}

	private static int getDistance(BlockState neighbor) {
		if (neighbor.getBlock() instanceof LogBlock) {
			return 0;
		} else {
			return neighbor.getBlock() instanceof LeavesBlock ? neighbor.get(DISTANCE) : 10;
		}
	}
}
