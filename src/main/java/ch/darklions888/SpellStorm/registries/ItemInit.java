package ch.darklions888.SpellStorm.registries;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerType;
import ch.darklions888.SpellStorm.lib.ManaPower;
import ch.darklions888.SpellStorm.objects.itemgroup.SpellStormItemGroup;
import ch.darklions888.SpellStorm.objects.items.BaseBlockItem;
import ch.darklions888.SpellStorm.objects.items.BaseItem;
import ch.darklions888.SpellStorm.objects.items.BlueCrystalItem;
import ch.darklions888.SpellStorm.objects.items.BookOfMana;
import ch.darklions888.SpellStorm.objects.items.BookOfSpellsItem;
import ch.darklions888.SpellStorm.objects.items.CorruptedCrystalShardItem;
import ch.darklions888.SpellStorm.objects.items.CrystalShardItem;
import ch.darklions888.SpellStorm.objects.items.Foods;
import ch.darklions888.SpellStorm.objects.items.MagicalInkItem;
import ch.darklions888.SpellStorm.objects.items.SoulCatcherItem;
import ch.darklions888.SpellStorm.objects.items.spells.PageOfAggression;
import ch.darklions888.SpellStorm.objects.items.spells.PageOfDragonBall;
import ch.darklions888.SpellStorm.objects.items.spells.PageOfFallingRocks;
import ch.darklions888.SpellStorm.objects.items.spells.PageOfFireballs;
import ch.darklions888.SpellStorm.objects.items.spells.PageOfHealing;
import ch.darklions888.SpellStorm.objects.items.spells.PageOfMining;
import ch.darklions888.SpellStorm.objects.items.spells.PageOfTheWithers;
import ch.darklions888.SpellStorm.objects.items.spells.PageOfThunder;
import ch.darklions888.SpellStorm.objects.items.weapons.ManaInfusedSwordItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
	private static final ItemGroup TAB = SpellStormItemGroup.INSTANCE;
	
	public static final DeferredRegister<Item> REGISTER_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Lib.MOD_ID);

	//item
	public static final RegistryObject<Item> CRYSTAL = REGISTER_ITEMS.register("crystal", () -> new CrystalShardItem(MagicSource.NEUTRALMAGIC, ManaPower.LOW, null, true, new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> CRYTSAL_PIECES = REGISTER_ITEMS.register("crystal_pieces", () -> new BaseItem(MagicSource.NEUTRALMAGIC, ManaPower.VERYLOW, null, true, new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_PAPER = REGISTER_ITEMS.register("magical_paper", () -> new BaseItem(MagicSource.NEUTRALMAGIC, ManaPower.MEDIUM, null, true, new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> CORRUPTED_CRYSTAL = REGISTER_ITEMS.register("corrupted_crystal", () -> new CorruptedCrystalShardItem(MagicSource.UNKNOWNMAGIC,  ManaPower.HIGH,  null,  true,  new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> BLUE_CRYSTALS = REGISTER_ITEMS.register("blue_crystals", () -> new BlueCrystalItem(new Item.Properties().group(TAB).food(Foods.BLUE_CRYSTALS)));
	
	//Pages
	public static final RegistryObject<Item> PAGE_OF_THE_WITHER_SKULL = REGISTER_ITEMS.register("page_of_the_wither_skull", ()  -> new PageOfTheWithers(new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_AGGRESSION = REGISTER_ITEMS.register("page_of_aggression", () -> new PageOfAggression(new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_THUNDER = REGISTER_ITEMS.register("page_of_thunder", () -> new PageOfThunder(new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_FALLING_ROCK = REGISTER_ITEMS.register("page_of_falling_rock", () -> new PageOfFallingRocks(new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_FIREBALLS = REGISTER_ITEMS.register("page_of_fireballs", () -> new PageOfFireballs(new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_DRAGONBALL = REGISTER_ITEMS.register("page_of_dragonball", () -> new PageOfDragonBall(new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_MINING = REGISTER_ITEMS.register("page_of_mining", () -> new PageOfMining(new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> PAGE_OF_HEALING = REGISTER_ITEMS.register("page_of_healing", () -> new PageOfHealing(new Item.Properties().group(TAB)));
	
	//Other magic items
	public static final RegistryObject<Item> SOUL_CATCHER = REGISTER_ITEMS.register("soul_catcher", () -> new SoulCatcherItem(new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> BOOK_OF_MANA = REGISTER_ITEMS.register("book_of_mana", () -> new BookOfMana(new MagicSource[] { MagicSource.LIGHTMAGIC, MagicSource.DARKMAGIC, MagicSource.NEUTRALMAGIC, MagicSource.UNKNOWNMAGIC }, ManaContainerType.GIGANTIC, new Item.Properties().group(TAB).maxStackSize(1), false)); 
	public static final RegistryObject<Item> BOOK_OF_MANA_CREATIVE = REGISTER_ITEMS.register("book_of_mana_creative", () -> new BookOfMana(new MagicSource[] { MagicSource.LIGHTMAGIC, MagicSource.DARKMAGIC, MagicSource.NEUTRALMAGIC, MagicSource.UNKNOWNMAGIC }, ManaContainerType.GIGANTIC, new Item.Properties().group(TAB).maxStackSize(1), true));
	public static final RegistryObject<Item> BOOK_OF_SPELLS = REGISTER_ITEMS.register("book_of_spells", () -> new BookOfSpellsItem(new Item.Properties().group(TAB).maxStackSize(1)));
	public static final RegistryObject<Item> MAGICAL_INK = REGISTER_ITEMS.register("magical_ink", () -> new MagicalInkItem(null, new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_INK_UNKNOWN = REGISTER_ITEMS.register("magical_ink_unknown", ()-> new MagicalInkItem(MagicSource.UNKNOWNMAGIC, new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_INK_DARK = REGISTER_ITEMS.register("magical_ink_dark", () -> new MagicalInkItem(MagicSource.DARKMAGIC, new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_INK_LIGHT = REGISTER_ITEMS.register("magical_ink_light", () -> new MagicalInkItem(MagicSource.LIGHTMAGIC, new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_INK_NEUTRAL = REGISTER_ITEMS.register("magical_ink_neutral", () -> new MagicalInkItem(MagicSource.NEUTRALMAGIC, new Item.Properties().group(TAB)));
	
	// ManaInfused Gear
	public static final RegistryObject<Item> MANA_INFUSED_INGOT = REGISTER_ITEMS.register("mana_infused_ingot", () -> new BaseItem(MagicSource.NEUTRALMAGIC, ManaPower.MEDIUM, null, true, new Item.Properties().group(TAB).maxStackSize(16)));
	public static final RegistryObject<Item> MANA_INFUSED_SWORD = REGISTER_ITEMS.register("mana_infused_sword",  () -> new ManaInfusedSwordItem(MagicSource.NEUTRALMAGIC, ManaPower.HIGH, ManaContainerType.MEDIUM, new Item.Properties().group(TAB).maxStackSize(1)));
	
	//BlockItem
	public static final RegistryObject<Item> CRYSTAL_ORE_BLOCK = REGISTER_ITEMS.register("crystal_ore", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.MEDIUM, TextFormatting.LIGHT_PURPLE, false, BlockInit.CRYSTAL_ORE.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MANAINFUSER = REGISTER_ITEMS.register("manainfuser", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.HIGH, null, false, BlockInit.MANAINFUSER.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> SOUL_EXTRACTOR = REGISTER_ITEMS.register("soul_extractor", () -> new BaseBlockItem(MagicSource.UNKNOWNMAGIC, ManaPower.VERYHIGH, null, false, BlockInit.SOUL_EXTRACTOR.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_WOOD_LOG = REGISTER_ITEMS.register("magical_wood_log", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.LOW, null, false, BlockInit.MAGICAL_WOOD_LOG.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_WOOD_PLANK = REGISTER_ITEMS.register("magical_planks", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.LOW, null, false, BlockInit.MAGICAL_WOOD_PLANK.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_LEAVES = REGISTER_ITEMS.register("magical_leaves", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.LOW, null, false, BlockInit.MAGICAL_LEAVES.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_TREE_SAPLING = REGISTER_ITEMS.register("magical_tree_sapling", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.VERYLOW, null, false, BlockInit.MAGICAL_TREE_SAPLING.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> CORRUPTED_CRYSTAL_ORE_BLOCK = REGISTER_ITEMS.register("corrupted_crystal_ore", () -> new BaseBlockItem(MagicSource.UNKNOWNMAGIC, ManaPower.VERYHIGH, TextFormatting.BLACK, false, BlockInit.CORRUPTED_CRYSTAL_ORE.get(), new Item.Properties().group(TAB)));
	public static final RegistryObject<Item> MAGICAL_FORGE_BLOCK = REGISTER_ITEMS.register("magical_forge", () -> new BaseBlockItem(MagicSource.NEUTRALMAGIC, ManaPower.HIGH, null, false, BlockInit.MAGICAL_FORGE.get(), new Item.Properties().group(TAB)));

}
