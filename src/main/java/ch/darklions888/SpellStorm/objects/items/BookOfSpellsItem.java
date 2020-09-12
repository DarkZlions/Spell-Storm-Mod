package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import ch.darklions888.SpellStorm.client.input.KeyBoardHelper;
import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.network.PacketHandler;
import ch.darklions888.SpellStorm.network.PacketRotateBookSlot;
import ch.darklions888.SpellStorm.objects.containers.BookOfSpellsContainer;
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

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
	}

	public static void nextSlot(ItemStack stackIn) {
		int slot = getSelectedSlot(stackIn);
		slot++;
		slot = (int) MathHelpers.CycleNumberLine(slot, 0, 5);
		setSelectedSlot(stackIn, slot);
	}

	public static void previousSlot(ItemStack stackIn) {
		int slot = getSelectedSlot(stackIn);
		slot--;
		slot = (int) MathHelpers.CycleNumberLine(slot, 0, 5);
		setSelectedSlot(stackIn, slot);
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
