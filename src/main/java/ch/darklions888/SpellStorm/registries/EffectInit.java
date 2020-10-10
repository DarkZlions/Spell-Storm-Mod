package ch.darklions888.SpellStorm.registries;

import ch.darklions888.SpellStorm.lib.Lib;
import net.minecraft.potion.Effect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectInit {
	public static final DeferredRegister<Effect> REGISTER_EFFECT = DeferredRegister.create(ForgeRegistries.POTIONS, Lib.MOD_ID);
	
}
