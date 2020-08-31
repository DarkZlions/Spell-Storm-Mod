package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.lib.Lib;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit 
{
	public static final DeferredRegister<SoundEvent> REGISTER_SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Lib.MOD_ID);
	
	public static final RegistryObject<SoundEvent> ETERNAL_SCREAMING = REGISTER_SOUNDS.register("eternal_screaming", () -> new SoundEvent(Lib.ResourceLocations.ETERNAL_SCREAMING_SOUND));
	public static final RegistryObject<SoundEvent> HAUNTED_SOULS = REGISTER_SOUNDS.register("haunted_souls", () -> new SoundEvent(Lib.ResourceLocations.HAUNTED_SOULS_SOUND));
}
