package ch.darklions888.SpellStorm.objects.containers;

import java.util.Objects;

import org.apache.logging.log4j.Logger;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaPower;
import ch.darklions888.SpellStorm.init.BlockInit;
import ch.darklions888.SpellStorm.init.ContainerTypesInit;
import ch.darklions888.SpellStorm.init.ItemInit;
import ch.darklions888.SpellStorm.objects.items.BaseItem;
import ch.darklions888.SpellStorm.objects.items.BasePageItem;
import ch.darklions888.SpellStorm.objects.items.PageOfTheWithers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class ManaInfuserContainer extends Container
{
	private final IInventory outputSlots = new CraftResultInventory();
	private final IInventory inputSlots = new Inventory(2)
	{
		public void markDirty()
		{
			super.markDirty();
			ManaInfuserContainer.this.onCraftMatrixChanged(this);
		}
	};
	
	private final IWorldPosCallable worldPosCallable;
	private final IntReferenceHolder maximumCost = IntReferenceHolder.single();
	public int manaCost;
	private final PlayerEntity player;
	
	public ManaInfuserContainer(ContainerType<?> type, int id, PlayerInventory inventoryIn, final IWorldPosCallable worldPosCallableIn)
	{
		super(type, id);
		
		this.worldPosCallable = worldPosCallableIn;
		this.player = inventoryIn.player;
		
		this.addSlot(new Slot(this.inputSlots, 0, 58, 51)
		{
			public boolean isItemValid(ItemStack stack)
			{
				if(stack.getItem() instanceof BasePageItem)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		});
		
		this.addSlot(new Slot(this.inputSlots, 1, 102, 51)
		{
			public boolean isItemValid(ItemStack stack)
			{
				if(stack.getItem() instanceof BaseItem)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		});
		
		this.addSlot(new Slot(this.outputSlots, 2, 80, 10)
		{
			public boolean isItemValid(ItemStack stack)
			{
				return false;
			}
			
			public boolean canTakeStack(PlayerEntity player)
			{
				return true;
			}
			
			public ItemStack onTake(PlayerEntity playerIn, ItemStack stack)
			{

				
				ManaInfuserContainer.this.outputSlots.setInventorySlotContents(2, ItemStack.EMPTY);
				
				if(ManaInfuserContainer.this.inputSlots.getStackInSlot(1).isEmpty())
				{
					ManaInfuserContainer.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);
					ManaInfuserContainer.this.inputSlots.setInventorySlotContents(1, ItemStack.EMPTY);
				}
				else
				{
					ItemStack input = ManaInfuserContainer.this.inputSlots.getStackInSlot(1);
					int slotCount = input.getCount();
					
					input.setCount(slotCount - 1);
					ManaInfuserContainer.this.inputSlots.setInventorySlotContents(1, input);
					
					ManaInfuserContainer.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);
				}
				
				return stack;
			}
		});
		
		for(int i = 0; i < 3; i++)
		{
			for(int j= 0; j < 9; j++)
			{
				this.addSlot(new Slot(inventoryIn, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(int k = 0; k < 9; k++)
		{
			this.addSlot(new Slot(inventoryIn, k, 8 + k * 18, 142));
		}

	}
	
	public ManaInfuserContainer(ContainerType<?> containerType, int windowId, PlayerInventory playerInventory) 
	{
		this(containerType, windowId, playerInventory, IWorldPosCallable.DUMMY);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) 
	{
		if(inventoryIn == this.inputSlots)
		{
			this.updateOutPut();
		}
		super.onCraftMatrixChanged(inventoryIn);
	}

	public void updateOutPut()
	{
		ItemStack itemstack1 = this.inputSlots.getStackInSlot(0);
		ItemStack itemstack2 = this.inputSlots.getStackInSlot(1);
		
		if(itemstack1.getItem() instanceof BasePageItem && itemstack2.getItem() instanceof BaseItem)
		{

			ItemStack stack1 = itemstack1.copy();
			
			BasePageItem page = (BasePageItem) itemstack1.getItem();
			BaseItem base = (BaseItem) itemstack2.getItem();
			
			if(page.canReceiveManaFromtItem(itemstack1, itemstack2))
			{
				page.addMana(stack1, base.manaPower().mana);	
				this.outputSlots.setInventorySlotContents(2, stack1);
				this.detectAndSendChanges();
			}
		}
		else
		{
			this.outputSlots.setInventorySlotContents(2, ItemStack.EMPTY);
		}
	}
	
	@Override
   public void onContainerClosed(PlayerEntity playerIn) 
   {
	      super.onContainerClosed(playerIn);
	      this.worldPosCallable.consume((p_216973_2_, p_216973_3_) -> {
	         this.clearContainer(playerIn, p_216973_2_, this.inputSlots);
	     });
   }
   
	@Override
   public boolean canInteractWith(PlayerEntity playerIn) 
   {
	      return isWithinUsableDistance(this.worldPosCallable, playerIn, BlockInit.MANAINFUSER.get());
   }
   
   public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) 
   {
	      ItemStack itemstack = ItemStack.EMPTY;
	      Slot slot = this.inventorySlots.get(index);
	      if (slot != null && slot.getHasStack()) {
	         ItemStack itemstack1 = slot.getStack();
	         itemstack = itemstack1.copy();
	         if (index == 2) {
	            if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
	               return ItemStack.EMPTY;
	            }

	            slot.onSlotChange(itemstack1, itemstack);
	         } else if (index != 0 && index != 1) {
	            if (index >= 3 && index < 39 && !this.mergeItemStack(itemstack1, 0, 2, false)) {
	               return ItemStack.EMPTY;
	            }
	         } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
	            return ItemStack.EMPTY;
	         }

	         if (itemstack1.isEmpty()) {
	            slot.putStack(ItemStack.EMPTY);
	         } else {
	            slot.onSlotChanged();
	         }

	         if (itemstack1.getCount() == itemstack.getCount()) {
	            return ItemStack.EMPTY;
	         }

	         slot.onTake(playerIn, itemstack1);
	      }

	      return itemstack;
	   }
   
   public static ManaInfuserContainer create(int windowId, PlayerInventory playerInventory) {
	    return new ManaInfuserContainer((ContainerType)ContainerTypesInit.MANA_INFUSER.get(), windowId, playerInventory);
	  }
}
