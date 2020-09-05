package ch.darklions888.SpellStorm.lib;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class Lib {
	
	public static final String MOD_ID = "spellstorm";
	
	public static class RegistryNames {
		public static ResourceLocation location(String key) {
			return new ResourceLocation(MOD_ID, key);
		}
		
		// ConfiguredFeature
		public static final ResourceLocation MAGICAL_TREE_REGISTRY_NAME = location("magical_tree");
		
		// Blocks
		
		// Sound
		public static final ResourceLocation ETERNAL_SCREAMING_SOUND = location("eternal_screaming");
		public static final ResourceLocation HAUNTED_SOULS_SOUND = location("haunted_souls");
		
		// Texture for gui
		public static final ResourceLocation BOOK_OF_SPELLS_SCREEN_BACKGROUND_TEXUTRE = location("textures/gui/container/bookofspells_container.png");
		public static final ResourceLocation MANA_INFUSER_SCREEN_BACKGROUND_TEXTURE = location("textures/gui/container/manainfuser_container.png");
		public static final ResourceLocation SOUL_EXTRACTOR_SCREEN_BACKGROUND_TEXTURE = location("textures/gui/container/soulextractor_container.png");
	
		// Networking
		public static final ResourceLocation NETWORK_CHANNEL = location("channel");
		
		// Advancement
		
	}
	
	public static class TextComponents {
		public static TranslationTextComponent prefix(String text) {
			return new TranslationTextComponent(new String(Lib.MOD_ID + "misc" + "." + text));
		}
		
		public static TranslationTextComponent getSourceName(MagicSource source) {
			return prefix("magic_source_name_" + source.sourceId);
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
		public static final TranslationTextComponent MAGIC_SOURCE_NEUTRAL = prefix("magic_source_name_neutral");
		public static final TranslationTextComponent MAGIC_SOURCE_DARK = prefix("magic_source_name_dark");
		public static final TranslationTextComponent MAGIC_SOURCE_LIGHT = prefix("magic_source_name_light");
		public static final TranslationTextComponent MAGIC_SOURCE_UNKNOWN = prefix("magic_source_name_unknown");
		public static final TranslationTextComponent DESC_KEY_SHIFT = prefix("description_key_shift");
		
	}
}
