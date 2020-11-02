package ch.darklions888.SpellStorm.data.loot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.registries.BlockInit;
import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.TableBonus;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class BlockLootTableProvider extends BlockLootTables implements IDataProvider {

	private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
	private static final ILootCondition.IBuilder NO_SILK_TOUCH = SILK_TOUCH.inverted();
	private static final ILootCondition.IBuilder SHEARS = MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS));
	private static final ILootCondition.IBuilder SILK_TOUCH_OR_SHEARS = SHEARS.alternative(SILK_TOUCH);
	private static final ILootCondition.IBuilder NOT_SILK_TOUCH_OR_SHEARS = SILK_TOUCH_OR_SHEARS.inverted();
	private static final Set<Item> IMMUNE_TO_EXPLOSIONS = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(IItemProvider::asItem).collect(ImmutableSet.toImmutableSet());
	private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
	private static final float[] RARE_SAPLING_DROP_RATES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private final DataGenerator dataGenerator;
	private final Map<Block, Function<Block, LootTable.Builder>> functionTable = new HashMap<>();
	
	public BlockLootTableProvider(DataGenerator dataGenerator) {
		this.dataGenerator = dataGenerator;
		
		functionTable.put(BlockInit.CORRUPTED_CRYSTAL_ORE.get(), b -> BlockLootTableProvider.droppingWithSilkTouch(b, ItemInit.CORRUPTED_CRYSTAL.get()));
		functionTable.put(BlockInit.CRYSTAL_ORE.get(), b -> BlockLootTableProvider.droppingWithSilkTouch(b, ItemInit.CRYSTAL.get()));
		functionTable.put(BlockInit.MAGICAL_LEAVES.get(), b -> BlockLootTableProvider.droppingWithChancesSticksAndOther(b, BlockInit.MAGICAL_TREE_SAPLING.get(), ItemInit.CRYSTAL_PIECES.get(), DEFAULT_SAPLING_DROP_RATES));	
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void act(DirectoryCache cache) throws IOException {
		Map<ResourceLocation, LootTable.Builder> tables = new HashMap<>();

		for (Block b : Registry.BLOCK) {
			ResourceLocation id = Registry.BLOCK.getKey(b);
			if (!Lib.MOD_ID.equals(id.getNamespace())) {
				continue;
			}
			Function<Block, LootTable.Builder> func = functionTable.getOrDefault(b, BlockLootTableProvider::dropping);
			tables.put(id, func.apply(b));
		}

		for (Map.Entry<ResourceLocation, LootTable.Builder> e : tables.entrySet()) {
			Path path = getPath(dataGenerator.getOutputFolder(), e.getKey());
			IDataProvider.save(GSON, cache, LootTableManager.toJson(e.getValue().setParameterSet(LootParameterSets.BLOCK).build()), path);
		}
	}
	
	protected static LootTable.Builder droppingWithChancesSticksAndOther(Block block, Block sapling, IItemProvider itemIn, float... chances) {
		return droppingWithChancesAndSticks(block, sapling, chances)
				.addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(NOT_SILK_TOUCH_OR_SHEARS)
						.addEntry(withSurvivesExplosion(block, ItemLootEntry.builder(itemIn))
								.acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.005F, 0.0055555557F,
										0.00625F, 0.008333334F, 0.025F))));
	}

	private static Path getPath(Path root, ResourceLocation id) {
		return root.resolve("data/" + id.getNamespace() + "/loot_tables/blocks/" + id.getPath() + ".json");
	}
	
	@Override
	public String getName() {
		return null;
	}

	
}
