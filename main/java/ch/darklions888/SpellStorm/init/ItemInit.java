package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.objects.CustomItemGroup.SpellStormItemGroup;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = SpellStormMain.MODID, bus = Bus.MOD)
@ObjectHolder(SpellStormMain.MODID)
public class ItemInit 
{
	public static final DeferredRegister<Item> REGISTERITEMS = new DeferredRegister<Item>(ForgeRegistries.ITEMS, SpellStormMain.MODID);
	
	public static final RegistryObject<Item> CRYSTAL = REGISTERITEMS.register("crystal", () -> new Item(new Item.Properties().group(SpellStormItemGroup.INSTANCE)));
}
