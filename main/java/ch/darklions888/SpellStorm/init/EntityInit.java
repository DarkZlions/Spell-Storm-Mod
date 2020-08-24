package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.objects.entities.DragonBallEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

	public static final DeferredRegister<EntityType<?>> REGISTER_ENTITY = new DeferredRegister<>(ForgeRegistries.ENTITIES, SpellStormMain.MODID);
	
	public static final RegistryObject<EntityType<DragonBallEntity>> DRAGON_BALL_ENTITY = REGISTER_ENTITY.register
			("dragon_ball_entity", () -> EntityType.Builder.<DragonBallEntity>create(DragonBallEntity::new, EntityClassification.MISC)
			.build(SpellStormMain.location("dragon_ball_entity").toString()));
}
