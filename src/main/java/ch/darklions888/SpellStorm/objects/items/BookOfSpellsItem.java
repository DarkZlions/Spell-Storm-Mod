package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import ch.darklions888.SpellStorm.client.input.KeyBoardHelper;
import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.network.PacketHandler;
import ch.darklions888.SpellStorm.network.PacketRotateBookSlot;
import ch.darklions888.SpellStorm.objects.containers.BookOfSpellsContainer;
import ch.darklions888.SpellStorm.objects.items.pages.AbstractPageItem;
import ch.darklions888.SpellStorm.registries.ContainerTypesInit;
import ch.darklions888.SpellStorm.util.helpers.ItemNBTHelper;
import ch.darklions888.SpellStorm.util.helpers.mathhelpers.MathHelpers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class BookOfSpellsItem extends AbstractContainerItem {

	private static final String SLOT_TAG = "book_of_spells_slot_number_tag";

	public BookOfSpellsItem(Item.Properties properties) {
		super(properties, 6);
		MinecraftForge.EVENT_BUS.addListener(this::onLeftClick);
	}

	private void onLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
		PacketHandler.sendToServer(new PacketRotateBookSlot(true));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

		ItemStack stack = playerIn.getHeldItem(handIn);

		if (worldIn.isRemote) {
			return ActionResult.resultPass(stack);

		} else {
			if (playerIn.isSneaking()) {
				openGui(playerIn, handIn == Hand.MAIN_HAND ? EquipmentSlotType.MAINHAND : EquipmentSlotType.OFFHAND);

			} else {
				ItemStack stackFromSlot = getInventory(stack).getStackInSlot(getSelectedSlot(stack));

				if (stackFromSlot != ItemStack.EMPTY || stackFromSlot != null) {

					if (stackFromSlot.getItem() instanceof IMagicalPageItem) {
						IMagicalPageItem page = (IMagicalPageItem) stackFromSlot.getItem();

						page.getAbilities(worldIn, playerIn, handIn, stackFromSlot, new ItemStack(this));
					}
				} else {
					return ActionResult.resultPass(stack);
				}

			}
		}
		return ActionResult.resultSuccess(stack);
	}

	public static void nextSlot(ItemStack stackIn) {
		if (stackIn.getItem() instanceof BookOfSpellsItem) {
			
			Item book = (AbstractContainerItem)stackIn.getItem();
			
			int slotCount = ((AbstractContainerItem) book).getSizeInventory();
			ItemStack[] itemsInBook = new ItemStack[slotCount];
			
			for (int i = 0; i < slotCount; i++) {
				itemsInBook[i] = ((AbstractContainerItem)book).getInventory(stackIn).getStackInSlot(i);
			}
			
			int emptycount = 0;
			for (ItemStack stack : itemsInBook) {
				if (stack.isEmpty()) {
					emptycount++;
				}
			}
			if (emptycount == slotCount) {
				return;
			}
			
			int slot = getSelectedSlot(stackIn);	
			if (itemsInBook[getSelectedSlot(stackIn)].isEmpty() && emptycount < 2) {
				for (int i = slot; i < slotCount*2; i++) {

					slot++;
					slot = (int) MathHelpers.CycleNumberLine(slot, 0, 5);

					if (!itemsInBook[slot].isEmpty() && itemsInBook[slot].getItem() instanceof AbstractPageItem) {
						setSelectedSlot(stackIn, slot);
						return;
					}
				}
			}


			for (int i = slot; i < slotCount * 2; i++) {
				slot++;
				slot = (int) MathHelpers.CycleNumberLine(slot, 0, 5);
				if (!itemsInBook[slot].isEmpty() && itemsInBook[slot].getItem() instanceof AbstractPageItem) {
					setSelectedSlot(stackIn, slot);
					return;
				}
			}
			
		}
	}

	public static void previousSlot(ItemStack stackIn) {
		int slot = getSelectedSlot(stackIn);
		slot--;
		slot = (int) MathHelpers.CycleNumberLine(slot, 0, 5);
		setSelectedSlot(stackIn, slot);
	}
	
	public ItemStack getItemStackInSlot(ItemStack stackIn, int slot) {
		return this.getInventory(stackIn).getStackInSlot(slot);
	}
	
	public ItemStack getItemStackInCurrentSlot(ItemStack stackIn) {
		return this.getInventory(stackIn).getStackInSlot(getSelectedSlot(stackIn));
	}

	public static void setSelectedSlot(ItemStack stackIn, int slot) {
		ItemNBTHelper.setInt(stackIn, SLOT_TAG, slot);
	}

	public static int getSelectedSlot(ItemStack stackIn) {
		return ItemNBTHelper.getInt(stackIn, SLOT_TAG, 0);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		if (!KeyBoardHelper.IsHoldingShift()) {
			tooltip.add(new StringTextComponent(Lib.TextComponents.DESC_HOLD.getString() + " " + "\u00A7l" + Lib.TextComponents.DESC_KEY_SHIFT.getString() + " " + "\u00A7r" + Lib.TextComponents.DESC_BOOK_OF_SPELLS_5.getString()));
			tooltip.add(new StringTextComponent(Lib.TextComponents.DESC_SLOT.getString() + ": " + String.valueOf((getSelectedSlot(stack) + 1) + " " + Lib.TextComponents.DESC_BOOK_OF_SPELLS_6.getString())));
		} else {
			ItemStack stackInSlot = getInventory(stack).getStackInSlot(getSelectedSlot(stack));
			if (stackInSlot == ItemStack.EMPTY) {
				tooltip.add(Lib.TextComponents.DESC_BOOK_OF_SPELLS_3);
			} else {
				tooltip.add(new StringTextComponent(
						getInventory(stack).getStackInSlot(getSelectedSlot(stack)).getDisplayName().getString()
								+ " " + Lib.TextComponents.DESC_BOOK_OF_SPELLS_4.getString()));
			}
			
			for (int i = 0; i < 3; i++) {
				tooltip.add(Lib.TextComponents.prefix("description_book_of_spells_" + i));
			}
		}
	}

	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		
		TranslationTextComponent translationText = new TranslationTextComponent(this.getTranslationKey(stack));
		
		if (!getInventory(stack).getStackInSlot(getSelectedSlot(stack)).isEmpty()) {
			return new TranslationTextComponent(translationText.getString() + " [" + getInventory(stack).getStackInSlot(getSelectedSlot(stack)).getDisplayName().getString() + "]");
		} else {
			return translationText;
		}
	}

	@Override
	protected INamedContainerProvider getContainer(ItemStack stackIn) {
		return new SimpleNamedContainerProvider((id, inventory, player) -> {
			return new BookOfSpellsContainer(ContainerTypesInit.BOOK_OF_SPELLS.get(), id, inventory,
					getInventory(stackIn));
		}, new TranslationTextComponent("container.book_of_spells"));
	}
}
