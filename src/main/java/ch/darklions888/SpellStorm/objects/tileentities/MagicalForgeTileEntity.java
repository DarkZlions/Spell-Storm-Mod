package ch.darklions888.SpellStorm.objects.tileentities;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import ch.darklions888.SpellStorm.crafting.recipes.MagicalForgeRecipe;
import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.blocks.MagicalForgeBlock;
import ch.darklions888.SpellStorm.objects.containers.MagicalForgeContainer;
import ch.darklions888.SpellStorm.registries.RecipeSerializerInit;
import ch.darklions888.SpellStorm.registries.TileEntityInit;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

@SuppressWarnings("unchecked")
public class MagicalForgeTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {
	
	private NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
	private static final int[] SLOTS_UP = new int[] { 0, 1 };
	private static final int[] SLOTS_DOWN = new int[] { 2, 3 };
	private static final int[] SLOTS_HORIZONTAL = new int[] { 2 };
	private int workTime;
	private int recipesUsed;
	private int mergingTime;
	private int mergingTimeTotal;
	private final IIntArray forgeData = new IIntArray() {
		public int get(int index) {
			switch (index) {
			case 0:
				return MagicalForgeTileEntity.this.workTime;
			case 1:
				return MagicalForgeTileEntity.this.recipesUsed;
			case 2:
				return MagicalForgeTileEntity.this.mergingTime;
			case 3:
				return MagicalForgeTileEntity.this.mergingTimeTotal;
			default:
				return 0;
			}
		}

		public void set(int index, int value) {
			switch (index) {
			case 0:
				MagicalForgeTileEntity.this.workTime = value;
				break;
			case 1:
				MagicalForgeTileEntity.this.recipesUsed = value;
				break;
			case 2:
				MagicalForgeTileEntity.this.mergingTime = value;
				break;
			case 3:
				MagicalForgeTileEntity.this.mergingTimeTotal = value;
			}

		}

		public int size() {
			return 4;
		}
	};
	private final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap<>();
	private final IRecipeType<? extends IRecipe<IInventory>> recipeType = RecipeSerializerInit.MAGICAL_FORGE_TYPE;

	public MagicalForgeTileEntity() {
		super(TileEntityInit.MAGICAL_FORGE_TILEENTITY.get());
	}
	
	public List<IRecipe<?>> getIRecipeList(World world, Vector3d vec3) {
		List<IRecipe<?>> list = Lists.newArrayList();

		for (Entry<ResourceLocation> entry : this.recipes.object2IntEntrySet()) {
			world.getRecipeManager().getRecipe(entry.getKey()).ifPresent((recipe) -> {
				list.add(recipe);
				spawnXpOrb(world, vec3, entry.getIntValue(),
						((MagicalForgeRecipe) recipe).getExperience());
			});
		}

		return list;
	}

	private static void spawnXpOrb(World world, Vector3d vec3, int entryValue, float experienceValue) {
		int i = MathHelper.floor((float) entryValue * experienceValue);
		float f = MathHelper.frac((float) entryValue * experienceValue);
		if (f != 0.0F && Math.random() < (double) f) {
			++i;
		}

		while (i > 0) {
			int j = ExperienceOrbEntity.getXPSplit(i);
			i -= j;
			world.addEntity(new ExperienceOrbEntity(world, vec3.x, vec3.y, vec3.z, j));
		}

	}
	
	public static int getBurnTimes(ItemStack stack) {
		return ForgeHooks.getBurnTime(stack);
	}
	
	public static boolean isFuel(ItemStack stack) {
		return ForgeHooks.getBurnTime(stack) > 0;
	}
	
	@Deprecated
	public static Map<Item, Integer> getBurnTimes() {
		return AbstractFurnaceTileEntity.getBurnTimes();
	}
	
	private boolean isWorking() {
		return this.workTime > 0;
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt) {	
		super.read(state, nbt);
		
		this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbt, this.items);
		this.workTime = nbt.getInt("workTime");
		this.mergingTime = nbt.getInt("mergingTime");
		this.mergingTimeTotal = nbt.getInt("mergingTimeTotal");
		this.recipesUsed = getBurnTimes(this.items.get(2));
		CompoundNBT cmp = nbt.getCompound("RecipesUsed");
		
		for (String s : cmp.keySet()) {
			this.recipes.put(new ResourceLocation(s), cmp.getInt(s));
		}
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		CompoundNBT ret = super.write(compound);
		
		compound.putInt("workTime", this.workTime);
		compound.putInt("mergingTime", this.mergingTime);
		compound.putInt("mergingTimeTotal", this.mergingTimeTotal);
		ItemStackHelper.saveAllItems(compound, this.items);
		CompoundNBT compoundnbt = new CompoundNBT();
		this.recipes.forEach((s, i) -> {
			compoundnbt.putInt(s.toString(), i);
		});
		compound.put("RecipesUsed", compoundnbt);
		return ret;
	}
	
	@Override
	public void tick() { 		
		boolean isBurning = this.isWorking();
		boolean flag = false;
		if (this.isWorking()) {
			--this.workTime;
		}		

		
		if (!this.world.isRemote()) {
			
			//ServerWorld serverWorld = (ServerWorld)this.getWorld();
			
			ItemStack fuelStack = this.items.get(2);
			if (this.isWorking() || !fuelStack.isEmpty() && !this.items.get(0).isEmpty()) {
				
				IRecipe<? extends IInventory> irecipe = (IRecipe<? extends IInventory>) this.world.getRecipeManager().getRecipe((IRecipeType<MagicalForgeRecipe>)this.recipeType, this, this.world).orElse(null); 
				if (!this.isWorking() && this.canMerg(irecipe)) {
					this.workTime = MagicalForgeTileEntity.getBurnTimes(fuelStack);
					this.recipesUsed = this.workTime;
					
					if (this.isWorking()) {
						
						flag = true;
						if (fuelStack.hasContainerItem()) {
							this.items.set(2, fuelStack.getContainerItem());
						} else if (!fuelStack.isEmpty()) {
							fuelStack.shrink(1);
							if (fuelStack.isEmpty()) {
								this.items.set(2, fuelStack.getContainerItem());
							}
						}
					}
				}
				
				if (this.isWorking() && this.canMerg(irecipe)) {
					this.mergingTime++;
					if (this.mergingTime == this.mergingTimeTotal) {
						this.mergingTime = 0;
						this.mergingTimeTotal = this.getMergingTime();
						this.merg(irecipe);
						flag = true;
					}
				} else {
					this.mergingTime = 0;
				}
			} else if (!this.isWorking() && this.mergingTime > 0) {
				this.mergingTime = MathHelper.clamp(this.mergingTime - 2, 0, this.mergingTimeTotal);
			}
			
			if (isBurning != this.isWorking()) {
				flag = true;
				this.world.setBlockState(pos, this.world.getBlockState(this.pos).with(MagicalForgeBlock.ON, Boolean.valueOf(this.isWorking())), 3);
			}
		}
		
		if (flag) {
			this.markDirty();
		}
	}
	
	private void merg(@Nullable IRecipe<?> recipeIn) {
		if (recipeIn != null && this.canMerg(recipeIn)) {
			ItemStack magicStack = this.items.get(0);
			ItemStack ingotStack = this.items.get(1);
			ItemStack resultStack = recipeIn.getRecipeOutput();
			ItemStack outputStack = this.items.get(3);
			
			if (outputStack.isEmpty()) {
				this.items.set(3, resultStack.copy());
			} else if (outputStack.getItem() == resultStack.getItem()) {
				outputStack.grow(resultStack.getCount());
			}
			
			if (!this.world.isRemote()) {
				this.setRecipeUsed(recipeIn);
			}
			
			magicStack.shrink(1);
			ingotStack.shrink(1);
		}
	}
	
	private boolean canMerg(@Nullable IRecipe<?> recipeIn) {
		if (!this.items.get(0).isEmpty() && !this.items.get(1).isEmpty() && recipeIn != null) {
			ItemStack stack = recipeIn.getRecipeOutput();
			if (stack.isEmpty()) {
				return false;
			} else {
				ItemStack resultStack = this.items.get(3);
				if (resultStack.isEmpty()) {
					return true;
				} else if (!resultStack.isItemEqual(stack)) {				
					return false;
				} else if (resultStack.getCount() + stack.getCount() <= this.getInventoryStackLimit() && resultStack.getCount() + stack.getCount() <= resultStack.getMaxStackSize()) {
					return true;
				} else {
					return resultStack.getCount() + stack.getCount() <= stack.getMaxStackSize();
				}
			}
		} else {
			return false;
		}
	}

	@Override
	public int getSizeInventory() {
		return this.items.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : this.items) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.items.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.items, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.items, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack idxStack = this.items.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(idxStack) && ItemStack.areItemStackTagsEqual(stack, idxStack);
		
		this.items.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
		
		if (index == 0 && !flag) {
			this.mergingTimeTotal = this.getMergingTime();
			this.mergingTime = 0;
			this.markDirty();
		}
	}
	
	private int getMergingTime() {
		return this.world.getRecipeManager().getRecipe((IRecipeType<MagicalForgeRecipe>)this.recipeType, this, this.world).map(MagicalForgeRecipe::getMergingTime).orElse(400);
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return player.getDistanceSq((double)this.pos.getX()	+ 0.5d, (double)this.pos.getY() + 0.5d, (double)this.pos.getZ() + 0.5d) <= 64.0d;
		}
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 3) {
			return false;
		} else if (index != 2) {
			return true;
		} else {
			ItemStack itemstack = this.items.get(2);
			return isFuel(stack) || stack.getItem() == Items.BUCKET && itemstack.getItem() != Items.BUCKET;
		}
	}
	
	@Override
	public void clear() {
		this.items.clear();
	}

	@Override
	public void fillStackedContents(RecipeItemHelper helper) {
		for (ItemStack is : this.items) {
			helper.accountStack(is);
		}
	}

	@Override
	public void setRecipeUsed(IRecipe<?> recipe) {
		if (recipe != null) {
			ResourceLocation rl = recipe.getId();
			this.recipes.addTo(rl, 1);
		}
	}
	
	@Nullable
	@Override
	public IRecipe<?> getRecipeUsed() {
		return null;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return Lib.TextComponents.DEFAULT_NAME_MAGICAL_FORGE;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new MagicalForgeContainer(id, player, this, this.forgeData);
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.DOWN) {
			return SLOTS_DOWN;
		} else {
			return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
		}
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		if (direction == Direction.DOWN && index == 1) {
			Item item = stack.getItem();
			if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
				return false;
			}
		}

		return true;
	}
}
