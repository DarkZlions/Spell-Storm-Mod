package ch.darklions888.SpellStorm.client.input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

import ch.darklions888.SpellStorm.lib.Lib;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Lib.MOD_ID, value = Dist.CLIENT)
public class InputHandler {
	private static final String CATEGORY = "key.categories." + Lib.MOD_ID;
	
	public static final KeyBinding openBookOfSpellsContainer = new KeyBinding(Lib.MOD_ID + ".key.openBookOfSpellsContainer", GLFW_KEY_R, CATEGORY);
	
	static {
		//ClientRegistry.registerKeyBinding(openBookOfSpellsContainer);
	}
	
	@SubscribeEvent
	public static void OnClientTickEvent(final ClientTickEvent event) {
		//if (openBookOfSpellsContainer.isPressed()) {
		
	}
}
