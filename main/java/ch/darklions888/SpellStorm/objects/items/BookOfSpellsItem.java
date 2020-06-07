package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import ch.darklions888.SpellStorm.init.ContainerTypesInit;
import ch.darklions888.SpellStorm.interfaces.IMagicalPageItem;
import ch.darklions888.SpellStorm.objects.containers.BaseInventory;
import ch.darklions888.SpellStorm.objects.containers.BookOfSpellsContainer;
import ch.darklions888.SpellStorm.util.helpers.ItemNBTHelper;
import ch.darklions888.SpellStorm.util.helpers.KeyBoardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class BookOfSpellsItem extends Item {

	private static final ITextComponent text = new StringTextComponent("Book Of Spells");

	private static final String SLOT_TAG = "book_of_spells_slot_number_tag";

	public BookOfSpellsItem(Item.Properties properties) {

		super(properties);

	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

		ItemStack stack = playerIn.getHeldItem(handIn);

		if (worldIn.isRemote) {

			return ActionResult.resultPass(stack);

		} else {

			if (KeyBoardHelper.IsHoldingShift()) {

				NetworkHooks.openGui((ServerPlayerEntity) playerIn, getContainer(stack));

			} else {

				ItemStack stackFromSlot = getInventory(stack).getStackInSlot(getSelectedSlot(stack));

				if (stackFromSlot != ItemStack.EMPTY || stackFromSlot != null) {

					if (stackFromSlot.getItem() instanceof IMagicalPageItem) {
						IMagicalPageItem page = (IMagicalPageItem) stackFromSlot.getItem();

						page.getAbilities(worldIn, playerIn, handIn, stackFromSlot);
					}
				}

			}
			return ActionResult.resultSuccess(stack);

		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

		if (KeyBoardHelper.IsHoldingControl() && isSelected && !ItemNBTHelper.getBoolean(stack, "is_used", false)) {
			int index = getSelectedSlot(stack);

			index = (index++) < getSizeInventory() - 1 ? index++ : 0;

			setSelectedSlot(stack, index);

			ItemNBTHelper.setBoolean(stack, "is_used", true);
		} else {
			ItemNBTHelper.setBoolean(stack, "is_used", false);
		}
	}

	private void setSelectedSlot(ItemStack stackIn, int i) {
		ItemNBTHelper.setInt(stackIn, SLOT_TAG, i);
	}

	private int getSelectedSlot(ItemStack stackIn) {
		return ItemNBTHelper.getInt(stackIn, SLOT_TAG, 0);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		if (!KeyBoardHelper.IsHoldingShift()) {
			tooltip.add(new StringTextComponent("Hold " + "\u00A7l" + "Shift " + "\u00A7r" + "for more Information."));
			tooltip.add(new StringTextComponent("Slot: " + String.valueOf(getSelectedSlot(stack) + " is selected.")));
		} else {
			tooltip.add(new StringTextComponent("Shift & Right-click to open the book."));
			tooltip.add(new StringTextComponent(" "));
			tooltip.add(new StringTextComponent("Push Control to switch through the slots."));
		}
	}

	public BaseInventory getInventory(ItemStack stackIn) {
		BaseInventory inv = new BaseInventory(stackIn, getSizeInventory());
		return inv;
	}

	public static int getSizeInventory() {
		return 6;
	}

	private static INamedContainerProvider getContainer(ItemStack stackIn) {

		return new SimpleNamedContainerProvider((id, inventory, player) -> {
			return new BookOfSpellsContainer(ContainerTypesInit.BOOK_OF_SPELLS.get(), id, inventory,
					new BaseInventory(stackIn, 6));
		}, text);

	}
}
