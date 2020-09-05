package ch.darklions888.SpellStorm.objects.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CorruptedCrystalOreBlock extends CrystalOreBlock {

	public CorruptedCrystalOreBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBlockHarvested(worldIn, pos, state, player);
		if (!worldIn.isRemote()) {
			
			
		}
	}
}
