package ch.darklions888.SpellStorm.client;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KeyBindings {

	public static KeyBinding key_shift;
	
	public static void init() {
		
		key_shift = new KeyBinding("key.shift", GLFW.GLFW_KEY_LEFT_SHIFT, "key.categories.input");	
	}
	
}
