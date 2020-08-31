package ch.darklions888.SpellStorm.objects.blocks;

import java.util.Random;

import ch.darklions888.SpellStorm.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CustomOreBlock extends Block {

	public CustomOreBlock(Properties properties) {
		super(properties);
	}

	protected int getExperience(Random rand) {
		if (this == BlockInit.CRYSTAL_ORE.get())
			return MathHelper.nextInt(rand, 3, 4);
		else
			return 0;
	}

	@SuppressWarnings("deprecation")
	public void spawnAdditionalDrops(BlockState state, World worldIn, BlockPos pos, ItemStack stack) {
		super.spawnAdditionalDrops(state, (ServerWorld) worldIn, pos, stack);
	}

	@Override
	public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
		return silktouch == 0 ? this.getExperience(RANDOM) : fortune > 0 ? this.getExperience(RANDOM) + fortune : 0;
	}

}
