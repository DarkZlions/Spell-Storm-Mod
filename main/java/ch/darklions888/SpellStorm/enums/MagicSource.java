package ch.darklions888.SpellStorm.enums;

public enum MagicSource 
{
	DARKMAGIC("Dark"),
	LIGHTMAGIC("Light"),
	NEUTRALMAGIC("Neutral"),
	UNKNOWNMAGIC("UNKNOWN");
	
	public String sourceName;
	
	MagicSource(String sourceName)
	{
		this.sourceName = sourceName;
	}
}
