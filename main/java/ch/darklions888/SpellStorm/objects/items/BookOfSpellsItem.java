package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.init.ContainerTypesInit;
import ch.darklions888.SpellStorm.objects.containers.BookOfSpellsContainer;
import static ch.darklions888.SpellStorm.util.helpers.ItemNBTHelper.*;
import ch.darklions888.SpellStorm.util.helpers.KeyBoardHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BookOfSpellsItem extends Item {
	private static final ITextComponent text = new StringTextComponent("Book Of Spells");
	public static final String COMPOUND_TAG = "book_of_spells_compound";

	public BookOfSpellsItem(Item.Properties properties) {

		super(properties);

	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

		ItemStack stack = playerIn.getHeldItem(handIn);

		if (getCompoundNBT(stack) == null)
			createCompoundNBT(stack);

		if (worldIn.isRemote) {

			return ActionResult.resultPass(stack);

		} else {

			if (KeyBoardHelper.IsHoldingShift()) {
				NetworkHooks.openGui((ServerPlayerEntity) playerIn, getContainer(stack));
			}
			return ActionResult.resultSuccess(stack);

		}
	}

	private void createCompoundNBT(ItemStack stack) {
		setCompound(stack, COMPOUND_TAG, new CompoundNBT());
	}

	private CompoundNBT getCompoundNBT(ItemStack stack) {
		return getCompound(stack, COMPOUND_TAG, false);
	}

	public int getSizeInventory() {
		return 6;
	}

	private void saveItems(ItemStack stack, NonNullList<ItemStack> items) {
		ItemStackHelper.saveAllItems(getCompound(stack, COMPOUND_TAG, false), getItems(stack), true);
	}

	public NonNullList<ItemStack> getItems(ItemStack stack) {

		NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);
		
		ItemStackHelper.loadAllItems(getCompound(stack, COMPOUND_TAG, false), items);

		return items;
				
	}

	private static INamedContainerProvider getContainer(ItemStack stackIn) {

		return new SimpleNamedContainerProvider((id, inventory, player) -> {
			return new BookOfSpellsContainer(ContainerTypesInit.BOOK_OF_SPELLS.get(), id, inventory, stackIn);
		}, text);

	}
}
