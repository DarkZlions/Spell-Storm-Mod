package ch.darklions888.SpellStorm.lib;

import java.util.function.Predicate;

import ch.darklions888.SpellStorm.registries.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class Lib {
	
	public static final String MOD_ID = "spellstorm";
	
	public static final class RegistryNames {
		public static ResourceLocation prefix(String key) {
			return new ResourceLocation(MOD_ID, key);
		}
		
		// ConfiguredFeature
		public static final ResourceLocation MAGICAL_TREE_REGISTRY_NAME_WP = prefix("magical_tree_without_placement");
		public static final ResourceLocation MAGICAL_TREE_REGISTRY_NAME_NP = prefix("magical_tree_with_placement");
		
		// Blocks
		
		// Entities
		public static final String MAGICAL_FIREBALL_ENTITY_STR = "magical_fireball_entity";
		public static final ResourceLocation MAGICAL_FIREBALL_ENTITY_RS = prefix(MAGICAL_FIREBALL_ENTITY_STR);
		
		// Sound
		public static final ResourceLocation ETERNAL_SCREAMING_SOUND = prefix("eternal_screaming");
		public static final ResourceLocation HAUNTED_SOULS_SOUND = prefix("haunted_souls");
		public static final ResourceLocation MAGICAL_FURNACE_CRACKLES_SOUND = prefix("magical_furnace_crackles");
		
		// Texturelocations
		public static final ResourceLocation BOOK_OF_SPELLS_SCREEN_BACKGROUND_TEXUTRE = prefix("textures/gui/container/bookofspells_container.png");
		public static final ResourceLocation MANA_INFUSER_SCREEN_BACKGROUND_TEXTURE = prefix("textures/gui/container/manainfuser_container.png");
		public static final ResourceLocation SOUL_EXTRACTOR_SCREEN_BACKGROUND_TEXTURE = prefix("textures/gui/container/soulextractor_container.png");
		public static final ResourceLocation MAGICAL_FORGE_SCREEN_BACKGROUND_TEXTURE = prefix("textures/gui/container/magical_forge_container.png");
		public static final ResourceLocation GATEWAY_TEXTURE = prefix("textures/entities/gateway.png");
	
		// Networking
		public static final ResourceLocation NETWORK_CHANNEL = prefix("channel");
		
		// Recipe
		public static final String MAGICAL_FORGE_RECIPE_ID = "magical_forge_merging";
		public static final ResourceLocation MAGICAL_FORGE_RECIPE_RESOURCELOCATION = prefix(MAGICAL_FORGE_RECIPE_ID);
	}
	
	public static final class TextComponents {
		public static TranslationTextComponent prefix(String text) {
			return new TranslationTextComponent(new String(Lib.MOD_ID + "misc" + "." + text));
		}
		
		public static final TranslationTextComponent MANA = prefix("mana");
		public static final TranslationTextComponent MANA_POWER = prefix("mana_power");
		public static final TranslationTextComponent MANA_SOURCE = prefix("mana_source");
		public static final TranslationTextComponent MANA_LEFT = prefix("mana_left");
		public static final TranslationTextComponent SOULCATCHER_CONTAINS = prefix("soulcatcher_contains");
		public static final TranslationTextComponent SOULCATCHER_IS_EMPTY = prefix("soulcatcher_is_empty");
		public static final TranslationTextComponent SOULCATCHER_MOB_HAS = prefix("description_soulcatcher_has_mob");
		public static final TranslationTextComponent DESC_MANA_CONTAINER_ITEM = prefix("description_mana_container");
		public static final TranslationTextComponent DESC_BOOK_OF_SPELLS_0 = prefix("description_book_of_spells_0");
		public static final TranslationTextComponent DESC_BOOK_OF_SPELLS_1 = prefix("description_book_of_spells_1");
		public static final TranslationTextComponent DESC_BOOK_OF_SPELLS_2 = prefix("description_book_of_spells_2");
		public static final TranslationTextComponent DESC_BOOK_OF_SPELLS_3 = prefix("description_book_of_spells_3");
		public static final TranslationTextComponent DESC_BOOK_OF_SPELLS_4 = prefix("description_book_of_spells_4");
		public static final TranslationTextComponent DESC_BOOK_OF_SPELLS_5 = prefix("description_book_of_spells_5");
		public static final TranslationTextComponent DESC_BOOK_OF_SPELLS_6 = prefix("description_book_of_spells_6");
		public static final TranslationTextComponent DESC_BOOK_MANA_CREATIVE = prefix("description_book_of_mana_creative");
		public static final TranslationTextComponent DESC_HOLD = prefix("description_hold");
		public static final TranslationTextComponent DESC_SLOT = prefix("description_slot");
		public static final TranslationTextComponent MAGIC_SOURCE_NEUTRAL = MagicSource.getSourceName(MagicSource.NEUTRALMAGIC);
		public static final TranslationTextComponent MAGIC_SOURCE_DARK = MagicSource.getSourceName(MagicSource.DARKMAGIC);
		public static final TranslationTextComponent MAGIC_SOURCE_LIGHT = MagicSource.getSourceName(MagicSource.LIGHTMAGIC);
		public static final TranslationTextComponent MAGIC_SOURCE_UNKNOWN = MagicSource.getSourceName(MagicSource.UNKNOWNMAGIC);
		public static final TranslationTextComponent DESC_KEY_SHIFT = prefix("description_key_shift");
		public static final TranslationTextComponent DEFAULT_NAME_MAGICAL_FORGE = new TranslationTextComponent("block.spellstorm.magical_forge");
		public static final TranslationTextComponent DESC_MAGICAL_INK = prefix("description_magical_ink_0");
		public static final TranslationTextComponent DESC_MANA_CONSUMPTION = prefix("description_mana_consumption");
	}
	
	public static final class BlockStatePredicates {
		public static final Predicate<BlockState> FACING_NORTH = (state) -> {
			if (!state.hasProperty(HorizontalBlock.HORIZONTAL_FACING)) return false;		
			return state != null && state.get(HorizontalBlock.HORIZONTAL_FACING) == Direction.NORTH;
		};
		
		public static final Predicate<BlockState> FACING_SOUTH = (state) -> {
			if (!state.hasProperty(HorizontalBlock.HORIZONTAL_FACING)) return false;	
			return state != null && state.get(HorizontalBlock.HORIZONTAL_FACING) == Direction.SOUTH;
		};
		
		public static final Predicate<BlockState> FACING_EAST = (state) -> {
			if (!state.hasProperty(HorizontalBlock.HORIZONTAL_FACING)) return false;		
			return state != null && state.get(HorizontalBlock.HORIZONTAL_FACING) == Direction.EAST;
		};
		
		public static final Predicate<BlockState> FACING_WEST = (state) -> {
			if (!state.hasProperty(HorizontalBlock.HORIZONTAL_FACING)) return false;		
			return state != null && state.get(HorizontalBlock.HORIZONTAL_FACING) == Direction.WEST;
		};
		
		public static final Predicate<BlockState> GATEWAY_PATTERN_STAIRS_NORTH = (state) -> {
			return state != null && FACING_NORTH.test(state) && state.isIn(Blocks.PURPUR_STAIRS);
		};
		
		public static final Predicate<BlockState> GATEWAY_PATTERN_STAIRS_SOUTH = (state) -> {
			return state != null && FACING_SOUTH.test(state) && state.isIn(Blocks.PURPUR_STAIRS);
		};
		
		public static final Predicate<BlockState> GATEWAY_PATTERN_STAIRS_EAST = (state) -> {
			return state != null && FACING_EAST.test(state) && state.isIn(Blocks.PURPUR_STAIRS);
		};
		
		public static final Predicate<BlockState> GATEWAY_PATTERN_STAIRS_WEST = (state) -> {
			return state != null && FACING_WEST.test(state) && state.isIn(Blocks.PURPUR_STAIRS);
		};
		
		public static final Predicate<BlockState> GATEWAY_PATTERN_CORE = (state) -> {
			return state != null && state.isIn(BlockInit.GATEWAY.get());
		};
		
		public static final Predicate<BlockState> GATEWAY_PATTERN_PILLAR = (state) -> {
			return state != null && state.isIn(Blocks.PURPUR_PILLAR);
		};
	}
}
