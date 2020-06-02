package ch.darklions888.SpellStorm.util.helpers;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.client.gui.ManaInfuserScreen;
import ch.darklions888.SpellStorm.init.BlockInit;
import ch.darklions888.SpellStorm.init.ContainerTypesInit;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SpellStormMain.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber 
{
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event)
	{
		//ScreenManager.registerFactory(ContainerTypesInit.MANA_INFUSER.get(), ManaInfuserScreen::new);
		
		ContainerTypesInit.MANA_INFUSER.ifPresent(container -> ScreenManager.registerFactory(container, ManaInfuserScreen::new));
		
		RenderTypeLookup.setRenderLayer(BlockInit.MAGICAL_TREE_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInit.SOUL_EXTRACTOR.get(), RenderType.getTranslucent());
	}
}
