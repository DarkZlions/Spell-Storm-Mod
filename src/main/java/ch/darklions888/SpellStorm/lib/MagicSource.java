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
	
	public TranslationTextComponent getSourceName() {
		return prefix("magic_source_name_" + this.sourceId);
	}
	
	public static TranslationTextComponent getSourceName(MagicSource sourceIn) {
		return prefix("magic_source_name_" + sourceIn.sourceId);
	}
}
