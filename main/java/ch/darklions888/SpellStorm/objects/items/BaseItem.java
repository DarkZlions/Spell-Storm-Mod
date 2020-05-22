package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import javax.annotation.Nullable;

import ch.darklions888.SpellStorm.interfaces.ManaAmount;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class BaseItem extends Item implements ManaAmount
{
	protected boolean effect = false;
	protected String displayName = null;
	protected final int mana;
	protected TextFormatting format;
	
	public BaseItem(
			int mana,
			@Nullable String displayName, 
			@Nullable TextFormatting format,
			@Nullable boolean hasEffect, 
			Item.Properties properties)
	{
		super(properties);
		this.effect = hasEffect;
		this.displayName = displayName;
		this.mana = mana;
		this.format = format;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) 
	{
		return effect;
	}
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack) 
	{
		if(displayName == null)
		{
			return new TranslationTextComponent(this.getTranslationKey(stack));
		}
		else
		{
			return new StringTextComponent( format + displayName);
		}
	}

	@Override
	public int Mana() 
	{
		return mana;
	}
	

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) 
	{
		tooltip.add(new StringTextComponent("\u00A7l" + "\u00A7d" + String.valueOf(mana) + "\u00A7r" +  " Mana power"));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
