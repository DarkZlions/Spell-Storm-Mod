package ch.darklions888.SpellStorm.interfaces;

import ch.darklions888.SpellStorm.enums.MagicSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public interface IMagicalPageItem {
	
	int getMana(ItemStack stackIn);

	int getMaxContainerSize(ItemStack stackIn);

	void addMana(ItemStack stackIn, int manaAmount);

	void setMana(ItemStack stackIn, int manaAmount);

	boolean canReceiveManaFromtItem(ItemStack stack1, ItemStack stack2);

	MagicSource magicSource();

	ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stackIn);
}
