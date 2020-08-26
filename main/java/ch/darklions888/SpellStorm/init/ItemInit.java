package ch.darklions888.SpellStorm.init;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerSize;
import ch.darklions888.SpellStorm.lib.ManaPower;
import ch.darklions888.SpellStorm.objects.itemgroup.SpellStormItemGroup;
import ch.darklions888.SpellStorm.objects.items.BaseBlockItem;
import ch.darklions888.SpellStorm.objects.items.BaseItem;
import ch.darklions888.SpellStorm.objects.items.BookOfMana;
import ch.darklions888.SpellStorm.objects.items.BookOfSpellsItem;
import ch.darklions888.SpellStorm.objects.items.CrytalShardItem;
import ch.darklions888.SpellStorm.objects.items.SoulCatcherItem;
import ch.darklions888.SpellStorm.objects.items.pages.PageOfAggression;
import ch.darklions888.SpellStorm.objects.items.pages.PageOfDragonBall;
import ch.darklions888.SpellStorm.objects.items.pages.PageOfFallingRocks;
import ch.darklions888.SpellStorm.objects.items.pages.PageOfFireballs;
import ch.darklions888.SpellStorm.objects.items.pages.PageOfMining;
import ch.darklions888.SpellStorm.objects.items.pages.PageOfTheWithers;
import ch.darklions888.SpellStorm.objects.items.pages.PageOfThunder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit 
{
	private static final ItemGroup TAB = SpellStormItemGroup.INSTANCE;
	
	public static final DeferredRegister<Item> REGISTER_ITEMS = new DeferredRegister<Item>(ForgeRegistries.ITEMS, SpellStormMain.MODID);
	
	
	//item
	public static final RegistryObject<Item> CRYSTAL = REGISTER_ITEMS.register("crystal", () -> new CrytalShardItem(MagicSource.NEUTRALMAGIC, ManaPower.LOW, null, true, new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> CRYTSAL_PIECES = REGISTER_ITEMS.register("crystal_pieces", () -> new BaseItem(MagicSource.NEUTRALMAGIC, ManaPower.VERYLOW, null, true, new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_PAPER = REGISTER_ITEMS.register("magical_paper", () -> new BaseItem(MagicSource.NEUTRALMAGIC, ManaPower.MEDIUM, null, true, new Item.Properties().group(TAB)));
	
	//Pages
	public static final RegistryObject<Item> PAGE_OF_THE_WITHER_SKULL = REGISTER_ITEMS.register("page_of_the_wither_skull", ()  -> new PageOfTheWithers(ManaContainerSize.OCEAN, MagicSource.DARKMAGIC, ManaPower.VERYHIGH, TextFormatting.DARK_RED, true, new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_AGGRESSION = REGISTER_ITEMS.register("page_of_aggression", () -> new PageOfAggression(ManaContainerSize.MEDIUM, MagicSource.DARKMAGIC, ManaPower.MEDIUM, TextFormatting.DARK_RED, true, new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_THUNDER = REGISTER_ITEMS.register("page_of_thunder", () -> new PageOfThunder(ManaContainerSize.SMALL, MagicSource.LIGHTMAGIC, ManaPower.HIGH, TextFormatting.YELLOW, true, new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_FALLING_ROCK = REGISTER_ITEMS.register("page_of_falling_rock", () -> new PageOfFallingRocks(ManaContainerSize.SMALL, MagicSource.UNKNOWNMAGIC, ManaPower.VERYHIGH, TextFormatting.BLACK, true, new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_FIREBALLS = REGISTER_ITEMS.register("page_of_fireballs", () -> new PageOfFireballs(ManaContainerSize.MEDIUM, MagicSource.NEUTRALMAGIC, ManaPower.LOW, TextFormatting.YELLOW, true, new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_DRAGONBALL = REGISTER_ITEMS.register("page_of_dragonball", () -> new PageOfDragonBall(ManaContainerSize.SMALL, MagicSource.UNKNOWNMAGIC, ManaPower.VERYHIGH, TextFormatting.DARK_PURPLE, true, new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_MINING = REGISTER_ITEMS.register("page_of_mining", () -> new PageOfMining(ManaContainerSize.BIGGER, MagicSource.NEUTRALMAGIC, ManaPower.MEDIUM, TextFormatting.GRAY, true, new Item.Properties().group(TAB).maxStackSize(1)));
	
	//Other magic items
	public static final RegistryObject<Item> SOUL_CATCHER = REGISTER_ITEMS.register("soul_catcher", () -> new SoulCatcherItem(new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> BOOK_OF_MANA = REGISTER_ITEMS.register("book_of_mana", () -> new BookOfMana(new MagicSource[] { MagicSource.LIGHTMAGIC, MagicSource.DARKMAGIC, MagicSource.NEUTRALMAGIC, MagicSource.UNKNOWNMAGIC }, ManaContainerSize.REALLYBIG, new Item.Properties().group(TAB).maxStackSize(1))); 
	public static final RegistryObject<Item> BOOK_OF_SPELLS = REGISTER_ITEMS.register("book_of_spells", () -> new BookOfSpellsItem(new Item.Properties().group(TAB).maxStackSize(1)));
	
	//BlockItem
	public static final RegistryObject<Item> CRYSTAL_ORE_BLOCK = REGISTER_ITEMS.register("crystal_ore", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.MEDIUM, TextFormatting.LIGHT_PURPLE, false, BlockInit.CRYSTAL_ORE.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MANAINFUSER = REGISTER_ITEMS.register("manainfuser", () -> new BaseBlockItem(MagicSource.UNKNOWNMAGIC, ManaPower.HIGH, null, false, BlockInit.MANAINFUSER.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> SOUL_EXTRACTOR = REGISTER_ITEMS.register("soul_extractor", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.VERYHIGH, null, false, BlockInit.SOUL_EXTRACTOR.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_WOOD_LOG = REGISTER_ITEMS.register("magical_wood_log", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.LOW, null, false, BlockInit.MAGICAL_WOOD_LOG.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_WOOD_PLANK = REGISTER_ITEMS.register("magical_wood_plank", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.LOW, null, false, BlockInit.MAGICAL_WOOD_PLANK.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_LEAVES = REGISTER_ITEMS.register("magical_leaves", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.LOW, null, false, BlockInit.MAGICAL_LEAVES.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_TREE_SAPLING = REGISTER_ITEMS.register("magical_tree_sapling", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.VERYLOW, null, false, BlockInit.MAGICAL_TREE_SAPLING.get(), new Item.Properties().group(TAB)));
	
}
