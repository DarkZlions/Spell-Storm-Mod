package ch.darklions888.SpellStorm.lib;

public enum ManaPower 
{
	VERYLOW(5),
	LOW(10),
	MEDIUM(30),
	HIGH(60),
	VERYHIGH(100);
	
	public int mana;

	private ManaPower(int mana) {
		this.mana = mana;
	}
}
