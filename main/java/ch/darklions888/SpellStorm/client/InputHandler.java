package ch.darklions888.SpellStorm.client;

import static ch.darklions888.SpellStorm.SpellStormMain.MODID;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class InputHandler {
	private static final String CATEGORY = "key.categories." + MODID;
	
	public static final KeyBinding openBookOfSpellsContainer = new KeyBinding(MODID + ".key.openBookOfSpellsContainer", GLFW_KEY_R, CATEGORY);
	
	static {
		//ClientRegistry.registerKeyBinding(openBookOfSpellsContainer);
	}
	
	@SubscribeEvent
	public static void OnClientTickEvent(final ClientTickEvent event) {
		
	}
}
