package ch.darklions888.SpellStorm.registries;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.tileentities.GateWayCoreTileEntity;
import ch.darklions888.SpellStorm.objects.tileentities.MagicalForgeTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit 
{
	public static final DeferredRegister<TileEntityType<?>> REGISTER_TILEENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Lib.MOD_ID);
	
	public static final RegistryObject<TileEntityType<MagicalForgeTileEntity>> MAGICAL_FORGE_TILEENTITY = REGISTER_TILEENTITIES.register("magical_forge", () -> TileEntityType.Builder.create(MagicalForgeTileEntity::new, BlockInit.MAGICAL_FORGE.get()).build(null));
	public static final RegistryObject<TileEntityType<GateWayCoreTileEntity>> GATEWAY_CORE_TILEENTTIY = REGISTER_TILEENTITIES.register("gateway_core", () -> TileEntityType.Builder.create(GateWayCoreTileEntity::new, BlockInit.GATEWAY_CORE.get()).build(null));
	
}
