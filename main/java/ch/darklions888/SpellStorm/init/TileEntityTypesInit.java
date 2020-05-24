package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.objects.tileentities.ManaInfuser_TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypesInit 
{
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, SpellStormMain.MODID);
	
	//public static final RegistryObject<TileEntityType<ManaInfuser_TileEntity>> MANAINFUSER = TILE_ENTITY_TYPES.register("manainfuser", () -> TileEntityType.Builder.create(ManaInfuser_TileEntity::new, BlockInit.MANAINFUSER.get()).build(null));
}
