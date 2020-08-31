package ch.darklions888.SpellStorm.lib;

import net.minecraft.util.ResourceLocation;

public class Lib {
	
	public static final String MOD_ID = "spellstorm";
	
	public static class ResourceLocations {
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
	}
}
