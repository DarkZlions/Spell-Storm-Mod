package ch.darklions888.SpellStorm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.darklions888.SpellStorm.client.ClientSideSetup;
import ch.darklions888.SpellStorm.client.proxy.ClientProxy;
import ch.darklions888.SpellStorm.data.DataGenerators;
import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.lib.config.ConfigHandler;
import ch.darklions888.SpellStorm.network.PacketHandler;
import ch.darklions888.SpellStorm.registries.BlockInit;
import ch.darklions888.SpellStorm.registries.ContainerTypesInit;
import ch.darklions888.SpellStorm.registries.EffectInit;
import ch.darklions888.SpellStorm.registries.EntityInit;
import ch.darklions888.SpellStorm.registries.IProxy;
import ch.darklions888.SpellStorm.registries.ItemInit;
import ch.darklions888.SpellStorm.registries.ParticlesInit;
import ch.darklions888.SpellStorm.registries.RecipeSerializerInit;
import ch.darklions888.SpellStorm.registries.SoundInit;
import ch.darklions888.SpellStorm.registries.TileEntityInit;
import ch.darklions888.SpellStorm.registries.WorldFeatureInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
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
	public static IProxy proxy = new IProxy() {};

	@SuppressWarnings("deprecation")
	public SpellStormMain() {
		
		DistExecutor.callWhenOn(Dist.CLIENT, () -> () -> proxy = new ClientProxy());
		
		final IEventBus Bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		Bus.addListener(this::CommonSetup);
		Bus.addListener(this::ClientSetup);
		Bus.addListener(DataGenerators::gatherData);
		
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_SPEC);
				
		BlockInit.REGISTER_BLOCKS.register(Bus);
		SoundInit.REGISTER_SOUNDS.register(Bus);
		ItemInit.REGISTER_ITEMS.register(Bus);
		ContainerTypesInit.REGISTER_CONTAINERS.register(Bus);
		ParticlesInit.REGISTER_PARTICLES.register(Bus);
		EntityInit.REGISTER_ENTITIES.register(Bus);
		RecipeSerializerInit.RECIPE_SERIALIZER.register(Bus);
		TileEntityInit.REGISTER_TILEENTITIES.register(Bus);
		EffectInit.REGISTER_EFFECTS.register(Bus);
		
		ConfigHandler.loadConfig(ConfigHandler.COMMON_SPEC, FMLPaths.CONFIGDIR.get().resolve("spellstorm-common.toml").toString());
		
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
		ClientSideSetup.clientSetup(event);
	}

	@SubscribeEvent
	public void LoadCompleteEvent(FMLLoadCompleteEvent event) {
	}
}
