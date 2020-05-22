package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaPower;
import ch.darklions888.SpellStorm.objects.CustomItemGroup.SpellStormItemGroup;
import ch.darklions888.SpellStorm.objects.items.BaseBlockItem;
import ch.darklions888.SpellStorm.objects.items.BaseItem;
import ch.darklions888.SpellStorm.objects.items.CrytalShardItem;
import ch.darklions888.SpellStorm.objects.items.PageOfTheWithers;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit 
{
	public static final DeferredRegister<Item> REGISTERITEMS = new DeferredRegister<Item>(ForgeRegistries.ITEMS, SpellStormMain.MODID);
	
	//item
	public static final RegistryObject<Item> CRYSTAL = REGISTERITEMS.register("crystal", () -> new CrytalShardItem(MagicSource.NEUTRALMAGIC, ManaPower.LOW, null, true, new Item.Properties().group(SpellStormItemGroup.INSTANCE)));
	public static final RegistryObject<Item> UNKNOWN_ITEM = REGISTERITEMS.register("unknown", () -> new BaseItem(MagicSource.UNKNOWNMAGIC, ManaPower.VERYHIGH, null, true, new Item.Properties().group(SpellStormItemGroup.INSTANCE)));
	
	//Pages
	public static final RegistryObject<Item> PAGE_OF_THE_WITHER_SKULL = REGISTERITEMS.register("page_of_the_wither_skull", ()  -> new PageOfTheWithers(MagicSource.DARKMAGIC, ManaPower.DARKPOWER, TextFormatting.DARK_RED, true, new Item.Properties().group(SpellStormItemGroup.INSTANCE)));
	
	//BlockItem
	public static final RegistryObject<Item> CRYSTAL_ORE_BLOCK = REGISTERITEMS.register("crystal_ore", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.MEDIUM, TextFormatting.LIGHT_PURPLE, false, BlockInit.crystal_ore, new Item.Properties().group(SpellStormItemGroup.INSTANCE)));
	
	
}
