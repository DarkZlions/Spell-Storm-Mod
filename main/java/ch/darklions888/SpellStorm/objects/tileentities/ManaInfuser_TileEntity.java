package ch.darklions888.SpellStorm.objects.tileentities;

import javax.annotation.Nonnull;

import ch.darklions888.SpellStorm.init.TileEntityTypesInit;
import ch.darklions888.SpellStorm.objects.blocks.ManaInfuserBlock;
import ch.darklions888.SpellStorm.objects.containers.ManaInfuserContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public abstract class ManaInfuser_TileEntity extends LockableLootTileEntity implements ISidedInventory, ITickableTileEntity
{
	private NonNullList<ItemStack> contents = NonNullList.withSize(3, ItemStack.EMPTY);
	protected int numPlayerUsing;
	private IItemHandlerModifiable items = createHandler();
	private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);
	
	public ManaInfuser_TileEntity(TileEntityType<?> typeIn) 
	{
		super(typeIn);
	}
	/*
	public ManaInfuser_TileEntity()
	{
		this(TileEntityTypesInit.MANAINFUSER.get());
	}
	*/
	@Override
	public int getSizeInventory() 
	{
		return 3;
	}
	
	@Override
	public NonNullList<ItemStack> getItems() 
	{
		return this.contents;
	}
	
	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) 
	{
		this.contents = itemsIn;
	}
	
	@Override
	protected ITextComponent getDefaultName() 
	{
		return new TranslationTextComponent("container.manainfuser");
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) 
	{	
		if(!this.checkLootAndWrite(compound))
		{
			ItemStackHelper.saveAllItems(compound, this.contents);
		}
		return compound;
	}
	
	@Override
	public void read(CompoundNBT compound) 
	{
		super.read(compound);
		
		this.contents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		if(!this.checkLootAndRead(compound))
		{
			ItemStackHelper.loadAllItems(compound, this.contents);
		}
	}
	
	private void playSound(SoundEvent sound)
	{
		double dx = (double)this.pos.getX() + .5d;
		double dy = (double)this.pos.getY() + .5d;
		double dz = (double)this.pos.getZ() + .5d;
		
		this.world.playSound((PlayerEntity) null, dx,  dy,  dz, sound, SoundCategory.BLOCKS, .5f, this.world.rand.nextFloat() * .1f + .9f);
	}
	
	@Override
	public boolean receiveClientEvent(int id, int type) 
	{
		if(id == 1)
		{
			this.numPlayerUsing = type;
			return true;
		}
		else
		{
			return super.receiveClientEvent(id, type);
		}
	}
	
	@Override
	public void openInventory(PlayerEntity player) 
	{
		if(!player.isSpectator())
		{
			if(this.numPlayerUsing < 0)
			{
				this.numPlayerUsing = 0;
			}
			
			++this.numPlayerUsing;
			this.onOpenOrClose();
		}
	}
	
	@Override
	public void closeInventory(PlayerEntity player) 
	{
		if(!player.isSpectator())
		{
			--this.numPlayerUsing;
			this.onOpenOrClose();
		}
	}
	
	protected void onOpenOrClose()
	{
		Block block = this.getBlockState().getBlock();
		if(block instanceof ManaInfuserBlock)
		{
			this.world.addBlockEvent(this.pos, block, 1, this.numPlayerUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, block);
		}
	}
	
	public static int getPlayersUsing(IBlockReader reader, BlockPos pos)
	{
		BlockState state = reader.getBlockState(pos);
		if(state.hasTileEntity())
		{
			TileEntity tile = reader.getTileEntity(pos);
			if(tile instanceof ManaInfuser_TileEntity)
			{
				return((ManaInfuser_TileEntity)tile).numPlayerUsing;
			}
		}
		return 0;
	}
	
	@Override
	public void updateContainingBlockInfo() 
	{
		super.updateContainingBlockInfo();
		if(this.itemHandler != null)
		{
			this.itemHandler.invalidate();
			this.itemHandler = null;
		}
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nonnull Direction side) 
	{
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return itemHandler.cast();
		}
		
		return super.getCapability(cap, side);
	}
	
	private IItemHandlerModifiable createHandler()
	{
		return new InvWrapper(this);
	}
	
	@Override
	public void remove() 
	{
		super.remove();
		if(itemHandler != null)
		{
			itemHandler.invalidate();
		}
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		// TODO Auto-generated method stub
		return false;
	}
}
