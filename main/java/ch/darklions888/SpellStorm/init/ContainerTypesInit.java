package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.objects.containers.ManaInfuserContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.ContainerType.IFactory;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypesInit 
{
	public static final DeferredRegister<ContainerType<?>> REGISTER_CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, SpellStormMain.MODID);
	
	public static final RegistryObject<ContainerType<ManaInfuserContainer>> MANA_INFUSER = REGISTER_CONTAINERS.register("mana_infuser", () -> new ContainerType<>((IFactory<ManaInfuserContainer>)ManaInfuserContainer::create));
}
