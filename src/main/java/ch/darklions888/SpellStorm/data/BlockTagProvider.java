package ch.darklions888.SpellStorm.data;

import ch.darklions888.SpellStorm.registries.BlockInit;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;

public class BlockTagProvider extends BlockTagsProvider {

	public BlockTagProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void registerTags() {
		this.getOrCreateBuilder(BlockTags.PLANKS).add(BlockInit.MAGICAL_WOOD_PLANK.get());
		this.getOrCreateBuilder(BlockTags.LOGS_THAT_BURN).add(BlockInit.MAGICAL_WOOD_LOG.get());
		this.getOrCreateBuilder(BlockTags.LEAVES).add(BlockInit.MAGICAL_LEAVES.get());
	}
	
	@Override
	public String getName() {
		return "SpellStorm block tags";
	}
}
