package ch.darklions888.SpellStorm.data.recipe;

import java.util.function.Consumer;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.items.MagicalInkItem;
import ch.darklions888.SpellStorm.objects.items.spells.AbstractPageItem;
import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("deprecation")
public class RecipeDataProvider extends RecipeProvider {

	public RecipeDataProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {	
		this.registerPages(consumer);
		this.registerMain(consumer);
		this.registerForgeRecipes(consumer);
	}
	
	private void registerMain(Consumer<IFinishedRecipe> consumer) {
		
		/////////////////////////////////////////////////////////////////
		// Shaped Recipes
		/////////////////////////////////////////////////////////////////
		
		ShapedRecipeBuilder
			.shapedRecipe(ItemInit.BOOK_OF_MANA.get())
			.key('B', Items.BOOK)
			.key('M', ItemInit.MAGICAL_PAPER.get())
			.key('L', ItemInit.MAGICAL_WOOD_LOG.get())
			.patternLine("LML")
			.patternLine("MBM")
			.patternLine("LML")
			.addCriterion("has_book", hasItem(Items.BOOK))
			.setGroup("magical")
			.build(consumer);
		
		ShapedRecipeBuilder
			.shapedRecipe(ItemInit.BOOK_OF_SPELLS.get())
			.key('B', Items.WRITABLE_BOOK)
			.key('M', ItemInit.MAGICAL_PAPER.get())
			.key('L', ItemInit.MAGICAL_WOOD_LOG.get())
			.patternLine("LML")
			.patternLine("MBM")
			.patternLine("LML")
			.addCriterion("has_book", hasItem(Items.BOOK))
			.setGroup("magical")
			.build(consumer);
		
		ShapedRecipeBuilder
			.shapedRecipe(ItemInit.CORRUPTED_ENDEREYE.get())
			.key('E', Items.ENDER_EYE)
			.key('C', ItemInit.CORRUPTED_CRYSTAL.get())
			.patternLine(" C ")
			.patternLine("CEC")
			.patternLine(" C ")
			.addCriterion("has_eye", hasItem(Items.ENDER_EYE))
			.setGroup("corrupted")
			.build(consumer);
		
		ShapedRecipeBuilder
			.shapedRecipe(ItemInit.GATEWAY_CORE_BLOCK.get())
			.key('P', Items.PURPUR_PILLAR)
			.key('B', Items.PURPUR_BLOCK)
			.patternLine("BPB")
			.patternLine("P P")
			.patternLine("BPB")
			.addCriterion("has_fragment", hasItem(ItemInit.END_GATEWAY_FRAGMENT.get()))
			.setGroup("corrupted")
			.build(consumer);
		
		ShapedRecipeBuilder
			.shapedRecipe(ItemInit.MAGICAL_FORGE_BLOCK.get())
			.key('O', Items.OBSIDIAN)
			.key('B', Items.BLAZE_POWDER)
			.key('G', Items.GLASS)
			.key('C', ItemInit.MAGICAL_FIREBALL.get())
			.patternLine("OBO")
			.patternLine("OGO")
			.patternLine("OCO")
			.addCriterion("has_fireball", hasItem(ItemInit.MAGICAL_FIREBALL.get()))
			.setGroup("magical")
			.build(consumer);	
		
		ShapedRecipeBuilder
			.shapedRecipe(ItemInit.MAGICAL_PAPER.get())
			.key('P', Items.PAPER)
			.key('C', ItemInit.CRYSTAL_PIECES.get())
			.patternLine(" C ")
			.patternLine("CPC")
			.patternLine(" C ")
			.addCriterion("has_crystal", hasItem(ItemInit.CRYSTAL_PIECES.get()))
			.setGroup("magical")
			.build(consumer);
		
		ShapedRecipeBuilder
			.shapedRecipe(ItemInit.MANAINFUSER.get())
			.key('P', ItemInit.CRYSTAL_PIECES.get())
			.key('C', ItemInit.CRYSTAL.get())
			.key('E', Items.ENCHANTING_TABLE)
			.key('O', Items.CRYING_OBSIDIAN)
			.patternLine(" C ")
			.patternLine("PEP")
			.patternLine("OOO")
			.addCriterion("has_crystal", hasItem(ItemInit.CRYSTAL.get()))
			.setGroup("magical")
			.build(consumer);
		
		ShapedRecipeBuilder
			.shapedRecipe(ItemInit.SOUL_CATCHER.get())
			.key('C', ItemInit.CORRUPTED_CRYSTAL.get())
			.key('B', Items.IRON_BARS)
			.key('O', Items.OBSIDIAN)
			.patternLine("OBO")
			.patternLine("BCB")
			.patternLine("OBO")
			.addCriterion("has_corrupted_crystal", hasItem(ItemInit.CORRUPTED_CRYSTAL.get()))
			.setGroup("soul")
			.build(consumer);
		
		ShapedRecipeBuilder
			.shapedRecipe(ItemInit.SOUL_EXTRACTOR.get())
			.key('C', ItemInit.SOUL_CATCHER.get())
			.key('S', Items.SOUL_SAND)
			.key('B', Items.POLISHED_BLACKSTONE_BRICKS)
			.key('L', Items.SOUL_LANTERN)
			.patternLine(" C ")
			.patternLine("SSS")
			.patternLine("BLB")
			.addCriterion("has_soul_catcher", hasItem(ItemInit.SOUL_CATCHER.get()))
			.setGroup("soul")
			.build(consumer);
		
		/////////////////////////////////////////////////////////////////
		// Shapeless Recipes
		/////////////////////////////////////////////////////////////////
		
		ShapelessRecipeBuilder
			.shapelessRecipe(ItemInit.CRYSTAL_PIECES.get(), 2)
			.addIngredient(ItemInit.CRYSTAL.get())
			.addCriterion("has_crystal", hasItem(ItemInit.CRYSTAL.get()))
			.setGroup("magical")
			.build(consumer);
		
		ShapelessRecipeBuilder
			.shapelessRecipe(ItemInit.CRYSTAL.get())
			.addIngredient(ItemInit.CRYSTAL_PIECES.get(), 3)
			.addCriterion("has_crystal_pieces", hasItem(ItemInit.CRYSTAL_PIECES.get()))
			.setGroup("magical")
			.build(consumer);
			
		
		ShapelessRecipeBuilder
			.shapelessRecipe(ItemInit.MAGICAL_INK.get())
			.addIngredient(ItemInit.CRYSTAL_PIECES.get())
			.addIngredient(Items.GLASS_BOTTLE)
			.addIngredient(Items.INK_SAC)
			.addIngredient(Items.FEATHER)
			.addCriterion("has_crystal", hasItem(ItemInit.CRYSTAL_PIECES.get()))
			.setGroup("pages")
			.build(consumer);
		
		ShapelessRecipeBuilder
			.shapelessRecipe(ItemInit.MAGICAL_WOOD_PLANK.get(), 4)
			.addIngredient(ItemInit.MAGICAL_WOOD_LOG.get())
			.addCriterion("has_wood", hasItem(ItemInit.MAGICAL_WOOD_LOG.get()))
			.setGroup("magical")
			.build(consumer);
		

	}
	
	private void registerForgeRecipes(Consumer<IFinishedRecipe> consumer) {
		/////////////////////////////////////////////////////////////////
		// Magical Forge Recipe
		/////////////////////////////////////////////////////////////////
		
		ForgeRecipeBuilder
			.magicalForgeRecipe(ItemInit.MANA_INFUSED_INGOT.get(), 400, ItemInit.CRYSTAL.get(), Items.IRON_INGOT)
			.addCriterion("has_forge", hasItem(ItemInit.MAGICAL_FORGE_BLOCK.get()))
			.build(consumer);
		
		ForgeRecipeBuilder
			.magicalForgeRecipe(ItemInit.BLUE_CRYSTALS.get(), 400, ItemInit.CRYSTAL.get(), Items.LAPIS_LAZULI)
			.addCriterion("has_forge", hasItem(ItemInit.MAGICAL_FORGE_BLOCK.get()))
			.build(consumer);
	}
	
	/////////////////////////////////////////////////////////////////
	// Pages Recipes
	/////////////////////////////////////////////////////////////////
	private void registerPages(Consumer<IFinishedRecipe> consumer) {
		createPage(consumer, ItemInit.PAGE_OF_AGGRESSION.get(), Items.BLAZE_POWDER);
		createPage(consumer, ItemInit.PAGE_OF_DRAGONBALL.get(), Items.DRAGON_BREATH);
		createPage(consumer, ItemInit.PAGE_OF_FALLING_ROCK.get(), Items.OBSIDIAN);
		createPage(consumer, ItemInit.PAGE_OF_FANGS.get(), ItemInit.FANGS.get());
		createPage(consumer, ItemInit.PAGE_OF_FIREBALLS.get(), ItemInit.MAGICAL_FIREBALL.get());
		createPage(consumer, ItemInit.PAGE_OF_HEALING.get(), Items.GHAST_TEAR);
		createPage(consumer, ItemInit.PAGE_OF_MINING.get(), Items.NETHERITE_PICKAXE);
		createPage(consumer, ItemInit.PAGE_OF_THUNDER.get(), ItemInit.CHARGED_CREEPER_PIECES.get());
		createPage(consumer, ItemInit.PAGE_OF_THE_WITHER_SKULL.get(), Items.WITHER_ROSE);
	}
	
	
	private static void createPage(Consumer<IFinishedRecipe> consumer, IItemProvider pageIn, IItemProvider coreItem) {
		
		if (pageIn instanceof AbstractPageItem) {
			
			Lib.LOGGER.debug("Generated recipe for: " + Registry.ITEM.getKey(pageIn.asItem()));
		} else {
			Lib.LOGGER.warn(Registry.ITEM.getKey(pageIn.asItem()) + "is not a instanceof AbstractPageItem");
			return;
		}
		
		ShapedRecipeBuilder
			.shapedRecipe(pageIn)
			.key('#', coreItem)
			.key('C', ItemInit.CRYSTAL.get())
			.key('P', ItemInit.MAGICAL_PAPER.get())
			.key('I', MagicalInkItem.getInkForSource(((AbstractPageItem) pageIn).getMagicSourceList(null).get(0)))
			.patternLine("PCP")
			.patternLine("C#C")
			.patternLine("PIP")
			.addCriterion("has_coreitem", hasItem(pageIn))
			.setGroup("magical_pages")
			.build(consumer);
	}
	
	@Override
	public String getName() {
		return "Spell Storm Crafting Recipes";
	}
}
