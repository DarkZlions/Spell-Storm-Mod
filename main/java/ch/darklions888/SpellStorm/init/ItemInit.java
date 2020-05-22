package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.objects.CustomItemGroup.SpellStormItemGroup;
import ch.darklions888.SpellStorm.objects.items.BaseBlockItem;
import ch.darklions888.SpellStorm.objects.items.CrytalShardItem;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit 
{
	public static final DeferredRegister<Item> REGISTERITEMS = new DeferredRegister<Item>(ForgeRegistries.ITEMS, SpellStormMain.MODID);
	
	//item
	public static final RegistryObject<Item> CRYSTAL = REGISTERITEMS.register("crystal", () -> new CrytalShardItem(10, "Crystal Shard", TextFormatting.LIGHT_PURPLE, true, new Item.Properties().group(SpellStormItemGroup.INSTANCE)));
	
	//BlockItem
	public static final RegistryObject<Item> CRYSTAL_ORE_BLOCK = REGISTERITEMS.register("crystal_ore", () -> new BaseBlockItem(30, "Crystal Ore", TextFormatting.LIGHT_PURPLE, false, BlockInit.crystal_ore, new Item.Properties().group(SpellStormItemGroup.INSTANCE)));
	
	
}
