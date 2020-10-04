package ch.darklions888.SpellStorm.lib.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.items.spells.AbstractPageItem;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Lib.MOD_ID)
public class ConfigHandler {

	public static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec CLIENT_CONFIG;
	
	public static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SERVER_CONFIG;
	
	public static final Map<AbstractPageItem, ForgeConfigSpec.IntValue> MANA_CONSUMPTION = new HashMap<>();
	
	static {	
		CLIENT_CONFIG = CLIENT_BUILDER.build();
		SERVER_CONFIG = SERVER_BUILDER.build();
	}
	
	public static void loadConfig(ForgeConfigSpec config, String path) {
		LogManager.getLogger().info("Loading config: " + path);
		final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();	
		LogManager.getLogger().info("Built config: " + path);		
		file.load();	
		LogManager.getLogger().info("Loaded config: " + path);	
		config.setConfig(file);
	}
	
	public static void init(ForgeConfigSpec.Builder serverBuilder, ForgeConfigSpec.Builder clientBuilder) {
		serverBuilder.comment("Magical Page Config: ");
		
		ForgeRegistries.ITEMS.forEach(item -> {
			if (item instanceof AbstractPageItem && item != null) {
				MANA_CONSUMPTION.put((AbstractPageItem) item, 
				serverBuilder.comment("Mana Consumption for: '").defineInRange("magicalpage.manaconsumption_", 1, 0, 9999));
			}
		});
	}
}
