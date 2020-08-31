package ch.darklions888.SpellStorm.world.gen.biome.trees;

import java.util.Random;

import ch.darklions888.SpellStorm.init.BlockInit;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;

public class MagicalTree extends Tree
{
	public static final TreeFeatureConfig MAGICAL_TREE_CONFIG = (new TreeFeatureConfig.Builder
			(new SimpleBlockStateProvider(BlockInit.MAGICAL_WOOD_LOG.get().getDefaultState()),
			new SimpleBlockStateProvider(BlockInit.MAGICAL_LEAVES.get().getDefaultState()),
			new BlobFoliagePlacer(2, 0))).baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines()
			.setSapling((net.minecraftforge.common.IPlantable)BlockInit.MAGICAL_TREE_SAPLING.get()).build();

	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean b) 
	{
		return Feature.NORMAL_TREE.withConfiguration(MAGICAL_TREE_CONFIG);
	}
	
	
}
