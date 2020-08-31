package ch.darklions888.SpellStorm.lib;

import ch.darklions888.SpellStorm.SpellStormMain;
import net.minecraft.util.ResourceLocation;

public class Lib {
	public static ResourceLocation location(String key) {
		return new ResourceLocation(SpellStormMain.MODID, key);
	}
}
