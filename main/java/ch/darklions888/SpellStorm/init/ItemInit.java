package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaContainerSize;
import ch.darklions888.SpellStorm.enums.ManaPower;
import ch.darklions888.SpellStorm.objects.itemgroup.SpellStormItemGroup;
import ch.darklions888.SpellStorm.objects.items.BaseBlockItem;
import ch.darklions888.SpellStorm.objects.items.BaseItem;
import ch.darklions888.SpellStorm.objects.items.BookOfMana;
import ch.darklions888.SpellStorm.objects.items.CrytalShardItem;
import ch.darklions888.SpellStorm.objects.items.PageOfAggression;
import ch.darklions888.SpellStorm.objects.items.PageOfFallingRocks;
import ch.darklions888.SpellStorm.objects.items.PageOfFireballs;
import ch.darklions888.SpellStorm.objects.items.PageOfTheWithers;
import ch.darklions888.SpellStorm.objects.items.PageOfThunder;
import ch.darklions888.SpellStorm.objects.items.SoulCatcherItem;
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

	//Pages
	public static final RegistryObject<Item> PAGE_OF_THE_WITHER_SKULL = REGISTERITEMS.register("page_of_the_wither_skull", ()  -> new PageOfTheWithers(ManaContainerSize.OCEAN, MagicSource.DARKMAGIC, ManaPower.VERYHIGH, TextFormatting.DARK_RED, true, new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_AGGRESSION = REGISTERITEMS.register("page_of_aggression", () -> new PageOfAggression(ManaContainerSize.MEDIUM, MagicSource.DARKMAGIC, ManaPower.MEDIUM, TextFormatting.DARK_RED, true, new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_THUNDER = REGISTERITEMS.register("page_of_thunder", () -> new PageOfThunder(ManaContainerSize.SMALL, MagicSource.LIGHTMAGIC, ManaPower.HIGH, TextFormatting.YELLOW, true, new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_FALLING_ROCK = REGISTERITEMS.register("page_of_falling_rock", () -> new PageOfFallingRocks(ManaContainerSize.SMALL, MagicSource.UNKNOWNMAGIC, ManaPower.VERYHIGH, TextFormatting.BLACK, true, new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_FIREBALLS = REGISTERITEMS.register("page_of_fireballs", () -> new PageOfFireballs(ManaContainerSize.MEDIUM, MagicSource.NEUTRALMAGIC, ManaPower.LOW, TextFormatting.YELLOW, true, new Item.Properties().group(TAB).maxStackSize(1)));
	
	//Other magic items
	public static final RegistryObject<Item> SOUL_CATCHER = REGISTERITEMS.register("soul_catcher", () -> new SoulCatcherItem(MagicSource.DARKMAGIC, ManaPower.MEDIUM, null, false, new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> BOOK_OF_MANA = REGISTERITEMS.register("book_of_mana", () -> new BookOfMana(new MagicSource[] { MagicSource.LIGHTMAGIC, MagicSource.DARKMAGIC, MagicSource.NEUTRALMAGIC, MagicSource.UNKNOWNMAGIC }, ManaContainerSize.REALLYBIG, new Item.Properties().group(TAB).maxStackSize(1))); 
	
	//BlockItem
	public static final RegistryObject<Item> CRYSTAL_ORE_BLOCK = REGISTERITEMS.register("crystal_ore", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.MEDIUM, TextFormatting.LIGHT_PURPLE, false, BlockInit.CRYSTAL_ORE.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MANAINFUSER = REGISTERITEMS.register("manainfuser", () -> new BaseBlockItem(MagicSource.UNKNOWNMAGIC, ManaPower.HIGH, null, false, BlockInit.MANAINFUSER.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_WOOD_LOG = REGISTERITEMS.register("magical_wood_log", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.LOW, null, false, BlockInit.MAGICAL_WOOD_LOG.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_WOOD_PLANK = REGISTERITEMS.register("magical_wood_plank", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.LOW, null, false, BlockInit.MAGICAL_WOOD_PLANK.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_LEAVES = REGISTERITEMS.register("magical_leaves", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.LOW, null, false, BlockInit.MAGICAL_LEAVES.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_TREE_SAPLING = REGISTERITEMS.register("magical_tree_sapling", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.VERYLOW, null, false, BlockInit.MAGICAL_TREE_SAPLING.get(), new Item.Properties().group(TAB)));
	
}
