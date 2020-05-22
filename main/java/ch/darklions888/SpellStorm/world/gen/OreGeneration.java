package ch.darklions888.SpellStorm.world.gen;

import ch.darklions888.SpellStorm.init.BlockInit;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGeneration 
{
	private static ConfiguredPlacement<CountRangeConfig> CrystalOre = Placement.COUNT_RANGE.configure(new CountRangeConfig(6, 0, 0, 32));
	private static int CrystalOreSize = 4;
	
	public static void GenerationSetup()
	{
		for(Biome biome : ForgeRegistries.BIOMES)
		{
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration
					(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.crystal_ore.getDefaultState(), CrystalOreSize)).withPlacement(CrystalOre));
		}
	}
}
