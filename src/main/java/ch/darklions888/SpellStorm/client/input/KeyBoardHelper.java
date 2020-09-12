/*
 * 
 * This keyboardhelper is only client side
 * won't work on a server.
 * Just believe me.
 * 
 */
package ch.darklions888.SpellStorm.client.input;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class KeyBoardHelper 
{
	private static final long WINDOW = Minecraft.getInstance().getMainWindow().getHandle();
	
	@OnlyIn(Dist.CLIENT)
	public static boolean IsHoldingShift()
	{
		return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_SHIFT) || 
				InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_SHIFT);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static boolean IsHoldingShifts()
	{
		return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_SHIFT) || 
				InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_SHIFT);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static boolean IsHoldingControl() {
		return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_CONTROL) || 
				InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_CONTROL);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static boolean isHoldingButton(int button) {
		return InputMappings.isKeyDown(WINDOW, button);
	}
}
