package ch.darklions888.SpellStorm.client;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.objects.items.weapons.BaseSwordItem;
import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.client.renderer.color.ItemColors;

public class ItemColorHandler {
	
	public static ItemColors init(ItemColors colorsIn) {
		
		colorsIn.register((itemStack, color) -> {
			return color == 0 ? MagicSource.getMagicSourceColour(MagicSource.DARKMAGIC) : -1;
		}, ItemInit.MAGICAL_INK_DARK.get());
		
		colorsIn.register((itemStack, color) -> {
			return color == 0 ? MagicSource.getMagicSourceColour(MagicSource.LIGHTMAGIC) : -1;
		}, ItemInit.MAGICAL_INK_LIGHT.get());
		
		colorsIn.register((itemStack, color) -> {
			return color == 0 ? MagicSource.getMagicSourceColour(MagicSource.UNKNOWNMAGIC) : -1;
		}, ItemInit.MAGICAL_INK_UNKNOWN.get());
		
		colorsIn.register((itemStack, color) -> {
			return color == 0 ? itemStack.getItem() instanceof BaseSwordItem ? MagicSource.getMagicSourceColour(((BaseSwordItem)itemStack.getItem()).getDefaultSource()) : 0xFFFFF : -1;
		}, ItemInit.MANA_INFUSED_SWORD.get());
		
		return colorsIn;
	}
}
