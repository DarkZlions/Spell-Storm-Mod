package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.lib.MagicSource;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BookOfMana extends BaseManaContainerItem
{
	private final boolean isCreative;
	
	public BookOfMana(MagicSource[] sources, int size, Properties properties, boolean isCreative) 
	{
		super(sources, size, properties);
		
		this.isCreative = isCreative;
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		
		if (isCreative) {
			for (MagicSource m : this.sources) {
				this.setManaValue(stack, m.getId(), this.getMaxMana(stack));
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (isCreative) {
			StringTextComponent text = new StringTextComponent(Lib.TextComponents.DESC_BOOK_MANA_CREATIVE.getString());
			tooltip.add(text.mergeStyle(TextFormatting.BOLD));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return isCreative;
	}
}
