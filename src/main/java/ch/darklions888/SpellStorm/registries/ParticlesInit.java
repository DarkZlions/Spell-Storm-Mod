package ch.darklions888.SpellStorm.registries;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.particle.RuneParticles;
import ch.darklions888.SpellStorm.objects.particle.SoulParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticlesInit {
	public static final DeferredRegister<ParticleType<?>> REGISTER_PARTICLES = DeferredRegister.create(
			ForgeRegistries.PARTICLE_TYPES, Lib.MOD_ID);

	public static final RegistryObject<BasicParticleType> SOULS_PARTICLE = REGISTER_PARTICLES.register("soul", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> RUNE_PARTICLE = REGISTER_PARTICLES.register("rune", () -> new BasicParticleType(false));

	@EventBusSubscriber(modid = Lib.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
	public static class RegisterParticleFactories {

		@SuppressWarnings("resource")
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
			Minecraft.getInstance().particles.registerFactory(SOULS_PARTICLE.get(), SoulParticles.Factory::new);
			Minecraft.getInstance().particles.registerFactory(RUNE_PARTICLE.get(), RuneParticles.Factory::new);
		}

	}
}