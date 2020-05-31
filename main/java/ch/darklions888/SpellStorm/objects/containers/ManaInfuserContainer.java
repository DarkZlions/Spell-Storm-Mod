package ch.darklions888.SpellStorm.objects.containers;

import ch.darklions888.SpellStorm.init.BlockInit;
import ch.darklions888.SpellStorm.init.ContainerTypesInit;
import ch.darklions888.SpellStorm.interfaces.IMagicalContainer;
import ch.darklions888.SpellStorm.interfaces.IMagicalItem;
import ch.darklions888.SpellStorm.interfaces.IMagicalPageItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;

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
	private int itemCost; //How much item it need to fully charge the page
	private int manaCost; //How much mana i need to take away from the container-item
	
	public ManaInfuserContainer(ContainerType<?> type, int id, PlayerInventory inventoryIn, final IWorldPosCallable worldPosCallableIn)
	{
		super(type, id);
		
		this.worldPosCallable = worldPosCallableIn;
		this.addSlot(new Slot(this.inputSlots, 0, 58, 51)
		{
			public boolean isItemValid(ItemStack stack)
			{
				if(stack.getItem() instanceof IMagicalPageItem || stack.getItem() instanceof IMagicalContainer)
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
				if(stack.getItem() instanceof IMagicalItem || stack.getItem() instanceof IMagicalContainer)
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
					if(input.getItem() instanceof IMagicalItem)
					{
						int slotCount = input.getCount();
						
						input.setCount(slotCount - itemCost);
						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(1, input);
						
						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);
					}
					else if(input.getItem() instanceof IMagicalContainer)
					{
						IMagicalContainer container = (IMagicalContainer) input.getItem();
						IMagicalPageItem page = (IMagicalPageItem) ManaInfuserContainer.this.inputSlots.getStackInSlot(0).getItem();
						
						container.addManaValue(input, page.magicSource().sourceId, -manaCost);
						
						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(1, input);
						
						ManaInfuserContainer.this.inputSlots.setInventorySlotContents(0, ItemStack.EMPTY);
					}
				}
				
				itemCost = 0;
				manaCost = 0;
				System.out.println(manaCost);
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
		try {
		ItemStack itemstack1 = this.inputSlots.getStackInSlot(0);
		ItemStack itemstack2 = this.inputSlots.getStackInSlot(1);
		
		if(itemstack1.getItem() instanceof IMagicalPageItem && itemstack2.getItem() instanceof IMagicalItem)
		{

			ItemStack stack1 = itemstack1.copy();
			
			IMagicalPageItem page = (IMagicalPageItem) itemstack1.getItem();
			IMagicalItem base = (IMagicalItem) itemstack2.getItem();
			
			if(page.canReceiveManaFromtItem(itemstack1, itemstack2))
			{
				int manaIn = page.getMana(itemstack1);
				int containerSize = page.getMaxContainerSize(itemstack1);
				int fillSize = containerSize - manaIn;
				int cost = fillSize / base.manaPower().mana;
				
				if(cost <= itemstack2.getCount())
				{
					this.itemCost = cost;
					page.addMana(stack1, base.manaPower().mana * cost);	
					this.outputSlots.setInventorySlotContents(2, stack1);
					this.detectAndSendChanges();
				}
				else
				{
					this.itemCost = itemstack2.getCount();
					page.addMana(stack1, base.manaPower().mana * itemstack2.getCount());
					this.outputSlots.setInventorySlotContents(2, stack1);
					this.detectAndSendChanges();
				}
			}
		}
		else if(itemstack1.getItem() instanceof IMagicalPageItem && itemstack2.getItem() instanceof IMagicalContainer) 
		{

			ItemStack stack1 = itemstack1.copy();
			
			IMagicalPageItem page = (IMagicalPageItem) itemstack1.getItem();
			IMagicalContainer container = (IMagicalContainer) itemstack2.getItem();
			
			if(page.canReceiveManaFromtItem(itemstack1, itemstack2))
			{
				String sourceId = page.magicSource().sourceId;
				int fillSize = page.getMaxContainerSize(itemstack1) - page.getMana(itemstack1);
				
				if(container.getManaValue(itemstack2, sourceId) >= fillSize)
				{
					this.manaCost = fillSize;
					page.addMana(stack1, manaCost);
					this.outputSlots.setInventorySlotContents(2, stack1);
					this.detectAndSendChanges();
				}
				else
				{
					this.manaCost = container.getManaValue(itemstack2, sourceId);
					page.addMana(stack1, manaCost);
					this.outputSlots.setInventorySlotContents(2, stack1);
					this.detectAndSendChanges();
				}
			}
		}
		else if(itemstack1.getItem() instanceof IMagicalContainer && itemstack2.getItem() instanceof IMagicalItem)
		{
			ItemStack stack1 = itemstack1.copy();
			
			IMagicalContainer container = (IMagicalContainer) itemstack1.getItem();
			IMagicalItem item = (IMagicalItem) itemstack2.getItem();
			
			if(container.hasMagicSource(item.magicSource()))
			{
				int manaIn = container.getManaValue(itemstack1, item.magicSource().sourceId);
				int containerSize = container.getContainerSize();
				int fillSize = containerSize - manaIn;
				int cost = fillSize / item.manaPower().mana;
				
				if(cost <= itemstack2.getCount())
				{
					this.itemCost = cost;
					container.addManaValue(stack1, item.magicSource().sourceId, item.manaPower().mana * cost);	
					this.outputSlots.setInventorySlotContents(2, stack1);
					this.detectAndSendChanges();
				}
				else
				{
					this.itemCost = itemstack2.getCount();
					container.addManaValue(stack1, item.magicSource().sourceId, item.manaPower().mana * itemstack2.getCount());
					this.outputSlots.setInventorySlotContents(2, stack1);
					this.detectAndSendChanges();
				}
			}
		}
		else
		{
			this.outputSlots.setInventorySlotContents(2, ItemStack.EMPTY);
		}
		
		}
		catch(Exception e)
		{
			System.out.println(e);
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
	    return new ManaInfuserContainer((ContainerType<?>)ContainerTypesInit.MANA_INFUSER.get(), windowId, playerInventory);
	  }
}
