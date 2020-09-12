package ch.darklions888.SpellStorm.client;

import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.client.renderer.color.ItemColors;

public class ItemColorHandler {
	
	public static ItemColors init(ItemColors colorsIn) {
		
		colorsIn.register((itemStack, color) -> {
			return color == 0 ? 0x8A0000 : -1;
		}, ItemInit.MAGICAL_INK_DARK.get());
		
		colorsIn.register((itemStack, color) -> {
			return color == 0 ? 0xFFB600 : -1;
		}, ItemInit.MAGICAL_INK_LIGHT.get());
		
		colorsIn.register((itemStack, color) -> {
			return color == 0 ? 10 : -1;
		}, ItemInit.MAGICAL_INK_UNKNOWN.get());
		
		return colorsIn;
	}
}
