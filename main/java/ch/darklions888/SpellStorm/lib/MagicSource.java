package ch.darklions888.SpellStorm.lib;

public enum MagicSource {
	DARKMAGIC("Dark"),
	LIGHTMAGIC("Light"),
	NEUTRALMAGIC("Neutral"),
	UNKNOWNMAGIC("UNKNOWN");

	public String sourceId;

	MagicSource(String sourceName) {
		this.sourceId = sourceName;
	}
}
