package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.lib.Lib;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

	public static final DeferredRegister<EntityType<?>> REGISTER_ENTITY = DeferredRegister.create(ForgeRegistries.ENTITIES, Lib.MOD_ID);
	
	/*
	public static final RegistryObject<EntityType<DragonBallEntity>> DRAGON_BALL_ENTITY = REGISTER_ENTITY.register
			("dragon_ball_entity", () -> EntityType.Builder.<DragonBallEntity>create(DragonBallEntity::new, EntityClassification.MISC)
			.build(Lib.location("dragon_ball_entity").toString()));
			*/
}
