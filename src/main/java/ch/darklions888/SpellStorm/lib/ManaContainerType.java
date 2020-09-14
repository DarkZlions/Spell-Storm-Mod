package ch.darklions888.SpellStorm.lib;

public enum ManaContainerType 
{
	VERYSMALL(60),
	SMALL(120),
	MEDIUM(180),
	GIANT(400),
	GIGANTIC(800),
	BIG(1600);
	
	public int size;

	private ManaContainerType(int size) {
		this.size = size;
	}
}
