package ch.darklions888.SpellStorm.crafting.recipes;

import javax.annotation.Nonnull;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.registries.RecipeSerializerInit;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class MagicalForgeRecipe implements IRecipe<IInventory> {
	
	public static final ResourceLocation KEY = Lib.RegistryNames.MAGICAL_FORGE_RECIPE_RESOURCELOCATION;
	protected final ResourceLocation id;
	protected final Ingredient ingredient;
	protected final ItemStack result;
	protected final int mergingTime;

	public MagicalForgeRecipe(ResourceLocation idIn, Ingredient ingredientIn, ItemStack resultIn, int mergingTime) {
		 this.id = idIn;
		 this.ingredient = ingredientIn;
		 this.result = resultIn;
		 this.mergingTime = mergingTime;
	}
	
	@Override
	public boolean matches(IInventory inv, World worldIn) {
		boolean match = this.ingredient.test(inv.getStackInSlot(0)) && this.ingredient.test(inv.getStackInSlot(1));
		return match;
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv) {
		return this.result.copy();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.result;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return RecipeSerializerInit.MAGICAL_FORGE_SERIALIZER.get();
	}
	
	public int getMergingTime() {
		return this.mergingTime;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = NonNullList.create();
		list.add(this.ingredient);
		return list;
	}
		
	@Nonnull
	@Override
	public IRecipeType<?> getType() {
		return Registry.RECIPE_TYPE.getOrDefault(KEY);
	}
	
	@Override
	public boolean canFit(int width, int height) {
		return true;
	}
}
