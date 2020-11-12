package ch.darklions888.SpellStorm.objects.items.weapons;

import java.util.ArrayList;
import java.util.List;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaPower;
import ch.darklions888.SpellStorm.objects.items.IStoreMana;
import ch.darklions888.SpellStorm.util.helpers.formatting.FormattingHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class BasePickaxeItem extends PickaxeItem implements IStoreMana {

	protected MagicSource defaultMagicSource;
	protected List<MagicSource> magicSourceList;
	protected ManaPower manaPower;
	protected int maxMana;
	
	public BasePickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, MagicSource sourceIn, ManaPower powerIn, int maxMana, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
		this.defaultMagicSource = sourceIn;
		this.magicSourceList = new ArrayList<>();
		this.magicSourceList.add(sourceIn);
		this.manaPower = powerIn;
		this.maxMana = maxMana;
	}

	@Override
	public int getMaxMana(ItemStack stackIn) {
		return this.maxMana;
	}

	@Override
	public List<MagicSource> getMagigSourceList(ItemStack stackIn) {
		return this.magicSourceList;
	}

	@Override
	public boolean hasMagicSource(ItemStack stackIn, MagicSource sourceIn) {
		return this.magicSourceList.contains(sourceIn);
	}
	
	public void setMagicSource(MagicSource sourceIn) {
		this.defaultMagicSource = sourceIn;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		String sourceName = defaultMagicSource.getSourceName().getString();
		tooltip.add(new StringTextComponent(FormattingHelper.GetSourceColor(defaultMagicSource) + FormattingHelper.GetFontFormat(defaultMagicSource) + sourceName + "\u00A7r" + " ").append(Lib.TextComponents.MANA_SOURCE));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
