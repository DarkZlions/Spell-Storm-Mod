package ch.darklions888.SpellStorm.registries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

import ch.darklions888.SpellStorm.lib.config.ConfigHandler;
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
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@SuppressWarnings({ "unchecked", "deprecation" })
public class WorldFeatureInit {
	
	private static final int crystalOre_veinCount = ConfigHandler.COMMON.crystalOre_veinCount.get();
	private static final int crystalOre_veinSize = ConfigHandler.COMMON.crystalOre_veinSize.get();
	private static final int crystalOre_minY = ConfigHandler.COMMON.crystalOre_minY.get();
	private static final int crystalOre_maxY = ConfigHandler.COMMON.crystalOre_maxY.get();
	
	private static final int corruptedCrystalOre_veinCount = ConfigHandler.COMMON.corruptedCrystalOre_veinCount.get();
	private static final int corruptedCrystalOre_veinSize = ConfigHandler.COMMON.corruptedCrystalOre_veinSize.get();
	private static final int corruptedCrystalOre_minY = ConfigHandler.COMMON.corruptedCrystalOre_minY.get();
	private static final int corruptedCrystalOre_maxY = ConfigHandler.COMMON.corruptedCrystalOre_maxY.get();
	
	public static void init() {
		registerOreConfig(crystalOre_veinCount, crystalOre_veinSize, crystalOre_minY, crystalOre_maxY, BlockInit.CRYSTAL_ORE.get(), OreFeatureConfig.FillerBlockType.field_241882_a);
		registerOreConfig(corruptedCrystalOre_veinCount, corruptedCrystalOre_veinSize, corruptedCrystalOre_minY, corruptedCrystalOre_maxY, BlockInit.CORRUPTED_CRYSTAL_ORE.get(), new BlockMatchRuleTest(Blocks.END_STONE));
	}
	
	public static void setup() {
		addOreToGenerateOverWorld(BlockInit.CRYSTAL_ORE.get());
		addOreToGenerateEnd(BlockInit.CORRUPTED_CRYSTAL_ORE.get());
		addMagicalTree();
	}


	private static void registerOreConfig(int veinCount, int veinSize, int minY, int maxY, Block ore, RuleTest targetBlock) {
		if (ore.getRegistryName() != null) {
			Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, ore.getRegistryName(), Feature.ORE
					.withConfiguration(new OreFeatureConfig(targetBlock,
							ore.getDefaultState(), veinSize))
					.withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(minY, minY, maxY)))
					.func_242728_a().func_242731_b(veinCount));
		}
	}

	private static void addOreToGenerateOverWorld(Block ore) {
		for (Map.Entry<RegistryKey<Biome>, Biome> biome : WorldGenRegistries.BIOME.getEntries()) {
			
			if (!biome.getValue().getCategory().equals(Biome.Category.NETHER) && !biome.getValue().getCategory().equals(Biome.Category.THEEND)) {
				
				addFeatureToBiome(biome.getValue(), GenerationStage.Decoration.UNDERGROUND_ORES, WorldGenRegistries.CONFIGURED_FEATURE.getOrDefault(ore.getRegistryName()));
			}
		}
	}
	
	private static void addOreToGenerateEnd(Block ore) {
		for (Map.Entry<RegistryKey<Biome>, Biome> biome : WorldGenRegistries.BIOME.getEntries()) {
			if (!biome.getKey().equals(Biomes.THE_END))
				addFeatureToBiome(biome.getValue(), GenerationStage.Decoration.UNDERGROUND_ORES, WorldGenRegistries.CONFIGURED_FEATURE.getOrDefault(ore.getRegistryName()));
		}
	}
	

	private static void addMagicalTree() {
		for (Map.Entry<RegistryKey<Biome>, Biome> biome : WorldGenRegistries.BIOME.getEntries()) {
			if (!biome.getValue().getCategory().equals(Biome.Category.NETHER) && !biome.getValue().getCategory().equals(Biome.Category.THEEND)) {
				ConfiguredFeature<BaseTreeFeatureConfig, ?> treeFeature = (ConfiguredFeature<BaseTreeFeatureConfig, ?>) WorldGenRegistries.CONFIGURED_FEATURE.getOrDefault(MagicalTree.getRegistryNameWithPlacement());
				addFeatureToBiome(biome.getValue(), GenerationStage.Decoration.VEGETAL_DECORATION, treeFeature);
			}
		}
	}

	public static void addFeatureToBiome(Biome biome, GenerationStage.Decoration decoration, ConfiguredFeature<?, ?> configuredFeature) {
		List<List<Supplier<ConfiguredFeature<?, ?>>>> biomeFeatures = new ArrayList<>(biome.getGenerationSettings().getFeatures());
		while (biomeFeatures.size() <= decoration.ordinal()) {
			biomeFeatures.add(Lists.newArrayList());
		}
		List<Supplier<ConfiguredFeature<?, ?>>> features = new ArrayList<>(biomeFeatures.get(decoration.ordinal()));
		features.add(() -> configuredFeature);
		biomeFeatures.set(decoration.ordinal(), features);

		ObfuscationReflectionHelper.setPrivateValue(BiomeGenerationSettings.class, biome.getGenerationSettings(), biomeFeatures, "field_242484_f");
	}
}
