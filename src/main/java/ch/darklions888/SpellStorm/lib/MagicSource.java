package ch.darklions888.SpellStorm.lib;

import net.minecraft.util.text.TranslationTextComponent;
import static ch.darklions888.SpellStorm.lib.Lib.TextComponents.prefix;

public enum MagicSource {
	DARKMAGIC("dark"),
	LIGHTMAGIC("light"),
	NEUTRALMAGIC("neutral"),
	UNKNOWNMAGIC("unknown");

	private final String sourceId;

	MagicSource(String sourceName) {
		this.sourceId = sourceName;
	}
	
	public String getId() {
		return this.sourceId;
	}
	
	public String getKey() {
		return this.getId();
	}
	
	public String getSourceId() {
		return this.getId();
	}
	
	public TranslationTextComponent getSourceName() {
		return prefix("magic_source_name_" + this.sourceId);
	}
	
	public static TranslationTextComponent getSourceName(MagicSource sourceIn) {
		return prefix("magic_source_name_" + sourceIn.sourceId);
	}
	
	public static MagicSource getSourceByKey(String key) {
		switch (key) {
		case "dark": 
			return DARKMAGIC;
		case "light":
			return LIGHTMAGIC;
		case "unknown":
			return UNKNOWNMAGIC;
		case "neutral":
			return NEUTRALMAGIC;
			
		default:
			return null;
		}
	}
	
	public static int getMagicSourceColour(MagicSource sourceIn) {
		switch (sourceIn) {
		
		case DARKMAGIC: 
			return 0x8A0000;
		case LIGHTMAGIC:
			return 0xFFBB600;
		case UNKNOWNMAGIC:
			return 0x1E1E1E;
		default:
			return 0xFFFFFF;
		}
	}
	
	@Override
	public String toString() {
		return this.sourceId;
	}
}
