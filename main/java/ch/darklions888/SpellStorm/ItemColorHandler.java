package ch.darklions888.SpellStorm;

import ch.darklions888.SpellStorm.objects.items.pages.MagicalInkItem;
import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.client.renderer.color.ItemColors;

public class ItemColorHandler {
	
	public static ItemColors init(ItemColors colorsIn) {
		/*
		 * (int)((float)base * multiplier / 255)
		 * (int)((float)8182217 * 28 / 255)
		 */
		colorsIn.register((itemStack, color) -> {
			return color == 0 ? MagicalInkItem.getColor(itemStack) : -1;
		}, ItemInit.MAGICAL_INK.get());
		
		return colorsIn;
	}
}
