package ch.darklions888.SpellStorm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.lib.config.ConfigHandler;
import ch.darklions888.SpellStorm.network.PacketHandler;
import ch.darklions888.SpellStorm.registries.BlockInit;
import ch.darklions888.SpellStorm.registries.ContainerTypesInit;
import ch.darklions888.SpellStorm.registries.EntityInit;
import ch.darklions888.SpellStorm.registries.ItemInit;
import ch.darklions888.SpellStorm.registries.ParticlesInit;
import ch.darklions888.SpellStorm.registries.RecipeSerializerInit;
import ch.darklions888.SpellStorm.registries.SoundInit;
import ch.darklions888.SpellStorm.registries.TileEntityTypesInit;
import ch.darklions888.SpellStorm.registries.WorldFeatureInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Lib.MOD_ID)
@Mod.EventBusSubscriber(modid = Lib.MOD_ID, bus = Bus.MOD)
public class SpellStormMain {

	public static final Logger LOGGER = LogManager.getLogger();
	public static SpellStormMain INSTANCE;

	public SpellStormMain() {
		final IEventBus Bus = FMLJavaModLoadingContext.get().getModEventBus();

		Bus.addListener(this::CommonSetup);
		Bus.addListener(this::ClientSetup);
		
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigHandler.SERVER_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_CONFIG);
		
		ConfigHandler.loadConfig(ConfigHandler.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(Lib.MOD_ID + "-client.toml").toString());
		ConfigHandler.loadConfig(ConfigHandler.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve(Lib.MOD_ID + "-server.toml").toString());
		
		BlockInit.REGISTER_BLOCKS.register(Bus);
		SoundInit.REGISTER_SOUNDS.register(Bus);
		ItemInit.REGISTER_ITEMS.register(Bus);
		ContainerTypesInit.REGISTER_CONTAINERS.register(Bus);
		ParticlesInit.REGISTER_PARTICLES.register(Bus);
		EntityInit.REGISTER_ENTITY.register(Bus);
		RecipeSerializerInit.RECIPE_SERIALIZER.register(Bus);
		TileEntityTypesInit.TILE_ENTITY_TYPES.register(Bus);

		SpellStormMain.INSTANCE = this;
		MinecraftForge.EVENT_BUS.register(this);
		
	}

	@SubscribeEvent
	public void CommonSetup(FMLCommonSetupEvent event) {	
		PacketHandler.init();
		WorldFeatureInit.init();
		WorldFeatureInit.setup();
	}

	@SubscribeEvent
	public void ClientSetup(FMLClientSetupEvent event) {
	}

	@SubscribeEvent
	public void LoadCompleteEvent(FMLLoadCompleteEvent event) {
	}
}
