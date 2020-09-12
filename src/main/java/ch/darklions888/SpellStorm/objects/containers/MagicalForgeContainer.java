package ch.darklions888.SpellStorm.objects.containers;

import ch.darklions888.SpellStorm.crafting.recipes.MagicalForgeRecipe;
import ch.darklions888.SpellStorm.objects.containers.slots.FuelSlot;
import ch.darklions888.SpellStorm.objects.containers.slots.MagicalForgeResultSlot;
import ch.darklions888.SpellStorm.objects.containers.slots.MagicalItemSlot;
import ch.darklions888.SpellStorm.registries.ContainerTypesInit;
import ch.darklions888.SpellStorm.registries.RecipeSerializerInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

public class MagicalForgeContainer extends BaseRecipeContainer<IInventory> {

	private final IInventory inv;
	private final IIntArray data;
	private final World world;
	private final IRecipeType<MagicalForgeRecipe> recipeType;

	public MagicalForgeContainer(int id, PlayerInventory playerInventory, IInventory inv, IIntArray forgeData) {
		super((ContainerType<?>)ContainerTypesInit.MAGICAL_FORGE.get(), id);
		this.recipeType = RecipeSerializerInit.MAGICAL_FORGE_TYPE;
		assertInventorySize(inv, 4);
		assertIntArraySize(forgeData, 4);
		this.inv = inv;
		this.data = forgeData;
		this.world = playerInventory.player.world;
		this.addSlot(new MagicalItemSlot(inv, 0, 23, 17));
		this.addSlot(new Slot(inv, 1, 57, 17));
		this.addSlot(new FuelSlot(inv, 2, 40, 53));
		this.addSlot(new MagicalForgeResultSlot(playerInventory.player, inv, 3, 116, 35));
		
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
		}
		
		this.trackIntArray(forgeData);
	}
	
	@Override
	public boolean matches(IRecipe<? super IInventory> recipeIn) {
		return recipeIn.matches(this.inv, this.world);
	}

	@Override
	public void fillStackedContents(RecipeItemHelper helper) {
		if (this.inv instanceof IRecipeHelperPopulator) {
			((IRecipeHelperPopulator)this.inv).fillStackedContents(helper);
		}
	}

	@Override
	public void clear() {
		this.inv.clear();
	}

	@Override
	public int getOutputSlot() {
		return 3;
	}

	@Override
	public int getWidth() {
		return 1;
	}

	@Override
	public int getHeight() {
		return 1;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public int getSize() {
		return 4;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.inv.isUsableByPlayer(playerIn);
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack inventoryStackCopy = ItemStack.EMPTY;		
		Slot slot = this.inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			
			ItemStack inventoryStack = slot.getStack();			
			inventoryStackCopy = inventoryStack.copy();
			
			if (index == 0) {
				if (!this.mergeItemStack(inventoryStack, 1, 40, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(inventoryStack, inventoryStackCopy);			
			} else if (index == 3) {
				if (!this.mergeItemStack(inventoryStack, 4, 40, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index != 1 && index != 0 && index != 2) {		
				
				if (!this.hasRecipe(inventoryStack)) {
					if (!this.mergeItemStack(inventoryStack, 0, 4, false)) {
						
						return ItemStack.EMPTY;
					}
					
				} else if (this.isFuel(inventoryStack)) {
					
					if (!this.mergeItemStack(inventoryStack, 0, 3, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 4 && index < 31) {
					if (!this.mergeItemStack(inventoryStack, 31, 40, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 31 && index < 40 && !this.mergeItemStack(inventoryStack, 4, 31, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(inventoryStack, 4, 40, false)) {
				return ItemStack.EMPTY;
			}

			if (inventoryStack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (inventoryStack.getCount() == inventoryStackCopy.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, inventoryStack);
		}

		return inventoryStackCopy;
	}

	protected boolean hasRecipe(ItemStack stack) {
		boolean has = this.world.getRecipeManager().getRecipe((IRecipeType<MagicalForgeRecipe>) this.recipeType, this.inv, this.world).isPresent();
		return has;
	}
	
	private boolean isFuel(ItemStack stack) {
		return ForgeHooks.getBurnTime(stack) > 0;
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getMergProgressionScaled() {
		int i = this.data.get(2);
		int j = this.data.get(3);
		return j != 0 && i != 0 ? i * 24 / j : 0;
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getBurnLeftScaled() {
		int i = this.data.get(1);
		if (i == 0) {
			i = 200;
		}
		
		return this.data.get(0) * 13 / i;
	}
	
	@OnlyIn(Dist.CLIENT)
	public boolean isBurning() {
		return this.data.get(0) > 0;
	}
	
	public static MagicalForgeContainer create(int id, PlayerInventory inventory) {
		return new MagicalForgeContainer(id, inventory, new Inventory(4), new IntArray(4));
	}
}
