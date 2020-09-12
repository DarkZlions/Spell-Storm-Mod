package ch.darklions888.SpellStorm.registries;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.tileentities.MagicalForgeTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypesInit 
{
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Lib.MOD_ID);
	
	public static final RegistryObject<TileEntityType<MagicalForgeTileEntity>> MAGICAL_FORGE_TILEENTITY = TILE_ENTITY_TYPES.register("magical_forge", () -> TileEntityType.Builder.create(MagicalForgeTileEntity::new, BlockInit.MAGICAL_FORGE.get()).build(null));
}
