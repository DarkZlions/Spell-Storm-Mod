package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.lib.Lib;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit 
{
	public static final DeferredRegister<SoundEvent> REGISTER_SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, SpellStormMain.MODID);
	
	public static final RegistryObject<SoundEvent> ETERNAL_SCREAMING = REGISTER_SOUNDS.register("eternal_screaming", () -> new SoundEvent(Lib.location("eternal_screaming")));
	public static final RegistryObject<SoundEvent> HAUNTED_SOULS = REGISTER_SOUNDS.register("haunted_souls", () -> new SoundEvent(Lib.location("haunted_souls")));
}
