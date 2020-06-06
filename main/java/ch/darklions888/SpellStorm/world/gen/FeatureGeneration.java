package ch.darklions888.SpellStorm.world.gen;

import ch.darklions888.SpellStorm.world.gen.biome.trees.MagicalTree;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class FeatureGeneration 
{
	public static void genFeatures()
	{
		for(Biome biome : ForgeRegistries.BIOMES)
		{
			biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
					Feature.NORMAL_TREE.withConfiguration(MagicalTree.MAGICAL_TREE_CONFIG).withPlacement(
							Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.05F, 1))));
		}
	}
}
