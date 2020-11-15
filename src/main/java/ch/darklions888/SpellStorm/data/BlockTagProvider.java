package ch.darklions888.SpellStorm.data;

import ch.darklions888.SpellStorm.lib.Lib.Tags;
import ch.darklions888.SpellStorm.registries.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("deprecation")
public class BlockTagProvider extends BlockTagsProvider {

	public BlockTagProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void registerTags() {
		this.getOrCreateBuilder(BlockTags.PLANKS).add(BlockInit.MAGICAL_WOOD_PLANK.get());
		this.getOrCreateBuilder(BlockTags.LOGS_THAT_BURN).add(BlockInit.MAGICAL_WOOD_LOG.get());
		this.getOrCreateBuilder(BlockTags.LEAVES).add(BlockInit.MAGICAL_LEAVES.get());
		
		Registry.BLOCK.getEntries().forEach((map) -> {
			Block block = map.getValue();
			// I'm tired to write each block manually, too much colours
			if (block instanceof ShulkerBoxBlock) {
				this.getOrCreateBuilder(Tags.DUPE_GLITCH_BLOCKS).add(block);
			}
		});
	}
	
	@Override
	public String getName() {
		return "SpellStorm block tags";
	}
}
