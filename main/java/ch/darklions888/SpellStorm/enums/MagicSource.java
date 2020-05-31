package ch.darklions888.SpellStorm.enums;

public enum MagicSource 
{
	DARKMAGIC("Dark"),
	LIGHTMAGIC("Light"),
	NEUTRALMAGIC("Neutral"),
	UNKNOWNMAGIC("UNKNOWN");
	
	public String sourceId;
	
	MagicSource(String sourceName)
	{
		this.sourceId = sourceName;
	}
}
