package ch.darklions888.SpellStorm.data.recipe;

import java.util.function.Consumer;

import com.google.gson.JsonObject;

import ch.darklions888.SpellStorm.registries.RecipeSerializerInit;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("deprecation")
public class ForgeRecipeBuilder {

	private final Item result;
	private final Ingredient ingredient;
	private final int mergingTime;
	private final Advancement.Builder advancementBuilder = Advancement.Builder.builder();
	
	public ForgeRecipeBuilder(IItemProvider resultIn, int mergingTime, IItemProvider ... itemsIn) {
		this(resultIn, mergingTime, Ingredient.fromItems(itemsIn));
	}
	
	public ForgeRecipeBuilder(IItemProvider resultIn, int mergingTime, Ingredient ingredientIn) {
		this.result = resultIn.asItem();
		this.ingredient = ingredientIn;
		this.mergingTime = mergingTime;
	}
	
	public static ForgeRecipeBuilder magicalForgeRecipe(IItemProvider resultIn, int mergingTime, IItemProvider ... itemsIn) {
		return new ForgeRecipeBuilder(resultIn, mergingTime, itemsIn);
	}
	
	public static ForgeRecipeBuilder magicalForgeRecipe(IItemProvider resultIn, int mergingTime, Ingredient ingredientIn) {
		return new ForgeRecipeBuilder(resultIn, mergingTime, ingredientIn);
	}
	
	public ForgeRecipeBuilder addCriterion(String name, ICriterionInstance criterionIn) {
		this.advancementBuilder.withCriterion(name, criterionIn);
		return this;
	}

	public void build(Consumer<IFinishedRecipe> consumerIn) {
		this.build(consumerIn, Registry.ITEM.getKey(this.result));
	}

	public void build(Consumer<IFinishedRecipe> consumerIn, String save) {
		ResourceLocation resourcelocation = Registry.ITEM.getKey(this.result);
		if ((new ResourceLocation(save)).equals(resourcelocation)) {
			throw new IllegalStateException("Magical Forge Recipe " + save + " should remove its 'save' argument");
		} else {
			this.build(consumerIn, new ResourceLocation(save));
		}
	}

	public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
		this.validate(id);
		this.advancementBuilder.withParentId(new ResourceLocation("recipes/root"))
				.withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(id))
				.withRewards(AdvancementRewards.Builder.recipe(id)).withRequirementsStrategy(IRequirementsStrategy.OR);
		
		consumerIn.accept(new ForgeRecipeBuilder.Result(id, this.result, this.ingredient, this.mergingTime, this.advancementBuilder,  
				new ResourceLocation(id.getNamespace(), "recipes/" + this.result.getGroup().getPath() + "/" + id.getPath())));
	}

	private void validate(ResourceLocation id) {
		if (this.advancementBuilder.getCriteria().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + id);
		}
	}
	
	public static class Result implements IFinishedRecipe {

	    private final ResourceLocation id;
		private final Item result;
		private final Ingredient ingredient;
		private final int mergingTime;
	    private final Advancement.Builder advancementBuilder;
	    private final ResourceLocation advancementId;
	    
	    public Result(ResourceLocation idIn, Item resultIn, Ingredient ingredientIn, int mergingTime, Advancement.Builder builderIn, ResourceLocation advancementId) {
	    	this.id = idIn;
	    	this.result = resultIn;
	    	this.ingredient = ingredientIn;
	    	this.mergingTime = mergingTime;
	    	this.advancementBuilder = builderIn;
	    	this.advancementId = advancementId;
	    }
		
		@Override
		public void serialize(JsonObject json) {
			json.add("ingredient", ingredient.serialize());
			JsonObject jobj = new JsonObject();
			jobj.addProperty("item", Registry.ITEM.getKey(this.result).toString());
			json.add("result", jobj);
			json.addProperty("mergingtime", this.mergingTime);
		}

		@Override
		public ResourceLocation getID() {
			return this.id;
		}

		@Override
		public IRecipeSerializer<?> getSerializer() {
			return RecipeSerializerInit.MAGICAL_FORGE_SERIALIZER.get();
		}

		@Override
		public JsonObject getAdvancementJson() {
			return this.advancementBuilder.serialize();
		}

		@Override
		public ResourceLocation getAdvancementID() {
			return this.advancementId;
		}
		
	}
}
