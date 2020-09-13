package ch.darklions888.SpellStorm.objects.items.spells;

import java.util.ArrayList;
import java.util.List;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerType;
import ch.darklions888.SpellStorm.lib.ManaPower;
import ch.darklions888.SpellStorm.objects.items.IInfusable;
import ch.darklions888.SpellStorm.objects.items.IMagicalPageItem;
import ch.darklions888.SpellStorm.util.helpers.formatting.FormattingHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public abstract class AbstractPageItem extends Item implements IMagicalPageItem, IInfusable
{
	protected MagicSource defaultManaSource;
	protected final List<MagicSource> magicSourceList;
	protected final int containingManaSize;
	protected final ManaContainerType manaContainer;
	protected final int manaConsumption;
	protected final TextFormatting format;
	protected final boolean hasEffect;
	
	public AbstractPageItem(ManaContainerType size, MagicSource source, ManaPower mana, int manaConsumption, TextFormatting format, boolean hasEffect, Properties properties)
	{
		super(properties);
		this.containingManaSize = size.size;
		this.manaContainer = size;
		this.defaultManaSource = source;
		this.manaConsumption = manaConsumption;
		this.magicSourceList = new ArrayList<>();
		this.magicSourceList.add(source);
		this.format = format;
		this.hasEffect = hasEffect;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return hasEffect;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return stack.getItem() instanceof IMagicalPageItem ? 1 : super.getItemStackLimit(stack);
	}
	
	@Override
	public abstract ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stackIn, ItemStack bookIn);
	
	@Override 
	public abstract int getInkColor();
	
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) 
	{
		tooltip.add(new StringTextComponent(String.valueOf(this.getManaValue(stack, this.defaultManaSource.getId())) + "/" + this.containingManaSize + " ").append(Lib.TextComponents.MANA_LEFT));
		String sourceName = this.defaultManaSource.getSourceName().getString();
		tooltip.add(new StringTextComponent(FormattingHelper.GetSourceColor(this.defaultManaSource) + FormattingHelper.GetFontFormat(this.defaultManaSource) + sourceName + "\u00A7r" + " ").append(Lib.TextComponents.MANA_SOURCE));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack) 
	{
		if(format == null)
		{
			return new TranslationTextComponent(this.getTranslationKey(stack));
		}
		else
		{
			TranslationTextComponent translationText = new TranslationTextComponent(this.getTranslationKey(stack));
			return new TranslationTextComponent(format + translationText.getString() + "  [" + String.valueOf(this.getManaValue(stack, this.defaultManaSource.getId())) + "/" + this.containingManaSize + "]");
		}
	}
	
	@Override
	public ManaContainerType getManaContainer() {
		return this.manaContainer;
	}

	@Override
	public List<MagicSource> getMagigSourceList() {
		return this.magicSourceList;
	}

	public boolean hasMagicSource(MagicSource sourceIn) {
		return this.magicSourceList.contains(sourceIn);
	}
}
