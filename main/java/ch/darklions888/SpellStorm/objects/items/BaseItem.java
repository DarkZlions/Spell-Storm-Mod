package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import javax.annotation.Nullable;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaContainerSize;
import ch.darklions888.SpellStorm.lib.ManaPower;
import ch.darklions888.SpellStorm.util.helpers.formatting.FormattingHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class BaseItem extends Item implements IMagicalItem
{
	protected boolean effect = false;
	protected ManaPower manapower;
	protected MagicSource source;
	protected TextFormatting format;
	
	public BaseItem(
			MagicSource source,
			ManaPower mana,
			@Nullable TextFormatting format,
			@Nullable boolean hasEffect, 
			Item.Properties properties)
	{
		super(properties);
		this.effect = hasEffect;
		this.manapower = mana;
		this.format = format;
		this.source = source;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) 
	{
		return effect;
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
			return new TranslationTextComponent(format + translationText.getString());
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) 
	{
		tooltip.add(new StringTextComponent("\u00A7l" + "\u00A7d" + String.valueOf(manapower.mana) + "\u00A7r" +  " Mana power"));
		tooltip.add(new StringTextComponent(FormattingHelper.GetSourceColor(source) + FormattingHelper.GetFontFormat(source) + source.sourceId + "\u00A7r" + " Magical Source"));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public MagicSource magicSource() 
	{
		return source;
	}

	@Override
	public ManaContainerSize manaContainer() 
	{
		return null;
	}

	@Override
	public ManaPower manaPower() 
	{
		return manapower;
	}
}
