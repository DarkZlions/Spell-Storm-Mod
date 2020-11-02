package ch.darklions888.SpellStorm.data;

import ch.darklions888.SpellStorm.data.loot.BlockLootTableProvider;
import ch.darklions888.SpellStorm.data.recipe.RecipeDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataGenerators {

	public static void gatherData(GatherDataEvent event) {
   
    	DataGenerator generator = event.getGenerator();
    	
		if (event.includeServer()) {
			generator.addProvider(new RecipeDataProvider(generator));
			generator.addProvider(new AdvancementsDataProvider(generator));
			generator.addProvider(new BlockLootTableProvider(generator));
			BlockTagProvider blocktagProvider = new BlockTagProvider(generator);
			generator.addProvider(blocktagProvider);
			generator.addProvider(new ItemTagProvider(generator, blocktagProvider));
		}
		
		if (event.includeClient()) {
			
		}
	}
}
