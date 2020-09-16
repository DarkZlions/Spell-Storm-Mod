package ch.darklions888.SpellStorm.lib.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MagicalPagesConfig {
	private static final String seperator = "##################################################";
	public static ForgeConfigSpec.IntValue manaConsumption_pageOfAggression;
	
	public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {
		server.comment(seperator);
		server.comment("Magical Page Config: ");
		
		manaConsumption_pageOfAggression = server.comment("Mana Consumption for 'Page of Aggression': ").defineInRange("magicalpage.manaConsumption_pageOfAggression", 1, 0, 999);
	}
}
