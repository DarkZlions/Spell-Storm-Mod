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

	
	
	@Override
	public MagicalForgeRecipe read(ResourceLocation recipeId, JsonObject json) {
		ItemStack result = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), true);
	    JsonElement jsonelement = (JsonElement)(JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient"));
	    Ingredient ingredient = Ingredient.deserialize(jsonelement);
		float experience = JSONUtils.getFloat(json, "experience");
		int cookTime = JSONUtils.getInt(json, "mergingTime");
		String group = JSONUtils.getString(json, "group", "");
		
		return new MagicalForgeRecipe(recipeId, group, ingredient, result, experience, cookTime);
	}

	@Override
	public MagicalForgeRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
		ItemStack result = buffer.readItemStack();
		Ingredient ingredient = Ingredient.read(buffer);
		float experience = buffer.readFloat();
		int cookTime = buffer.readInt();
		String group = buffer.readString();
		
		return new MagicalForgeRecipe(recipeId, group, ingredient, result, experience, cookTime);
	}

	@Override
	public void write(PacketBuffer buffer, MagicalForgeRecipe recipe) {
		buffer.writeString(recipe.group);
		recipe.ingredient.write(buffer);
		buffer.writeItemStack(recipe.result);
		buffer.writeFloat(recipe.experience);
		buffer.writeVarInt(recipe.mergingTime);
	}

}
