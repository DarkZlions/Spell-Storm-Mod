package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import javax.annotation.Nullable;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaPower;
import ch.darklions888.SpellStorm.util.helpers.formatting.FormattingHelper;
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

public class BaseBlockItem extends BlockItem implements IHasMagic {

	protected boolean effect = false;
	protected MagicSource source;
	protected ManaPower manapower;
	protected TextFormatting format;
	private final int burnTime;
	

	public BaseBlockItem(MagicSource source, ManaPower mana, @Nullable TextFormatting format, @Nullable boolean hasEffect, Block block, int burnTime, Item.Properties properties) {
		super(block, properties);
		this.effect = hasEffect;
		this.source = source;
		this.manapower = mana;
		this.format = format;
		this.burnTime = burnTime;
	}
	
	public BaseBlockItem(MagicSource source, ManaPower mana, @Nullable TextFormatting format, @Nullable boolean hasEffect, Block block, Item.Properties properties) {
		super(block, properties);
		this.effect = hasEffect;
		this.source = source;
		this.manapower = mana;
		this.format = format;
		this.burnTime = 0;
	}
	
	@Override
	public int getBurnTime(ItemStack itemStack) {
		return this.burnTime == 0 ? super.getBurnTime(itemStack) : this.burnTime;
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
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) 
	{
		tooltip.add(new StringTextComponent("\u00A7l" + "\u00A7d" + String.valueOf(manapower.mana) + "\u00A7r" +  " ").append(Lib.TextComponents.MANA_POWER));
		String sourceName = source.getSourceName().getString();
		tooltip.add(new StringTextComponent(FormattingHelper.GetSourceColor(source) + FormattingHelper.GetFontFormat(source) + sourceName + "\u00A7r" + " ").append(Lib.TextComponents.MANA_SOURCE));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public MagicSource getMagicSource(ItemStack stackIn) {
		return this.source;
	}

	@Override
	public ManaPower getManaPower(ItemStack stackIn) {
		return this.manapower;
	}
}
