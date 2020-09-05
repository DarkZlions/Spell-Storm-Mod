package ch.darklions888.SpellStorm.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

import ch.darklions888.SpellStorm.world.gen.biome.trees.MagicalTree;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class WorldFeatureInit {
	public static void init() {
		registerOreConfig(3, 8, 5, 20, BlockInit.CRYSTAL_ORE.get(), OreFeatureConfig.FillerBlockType.field_241882_a);
		registerOreConfig(2, 3, 0, 40, BlockInit.CORRUPTED_CRYSTAL_ORE.get(), new BlockMatchRuleTest(Blocks.END_STONE));
	}
	
	public static void setup() {
		addOreToGenerateOverWorld(BlockInit.CRYSTAL_ORE.get());
		addOreToGenerateEnd(BlockInit.CORRUPTED_CRYSTAL_ORE.get());
		addMagicalTree();
	}


	private static void registerOreConfig(int veinCount, int veinSize, int minY, int maxY, Block ore, RuleTest targetBlock) {
		if (ore.getRegistryName() != null) {
			Registry.register(WorldGenRegistries.field_243653_e, ore.getRegistryName(), Feature.ORE
					.withConfiguration(new OreFeatureConfig(targetBlock,
							ore.getDefaultState(), veinSize))
					.withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(minY, minY, maxY)))
					.func_242728_a().func_242731_b(veinCount));
		}
	}

	private static void addOreToGenerateOverWorld(Block ore) {
		for (Map.Entry<RegistryKey<Biome>, Biome> biome : WorldGenRegistries.field_243657_i.getEntries()) {
			
			if (!biome.getValue().getCategory().equals(Biome.Category.NETHER) && !biome.getValue().getCategory().equals(Biome.Category.THEEND)) {
				
				addFeatureToBiome(biome.getValue(), GenerationStage.Decoration.UNDERGROUND_ORES, WorldGenRegistries.field_243653_e.getOrDefault(ore.getRegistryName()));
			}
		}
	}
	
	private static void addOreToGenerateEnd(Block ore) {
		for (Map.Entry<RegistryKey<Biome>, Biome> biome : WorldGenRegistries.field_243657_i.getEntries()) {
			if (biome.getKey().equals(Biomes.END_HIGHLANDS))
				addFeatureToBiome(biome.getValue(), GenerationStage.Decoration.UNDERGROUND_ORES, WorldGenRegistries.field_243653_e.getOrDefault(ore.getRegistryName()));
		}
	}
	
	private static void addMagicalTree() {
		for (Map.Entry<RegistryKey<Biome>, Biome> biome : WorldGenRegistries.field_243657_i.getEntries()) {
			if (!biome.getValue().getCategory().equals(Biome.Category.NETHER) && !biome.getValue().getCategory().equals(Biome.Category.THEEND)) {
				
				addFeatureToBiome(biome.getValue(), GenerationStage.Decoration.VEGETAL_DECORATION, WorldGenRegistries.field_243653_e.getOrDefault(MagicalTree.getRegistryName()));
			}
		}
	}

	public static void addFeatureToBiome(Biome biome, GenerationStage.Decoration decoration,
			ConfiguredFeature<?, ?> configuredFeature) {
		List<List<Supplier<ConfiguredFeature<?, ?>>>> biomeFeatures = new ArrayList<>(
				biome.func_242440_e().func_242498_c());
		while (biomeFeatures.size() <= decoration.ordinal()) {
			biomeFeatures.add(Lists.newArrayList());
		}
		List<Supplier<ConfiguredFeature<?, ?>>> features = new ArrayList<>(biomeFeatures.get(decoration.ordinal()));
		features.add(() -> configuredFeature);
		biomeFeatures.set(decoration.ordinal(), features);

		ObfuscationReflectionHelper.setPrivateValue(BiomeGenerationSettings.class, biome.func_242440_e(), biomeFeatures,
				"field_242484_f");
	}
}
