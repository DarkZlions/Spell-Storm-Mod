package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.objects.containers.ManaInfuserContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypesInit 
{
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, SpellStormMain.MODID);
	
	public static final RegistryObject<ContainerType<ManaInfuserContainer>> MANA_INFUSER = CONTAINER_TYPES.register("mana_infuser", () -> new ContainerType((ContainerType.IFactory)ManaInfuserContainer::create));
}
