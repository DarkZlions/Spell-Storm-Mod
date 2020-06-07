package ch.darklions888.SpellStorm.enums;

public enum ManaPower 
{
	VERYLOW(5),
	LOW(10),
	MEDIUM(20),
	HIGH(30),
	VERYHIGH(40);
	
	public int mana;

	private ManaPower(int mana) {
		this.mana = mana;
	}
}
