package ch.darklions888.SpellStorm.data;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;

public class ItemTagProvider extends ItemTagsProvider{

	@SuppressWarnings("deprecation")
	public ItemTagProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider) {
		super(dataGenerator, blockTagProvider);
	}

	@Override
	protected void registerTags() {
		
		this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
		this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
		this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
		
		this.getOrCreateBuilder(Lib.Tags.SPELL_PAGES_ITEMS).add(ItemInit.PAGE_OF_AGGRESSION.get(),
				ItemInit.PAGE_OF_DRAGONBALL.get(), ItemInit.PAGE_OF_FALLING_ROCK.get(), ItemInit.PAGE_OF_FANGS.get(),
				ItemInit.PAGE_OF_FIREBALLS.get(), ItemInit.PAGE_OF_HEALING.get(), ItemInit.PAGE_OF_MINING.get(),
				ItemInit.PAGE_OF_THE_WITHER_SKULL.get(), ItemInit.PAGE_OF_THUNDER.get());
	}
	
	@Override
	public String getName() {
		return "SpellStorm item tags";
	}
}
