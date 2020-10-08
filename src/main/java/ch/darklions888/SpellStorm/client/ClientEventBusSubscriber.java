package ch.darklions888.SpellStorm.client;

import ch.darklions888.SpellStorm.client.gui.BookOfSpellsScreen;
import ch.darklions888.SpellStorm.client.gui.MagicalForgeScreen;
import ch.darklions888.SpellStorm.client.gui.ManaInfuserScreen;
import ch.darklions888.SpellStorm.client.gui.SoulExtractorScreen;
import ch.darklions888.SpellStorm.client.renderer.GateWayTileEntityRenderer;
import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.registries.BlockInit;
import ch.darklions888.SpellStorm.registries.ContainerTypesInit;
import ch.darklions888.SpellStorm.registries.EntityInit;
import ch.darklions888.SpellStorm.registries.TileEntityInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Lib.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {

		registerContainerScreen();
		registerRenderTypes();
		registerEntityRenderers();
		registerTileEntityRenderer();
		
		ItemColorHandler.init(Minecraft.getInstance().getItemColors());
	}
	
	private static void registerRenderTypes() {
		RenderTypeLookup.setRenderLayer(BlockInit.MAGICAL_TREE_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInit.SOUL_EXTRACTOR.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(BlockInit.MAGICAL_FORGE.get(), RenderType.getTranslucent());
	}
	
	private static void registerEntityRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.MAGICAL_FIREBALL.get(), renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
	}
	
	private static void registerContainerScreen() {
		ContainerTypesInit.MANA_INFUSER.ifPresent(container -> ScreenManager.registerFactory(container, ManaInfuserScreen::new));
		ContainerTypesInit.SOUL_EXTRACTOR.ifPresent(container -> ScreenManager.registerFactory(container, SoulExtractorScreen::new));
		ContainerTypesInit.BOOK_OF_SPELLS.ifPresent(container -> ScreenManager.registerFactory(container, BookOfSpellsScreen::new));
		ContainerTypesInit.MAGICAL_FORGE.ifPresent(container -> ScreenManager.registerFactory(container, MagicalForgeScreen::new));

	}
	
	private static void registerTileEntityRenderer() {
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.GATEWAY_TILEENTITY.get(), GateWayTileEntityRenderer::new);
	}
}
