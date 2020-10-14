package ch.darklions888.SpellStorm.crafting.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class MagicalForgeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MagicalForgeRecipe>{

	/*
	 * Serialize the variables always in the right order 
	 */
	
	@Override
	public MagicalForgeRecipe read(ResourceLocation recipeId, JsonObject json) {
	    JsonElement ingredientJson = (JsonElement)(JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient"));
	    Ingredient ingredient = Ingredient.deserialize(ingredientJson);
	    ItemStack result = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), true);
		int cookTime = JSONUtils.getInt(json, "mergingtime");
		
		return new MagicalForgeRecipe(recipeId, ingredient, result, cookTime);
	}

	@Override
	public MagicalForgeRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
		Ingredient ingredient = Ingredient.read(buffer);
		ItemStack result = buffer.readItemStack();	
		int cookTime = buffer.readVarInt(); // readVarInt not readInt you stupid idiot
		
		return new MagicalForgeRecipe(recipeId, ingredient, result, cookTime);
	}

	@Override
	public void write(PacketBuffer buffer, MagicalForgeRecipe recipe) {
		recipe.getIngredients().get(0).write(buffer);
		buffer.writeItemStack(recipe.result, false);
		buffer.writeVarInt(recipe.mergingTime);
	}

}
