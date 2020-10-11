package ch.darklions888.SpellStorm.registries;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.potion.EffectUtil;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectInit {
	public static final DeferredRegister<Effect> REGISTER_EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Lib.MOD_ID);
	
	public static final RegistryObject<Effect> DISINTERGRATED = REGISTER_EFFECTS.register("disintergrated", () -> new EffectUtil(EffectType.HARMFUL, 0x032620));
}
