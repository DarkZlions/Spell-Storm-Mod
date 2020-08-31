package ch.darklions888.SpellStorm.world.gen.biome;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

public class OreFeature extends Feature<OreConfig> {

	public OreFeature(Codec<OreConfig> codec) {
		super(codec);
	}

	@Override
	public boolean func_241855_a(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, OreConfig config) {
		ChunkPos chunkPos = new ChunkPos(pos);
		
		BlockPos.Mutable mPos = new BlockPos.Mutable();
		int height = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
		IChunk chunk = worldIn.getChunk(pos);
		
		for (int y = 0; y < height; y++) {
			mPos.setY(y);
			for (int x = chunkPos.getXStart(); x <= chunkPos.getXEnd(); x++) {
				mPos.setX(x);
				for (int z = chunkPos.getZStart(); z <= chunkPos.getZEnd(); z++) {
					mPos.setZ(z);
					if (chunk.getBlockState(mPos).getBlock() == config.target.getBlock() && rand.nextFloat() < config.chance) {
						chunk.setBlockState(mPos, config.state, false);
					}
				}
			}
		}
		
		return false;
	}

}
