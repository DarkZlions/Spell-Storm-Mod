package ch.darklions888.SpellStorm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.darklions888.SpellStorm.client.KeyBindings;
import ch.darklions888.SpellStorm.init.BlockInit;
import ch.darklions888.SpellStorm.init.ContainerTypesInit;
import ch.darklions888.SpellStorm.init.ItemInit;
import ch.darklions888.SpellStorm.init.ParticlesInit;
import ch.darklions888.SpellStorm.init.SoundInit;
import ch.darklions888.SpellStorm.network.NetworkHandler;
import ch.darklions888.SpellStorm.world.gen.FeatureGeneration;
import ch.darklions888.SpellStorm.world.gen.OreGeneration;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SpellStormMain.MODID)
@Mod.EventBusSubscriber(modid = SpellStormMain.MODID, bus = Bus.MOD)
public class SpellStormMain {
	
	public static final String MODID = "spellstorm";
	public static final Logger LOGGER = LogManager.getLogger();
	public static SpellStormMain INSTANCE;

	public SpellStormMain() {
		final IEventBus Bus = FMLJavaModLoadingContext.get().getModEventBus();

		Bus.addListener(this::CommonSetup);
		Bus.addListener(this::ClientSetup);

		SoundInit.REGISTER_SOUNDS.register(Bus);
		ItemInit.REGISTER_ITEMS.register(Bus);
		BlockInit.REGISTER_BLOCKS.register(Bus);
		ContainerTypesInit.REGISTER_CONTAINERS.register(Bus);
		ParticlesInit.REGISTER_PARTICLES.register(Bus);

		SpellStormMain.INSTANCE = this;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void CommonSetup(FMLCommonSetupEvent event) {
		OreGeneration.GenerationSetup();
		FeatureGeneration.genFeatures();
		NetworkHandler.registerMessages();
	}

	@SubscribeEvent
	public void ClientSetup(FMLClientSetupEvent event) {
		KeyBindings.init();
	}

	@SubscribeEvent
	public void LoadCompleteEvent(FMLLoadCompleteEvent event) {
		OreGeneration.GenerationSetup();
		FeatureGeneration.genFeatures();
	}

	public static ResourceLocation location(String key) {
		return new ResourceLocation(SpellStormMain.MODID, key);
	}
}
