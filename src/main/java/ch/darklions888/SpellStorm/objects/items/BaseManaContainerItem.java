package ch.darklions888.SpellStorm.objects.items;

import java.util.ArrayList;
import java.util.List;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.util.helpers.ItemNBTHelper;
import ch.darklions888.SpellStorm.util.helpers.formatting.FormattingHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class BaseManaContainerItem extends Item implements IStoreMana {
	protected List<MagicSource> sources;
	protected int manaContainerType;

	public BaseManaContainerItem(MagicSource[] sources, int size, Properties properties) {
		super(properties);

		this.sources = new ArrayList<>();
		for (MagicSource m : sources) {
			this.sources.add(m);
		}
		this.manaContainerType = size;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

		tooltip.add(Lib.TextComponents.DESC_MANA_CONTAINER_ITEM);

		for (MagicSource source : sources) {
			tooltip.add(new StringTextComponent(FormattingHelper.GetSourceColor(source)
					+ FormattingHelper.GetFontFormat(source) + source.getSourceName().getString() + "\u00A7r" + " Mana: "
					+ String.valueOf(getManaValue(stack, source.getId()) + "/" + String.valueOf(getMaxMana(stack)))));
		}

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public int getManaValue(ItemStack stack, String key) {
		return ItemNBTHelper.getInt(stack, key, 0);
	}

	@Override
	public void setManaValue(ItemStack stack, String key, int manaAmount) {
		ItemNBTHelper.setInt(stack, key, manaAmount);
	}

	@Override
	public void addManaValue(ItemStack stack, String key, int manaAmount) {
		setManaValue(stack, key, Math.min(getManaValue(stack, key) + manaAmount, manaContainerType));
	}

	@Override
	public boolean hasMagicSource(ItemStack stackIn, MagicSource sourceIn) {
		for (MagicSource source : sources) {
			if (source == sourceIn) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int getMaxMana(ItemStack stackIn) {
		return this.manaContainerType;
	}

	@Override
	public List<MagicSource> getMagicSourceList(ItemStack stackIn) {
		return this.sources;
	}
}
