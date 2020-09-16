package ch.darklions888.SpellStorm.registries;

import ch.darklions888.SpellStorm.lib.Lib;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FeaturesInit {
	public static final DeferredRegister<Feature<?>> FEATURES_REGISTER = DeferredRegister.create(ForgeRegistries.FEATURES, Lib.MOD_ID);
}
