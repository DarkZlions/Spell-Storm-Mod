package ch.darklions888.SpellStorm.lib.config;

import java.io.File;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import ch.darklions888.SpellStorm.lib.Lib;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {

	public static final Common COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;
	
	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
	
	public static class Common {
		
		// Spell Pages
		public final ForgeConfigSpec.IntValue pageOfTheWithers_maxMana;
		public final ForgeConfigSpec.IntValue pageOfTheWithers_manaConsumption;
		
		public final ForgeConfigSpec.IntValue pageOfDragonBall_maxMana;
		public final ForgeConfigSpec.IntValue pageOfDragonBall_manaConsumption;
		
		public final ForgeConfigSpec.IntValue pageOfAggression_maxMana;
		public final ForgeConfigSpec.IntValue pageOfAggression_manaConsumption;
		public final ForgeConfigSpec.IntValue pageOfAggression_coolDownDuration;
		
		public final ForgeConfigSpec.IntValue pageOfFallingRocks_maxMana;
		public final ForgeConfigSpec.IntValue pageOfFallingRocks_manaConsumption;
		public final ForgeConfigSpec.IntValue pageOfFallingRocks_coolDownDuration;
		public final ForgeConfigSpec.IntValue pageOfFallingRocks_explosionPower;
		
		public final ForgeConfigSpec.IntValue pageOfFangs_maxMana;
		public final ForgeConfigSpec.IntValue pageOfFangs_manaConsumption;
		public final ForgeConfigSpec.IntValue pageOfFangs_coolDownDuration;
		public final ForgeConfigSpec.IntValue pageOfFangs_maxRange;
		
		public final ForgeConfigSpec.IntValue pageOfFireballs_maxMana;
		public final ForgeConfigSpec.IntValue pageOfFireballs_manaConsumption;
		public final ForgeConfigSpec.DoubleValue pageOfFireballs_damageValue;
		
		public final ForgeConfigSpec.IntValue pageOfHealing_maxMana;
		public final ForgeConfigSpec.IntValue pageOfHealing_manaConsumption;
		public final ForgeConfigSpec.DoubleValue pageOfHealing_healAmount;
		public final ForgeConfigSpec.IntValue pageOfHealing_coolDownDuration;
		
		public final ForgeConfigSpec.IntValue pageOfMining_maxMana;
		public final ForgeConfigSpec.IntValue pageOfMining_manaConsumption;
		public final ForgeConfigSpec.DoubleValue pageOfMining_miningRange;
		
		public final ForgeConfigSpec.IntValue pageOfThunder_maxMana;
		public final ForgeConfigSpec.IntValue pageOfThunder_manaConsumption;
		public final ForgeConfigSpec.IntValue pageOfThunder_maxRange;
		
		// WorldGen
		public final ForgeConfigSpec.IntValue magicalTreeGen_count;
		public final ForgeConfigSpec.DoubleValue magicalTreeGen_extraChance;
		public final ForgeConfigSpec.IntValue magicalTreeGen_extraCount;
		
		public final ForgeConfigSpec.IntValue crystalOre_veinCount;
		public final ForgeConfigSpec.IntValue crystalOre_veinSize;
		public final ForgeConfigSpec.IntValue crystalOre_minY;
		public final ForgeConfigSpec.IntValue crystalOre_maxY;
		
		public final ForgeConfigSpec.IntValue corruptedCrystalOre_veinCount;
		public final ForgeConfigSpec.IntValue corruptedCrystalOre_veinSize;
		public final ForgeConfigSpec.IntValue corruptedCrystalOre_minY;
		public final ForgeConfigSpec.IntValue corruptedCrystalOre_maxY;
		
		public Common(ForgeConfigSpec.Builder builder) {
			builder.comment("SpellStorm Config:");
			
			builder.push("spellPageConfiguration");
			
			builder.comment("Page Of the Wither");
			pageOfTheWithers_maxMana = builder.defineInRange("maxMana_pageOfTheWithers", 400, 1, 10000);
			pageOfTheWithers_manaConsumption = builder.defineInRange("manaConsumption_pageOfTheWithers", 2, 1, 10000);
			
			builder.comment("Page Of Dragonball");
			pageOfDragonBall_maxMana = builder.defineInRange("maxMana_pageOfDragonball", 180, 1, 10000);
			pageOfDragonBall_manaConsumption = builder.defineInRange("manaConsumption_pageOfDragonball", 2, 1, 10000);
			
			builder.comment("Page Of Aggression");
			pageOfAggression_maxMana = builder.defineInRange("maxMana_pageOfAggression", 120, 1, 10000);
			pageOfAggression_manaConsumption = builder.defineInRange("manaConsumption_pageOfAggression", 1, 1, 10000);
			pageOfAggression_coolDownDuration = builder.comment("20 Ticks are 1 second.").defineInRange("coolDownDuration_pageOfAggression", 10, 0, 10000);
			
			builder.comment("Page Of Falling Rocks");
			pageOfFallingRocks_maxMana = builder.defineInRange("maxMana_pageOfFallingRocks", 120, 1, 10000);
			pageOfFallingRocks_manaConsumption = builder.defineInRange("manaConsumption_pageOfFallingRocks", 40, 1, 10000);
			pageOfFallingRocks_coolDownDuration = builder.comment("20 Ticks are 1 second.").defineInRange("coolDownDuration_pageOfFallingRocks", 30, 0, 10000);
			pageOfFallingRocks_explosionPower = builder.comment("The explosion power affect the damage and destruction range from the meteorit.").defineInRange("explosionPower_pageOfFallingRocks", 20, 0, 500);
			
			builder.comment("Page Of Fangs");
			pageOfFangs_maxMana = builder.defineInRange("maxMana_pageOfFangs", 160, 1, 10000);
			pageOfFangs_manaConsumption = builder.defineInRange("manaConsumption_pageOfFangs", 4, 1, 10000);
			pageOfFangs_coolDownDuration = builder.comment("20 Ticks are 1 second.").defineInRange("coolDownDuration_pageOfFangs", 15, 0, 10000);
			pageOfFangs_maxRange = builder.comment("The max range for the spell to detect enemies.").defineInRange("maxRange_pageOfFangs", 10, 0, 50);
			
			builder.comment("Page Of Fireball");
			pageOfFireballs_maxMana = builder.defineInRange("maxMana_pageOfFireball", 240, 1, 10000);
			pageOfFireballs_manaConsumption = builder.defineInRange("manaConsumption_pageOfFireball", 2, 1, 10000);
			pageOfFireballs_damageValue = builder.comment("The damage output from the fireball on impact.").defineInRange("damageValue_pageOfFireball", 8.0d, 0.0d, 999.0d);
			
			builder.comment("Page Of Healing");
			pageOfHealing_maxMana = builder.defineInRange("maxMana_pageOfHealing", 120, 1, 10000);
			pageOfHealing_manaConsumption = builder.defineInRange("manaConsumption_pageOfHealing", 6, 1, 10000);
			pageOfHealing_coolDownDuration = builder.comment("20 Ticks are 1 second.").defineInRange("coolDownDuration_pageOfHealing", 60, 0, 10000);
			pageOfHealing_healAmount = builder.comment("The amount of life is giving back on use.").defineInRange("healAmount_pageOfHealing", 10.0d, 0.0d, 999.0d);
			
			builder.comment("Page Of Mining");
			pageOfMining_maxMana = builder.defineInRange("maxMana_pageOfMining", 800, 1, 10000);
			pageOfMining_manaConsumption = builder.defineInRange("manaConsumption_pageOfMining", 1, 1, 10000);
			pageOfMining_miningRange = builder.comment("Max range of the player to mine blocks.").defineInRange("miningRange_pageOfMining", 10.0d, 1.0d, 20.0d);
			
			builder.comment("Page Of Thunder");
			pageOfThunder_maxMana = builder.defineInRange("maxMana_pageOfThunder", 180, 1, 10000);
			pageOfThunder_manaConsumption = builder.defineInRange("manaConsumption_pageOfThunder", 2, 1, 10000);
			pageOfThunder_maxRange = builder.comment("The max range for the spell to detect enemies.").defineInRange("maxRange_pageOfFangs", 10, 0, 50);
			
			builder.push("worldGenConfiguration");
			builder.comment("Magical Tree Placement Config:");
			magicalTreeGen_count = builder.defineInRange("magicalTreeGen_count", 0, 0, 100);
			magicalTreeGen_extraChance = builder.defineInRange("magicalTreeGen_extraChance", 0.01d, 0, 100);
			magicalTreeGen_extraCount = builder.defineInRange("magicalTreeGen_extraCount", 0, 0, 100);
			
			builder.comment("Crystal Ore Placement Config:");
			crystalOre_veinCount = builder.defineInRange("crystalOre_veinCount", 1, 0, 999);
			crystalOre_veinSize = builder.defineInRange("crystalOre_veinSize", 5, 0, 999);
			crystalOre_minY = builder.defineInRange("crystalOre_minY", 2, 0, 999);
			crystalOre_maxY = builder.defineInRange("crystalOre_maxY", 15, 0, 999);
			
			corruptedCrystalOre_veinCount = builder.defineInRange("corruptedCrystalOre_veinCount", 2, 0, 999);
			corruptedCrystalOre_veinSize = builder.defineInRange("corruptedCrystalOre_veinSize", 4, 0, 999);
			corruptedCrystalOre_minY = builder.defineInRange("corruptedCrystalOre_minY", 5, 0, 999);
			corruptedCrystalOre_maxY = builder.defineInRange("corruptedCrystalOre_maxY", 35, 0, 999);
			
		}
	}
	
	public static void loadConfig(ForgeConfigSpec config, String path) {
		Lib.LOGGER.info("Loading config: " + path);
		final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
		Lib.LOGGER.info("Built config: " + path);
		file.load();
		Lib.LOGGER.info("Loaded config: " + path);
		config.setConfig(file);
	}
}
