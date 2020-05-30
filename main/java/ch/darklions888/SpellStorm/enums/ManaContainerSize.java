package ch.darklions888.SpellStorm.enums;

public enum ManaContainerSize 
{
	VERYSMALL(60),
	SMALL(120),
	MEDIUM(180),
	BIG(240),
	GIANT(300),
	OCEAN(400);
	
	public int size;
	
	private ManaContainerSize(int size)
	{
		this.size = size;
	}
}
