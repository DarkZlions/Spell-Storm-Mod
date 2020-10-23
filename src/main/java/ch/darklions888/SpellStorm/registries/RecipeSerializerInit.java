package ch.darklions888.SpellStorm.registries;

import ch.darklions888.SpellStorm.crafting.recipes.MagicalForgeRecipe;
import ch.darklions888.SpellStorm.crafting.recipes.MagicalForgeSerializer;
import ch.darklions888.SpellStorm.lib.Lib;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializerInit {
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Lib.MOD_ID);

	public static final IRecipeType<MagicalForgeRecipe> MAGICAL_FORGE_TYPE = registerType(MagicalForgeRecipe.KEY);
	public static final RegistryObject<IRecipeSerializer<MagicalForgeRecipe>> MAGICAL_FORGE_SERIALIZER = RECIPE_SERIALIZER.register(Lib.RegistryNames.MAGICAL_FORGE_RECIPE_ID, () -> new MagicalForgeSerializer());
	
	@SuppressWarnings("unchecked")
	private static <T extends IRecipeType<?>> T registerType(ResourceLocation recipeTypeId) {
		return (T) Registry.register(Registry.RECIPE_TYPE, recipeTypeId, new RecipeType<>());
	}
	
	private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {
		@Override
		public String toString() {
			return Registry.RECIPE_TYPE.getKey(this).toString();
		}
	}
}
