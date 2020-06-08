package ch.darklions888.SpellStorm.util.helpers;

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
	public static boolean IsHoldingNum_0() {
		return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_0);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static boolean IsHoldingNum_1() {
		return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_1);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static boolean IsHoldingNum_2() {
		return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_2);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static boolean IsHoldingNum_3() {
		return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_3);
	}
	@OnlyIn(Dist.CLIENT)
	public static boolean IsHoldingNum_4() {
		return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_4);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static boolean IsHoldingNum_5() {
		return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_5);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static boolean IsHoldingNum_6() {
		return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_6);
	}
}
