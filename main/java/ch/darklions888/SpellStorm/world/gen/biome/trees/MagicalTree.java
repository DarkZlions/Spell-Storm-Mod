package ch.darklions888.SpellStorm.world.gen.biome.trees;

import java.util.Random;

import ch.darklions888.SpellStorm.init.BlockInit;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

import static ch.darklions888.SpellStorm.lib.Lib.ResourceLocations.MAGICAL_TREE_REGISTRY_NAME;

public class MagicalTree extends Tree {

	private static final ResourceLocation REGISTRY_NAME = MAGICAL_TREE_REGISTRY_NAME;
	
	@SuppressWarnings("unchecked")
	public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> MAGIC_TREE_CONFIG =  (ConfiguredFeature<BaseTreeFeatureConfig, ?>) register(
			REGISTRY_NAME,
			Feature.field_236291_c_
					.withConfiguration((new BaseTreeFeatureConfig.Builder(
							new SimpleBlockStateProvider(BlockInit.MAGICAL_WOOD_LOG.get().getDefaultState()),
							new SimpleBlockStateProvider(BlockInit.MAGICAL_LEAVES.get().getDefaultState()),
							new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
							new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).build())
					.withPlacement(Features.Placements.field_244001_l)
					.withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(0, 0.01f, 1))));

	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean b) {
		return MAGIC_TREE_CONFIG;
	}

	private static ConfiguredFeature<?, ?> register(ResourceLocation name,
			ConfiguredFeature<?, ?> configuredFeature) {
		return Registry.register(WorldGenRegistries.field_243653_e, name, configuredFeature);
	}
	
	public static ResourceLocation getRegistryName() {
		return REGISTRY_NAME;
	}
}
