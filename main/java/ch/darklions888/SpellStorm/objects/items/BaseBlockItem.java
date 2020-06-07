package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import javax.annotation.Nullable;

import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaContainerSize;
import ch.darklions888.SpellStorm.enums.ManaPower;
import ch.darklions888.SpellStorm.interfaces.IMagicalItem;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BaseBlockItem extends BlockItem implements IMagicalItem {

	protected boolean effect = false;
	protected MagicSource source;
	protected ManaPower manapower;
	protected TextFormatting format;

	public BaseBlockItem(MagicSource source, ManaPower mana, @Nullable TextFormatting format,
			@Nullable boolean hasEffect, Block block, Item.Properties properties) {
		super(block, properties);
		this.effect = hasEffect;
		this.source = source;
		this.manapower = mana;
		this.format = format;
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return effect;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		if (format == null) {
			return new TranslationTextComponent(this.getTranslationKey(stack));
		} else {
			TranslationTextComponent translationText = new TranslationTextComponent(this.getTranslationKey(stack));
			return new TranslationTextComponent(format + translationText.getString());
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new StringTextComponent(
				"\u00A7l" + "\u00A7d" + String.valueOf(manapower.mana) + "\u00A7r" + " Mana power"));

		tooltip.add(new StringTextComponent(
				GetSourceColor(source) + GetFontFormat(source) + source.sourceId + "\u00A7r" + " Magical Source"));

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	private String GetSourceColor(MagicSource sourceColor) {
		switch (sourceColor) {
		case LIGHTMAGIC:
			return "\u00A7e";

		case DARKMAGIC:
			return "\u00A74";

		case UNKNOWNMAGIC:
			return "\u00A70";

		default:
			return "\u00A7f";
		}
	}

	private String GetFontFormat(MagicSource source) {
		switch (source) {
		case UNKNOWNMAGIC:
			return "\u00A7k";

		default:
			return "\u00A7l";
		}
	}

	@Override
	public MagicSource magicSource() {
		return source;
	}

	@Override
	public ManaContainerSize manaContainer() {
		return null;
	}

	@Override
	public ManaPower manaPower() {
		return manapower;
	}
}
