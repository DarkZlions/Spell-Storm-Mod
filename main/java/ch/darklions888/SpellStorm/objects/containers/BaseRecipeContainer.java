package ch.darklions888.SpellStorm.objects.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class BaseRecipeContainer<C extends IInventory> extends Container {

	protected BaseRecipeContainer(ContainerType<?> type, int id) {
		super(type, id);
	}
	
	public abstract void fillStackedContents(RecipeItemHelper helper);
	
	public abstract void clear();
	
	public abstract boolean matches (IRecipe<? super C> recipeIn);
	
	public abstract int getOutputSlot();
	
	public abstract int getWidth();
	
	public abstract int getHeight();
	
	@OnlyIn(Dist.CLIENT)
	public abstract int getSize();
}
