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
		public final ForgeConfigSpec.IntValue pageOfTheWithers_maxMana;
		public final ForgeConfigSpec.IntValue pageOfTheWithers_manaConsumption;
		public final ForgeConfigSpec.IntValue pageOfDragonBall_maxMana;
		public final ForgeConfigSpec.IntValue pageOfDragonBall_manaConsumption;
		public final ForgeConfigSpec.IntValue pageOfAggression_maxMana;
		public final ForgeConfigSpec.IntValue pageOfAggression_manaConsumption;
		public final ForgeConfigSpec.IntValue pageOfFallingRocks_maxMana;
		public final ForgeConfigSpec.IntValue pageOfFallingRocks_manaConsumption;
		public final ForgeConfigSpec.IntValue pageOfFangs_maxMana;
		public final ForgeConfigSpec.IntValue pageOfFangs_manaConsumption;
		
		public Common(ForgeConfigSpec.Builder builder) {
			builder.comment("SpellStorm Config:");
			
			builder.push("spellPageConfiguration");
			pageOfTheWithers_maxMana = builder.comment("Maximum amount of mana that the Page Of The Withers can hold.")
					.defineInRange("maxContainer", 300, 1, 10000);
			pageOfTheWithers_manaConsumption = builder.comment("Maximum amount of mana that the Page Of The Withers can use on cast.")
					.defineInRange("maxConsumption", 2, 1, 10000);
			
			pageOfDragonBall_maxMana = builder.comment("Maximum amount of mana that the Page Of Dragonball can hold.")
					.defineInRange("maxContainer", 120, 1, 10000);
			pageOfDragonBall_manaConsumption = builder.comment("Maximum amount of mana that the Page Of Dragonball can use on cast.")
					.defineInRange("maxConsumption", 2, 1, 10000);
			
			pageOfAggression_maxMana = builder.comment("Maximum amount of mana that the Page Of Aggression can hold.")
					.defineInRange("maxContainer", 180, 1, 10000);
			pageOfAggression_manaConsumption = builder.comment("Maximum amount of mana that the Page Of Aggression can use on cast.")
					.defineInRange("maxConsumption", 1, 1, 10000);
			
			pageOfFallingRocks_maxMana = builder.comment("Maximum amount of mana that the Page Of Aggression can hold.")
					.defineInRange("maxContainer", 180, 1, 10000);
			pageOfFallingRocks_manaConsumption = builder.comment("Maximum amount of mana that the Page Of Aggression can use on cast.")
					.defineInRange("maxConsumption", 1, 1, 10000);
			
			pageOfFangs_maxMana = builder.comment("Maximum amount of mana that the Page Of Aggression can hold.")
					.defineInRange("maxContainer", 400, 1, 10000);
			pageOfFangs_manaConsumption = builder.comment("Maximum amount of mana that the Page Of Aggression can use on cast.")
					.defineInRange("maxConsumption", 4, 1, 10000);
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
