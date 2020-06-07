package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.SpellStormMain;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit 
{
	public static final DeferredRegister<SoundEvent> REGISTER_SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, SpellStormMain.MODID);
	
	public static final RegistryObject<SoundEvent> ETERNAL_SCREAMING = REGISTER_SOUNDS.register("eternal_screaming", () -> new SoundEvent(SpellStormMain.location("eternal_screaming")));
	public static final RegistryObject<SoundEvent> HAUNTED_SOULS = REGISTER_SOUNDS.register("haunted_souls", () -> new SoundEvent(SpellStormMain.location("haunted_souls")));
}
