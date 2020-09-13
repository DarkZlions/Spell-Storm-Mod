package ch.darklions888.SpellStorm.lib;

public enum ManaContainerType 
{
	VERYSMALL(60),
	SMALL(120),
	MEDIUM(180),
	BIG(240),
	GIANT(300),
	OCEAN(400),
	REALLYBIG(800),
	BIGGER(1600);
	
	public int size;

	private ManaContainerType(int size) {
		this.size = size;
	}
}
