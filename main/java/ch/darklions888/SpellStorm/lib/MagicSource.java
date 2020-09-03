package ch.darklions888.SpellStorm.lib;

public enum MagicSource {
	DARKMAGIC("dark"),
	LIGHTMAGIC("light"),
	NEUTRALMAGIC("neutral"),
	UNKNOWNMAGIC("unknown");

	public String sourceId;

	MagicSource(String sourceName) {
		this.sourceId = sourceName;
	}
}
