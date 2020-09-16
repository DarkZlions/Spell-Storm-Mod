package ch.darklions888.SpellStorm.lib.config;

import java.io.File;

import org.apache.logging.log4j.LogManager;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import ch.darklions888.SpellStorm.lib.Lib;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Lib.MOD_ID)
public class ConfigHandler {

	private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec CLIENT_CONFIG;
	
	private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SERVER_CONFIG;
	
	static {
		MagicalPagesConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
		
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
}
