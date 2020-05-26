package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaContainerSize;
import ch.darklions888.SpellStorm.enums.ManaPower;
import ch.darklions888.SpellStorm.objects.CustomItemGroup.SpellStormItemGroup;
import ch.darklions888.SpellStorm.objects.items.BaseBlockItem;
import ch.darklions888.SpellStorm.objects.items.BaseItem;
import ch.darklions888.SpellStorm.objects.items.CrytalShardItem;
import ch.darklions888.SpellStorm.objects.items.PageOfAggression;
import ch.darklions888.SpellStorm.objects.items.PageOfTheWithers;
import ch.darklions888.SpellStorm.objects.items.PageOfThunder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit 
{
	private static final ItemGroup TAB = SpellStormItemGroup.INSTANCE;
	
	public static final DeferredRegister<Item> REGISTERITEMS = new DeferredRegister<Item>(ForgeRegistries.ITEMS, SpellStormMain.MODID);
	
	
	//item
	public static final RegistryObject<Item> CRYSTAL = REGISTERITEMS.register("crystal", () -> new CrytalShardItem(MagicSource.NEUTRALMAGIC, ManaPower.LOW, null, true, new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> CRYTSAL_PIECES = REGISTERITEMS.register("crystal_pieces", () -> new BaseItem(MagicSource.NEUTRALMAGIC, ManaPower.VERYLOW, null, true, new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> UNKNOWN_ITEM = REGISTERITEMS.register("unknown", () -> new BaseItem(MagicSource.DARKMAGIC, ManaPower.VERYHIGH, null, true, new Item.Properties().group(TAB)));

	//Pages
	public static final RegistryObject<Item> PAGE_OF_THE_WITHER_SKULL = REGISTERITEMS.register("page_of_the_wither_skull", ()  -> new PageOfTheWithers(ManaContainerSize.OCEAN, MagicSource.DARKMAGIC, ManaPower.VERYHIGH, TextFormatting.DARK_RED, true, new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_AGGRESSION = REGISTERITEMS.register("page_of_aggression", () -> new PageOfAggression(ManaContainerSize.MEDIUM, MagicSource.DARKMAGIC, ManaPower.MEDIUM, TextFormatting.DARK_RED, true, new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_THUNDER = REGISTERITEMS.register("page_of_thunder", () -> new PageOfThunder(ManaContainerSize.SMALL, MagicSource.LIGHTMAGIC, ManaPower.HIGH, TextFormatting.YELLOW, true, new Item.Properties().group(TAB).maxStackSize(1)));
	
	//BlockItem
	public static final RegistryObject<Item> CRYSTAL_ORE_BLOCK = REGISTERITEMS.register("crystal_ore", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.MEDIUM, TextFormatting.LIGHT_PURPLE, false, BlockInit.CRYSTAL_ORE.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MANAINFUSER = REGISTERITEMS.register("manainfuser", () -> new BaseBlockItem(MagicSource.UNKNOWNMAGIC, ManaPower.HIGH, null, false, BlockInit.MANAINFUSER.get(), new Item.Properties().group(TAB)));
	
}
