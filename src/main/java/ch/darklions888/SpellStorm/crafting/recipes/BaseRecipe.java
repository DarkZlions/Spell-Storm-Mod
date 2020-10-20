package ch.darklions888.SpellStorm.crafting.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public abstract class BaseRecipe implements IRecipe<IInventory> {
	
	protected final ResourceLocation id;
	protected final ResourceLocation key;
	protected final IRecipeSerializer<?> recipeSerializer;
	
	public BaseRecipe(ResourceLocation id, ResourceLocation key, IRecipeSerializer<?> recipeSerializer) {
		this.id = id;
		this.key = key;
		this.recipeSerializer = recipeSerializer;
	}
	
	@Override
	public IRecipeType<?> getType() {
		return Registry.RECIPE_TYPE.getOrDefault(this.key);
	}
	
	@Override
	public ResourceLocation getId() {
		return this.id;
	}
	
	public ResourceLocation getKey() {
		return this.key;
	}
	
	@Override
	public IRecipeSerializer<?> getSerializer() {
		return this.recipeSerializer;
	}
}
