package ch.darklions888.SpellStorm.objects.items;

import java.util.List;
import java.util.Random;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaPower;
import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class MagicalInkItem extends Item implements IInfusable {
	private final MagicSource magicSource;
	
	public MagicalInkItem(MagicSource sourceIn, Properties properties) {
		super(properties);
		magicSource = sourceIn;
	}

	@Override
	public MagicSource getMagicSource() {
		return this.magicSource;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		MagicSource ms = ((MagicalInkItem)stack.getItem()).getMagicSource();
		
		if (ms != null && ms == MagicSource.UNKNOWNMAGIC) {
			return new TranslationTextComponent(this.getTranslationKey()).mergeStyle(TextFormatting.BLACK).mergeStyle(TextFormatting.OBFUSCATED);
		} else {
			return super.getDisplayName(stack);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		MagicSource ms = ((MagicalInkItem)stack.getItem()).getMagicSource();
		StringTextComponent stc = new StringTextComponent(Lib.TextComponents.DESC_MAGICAL_INK.getString());
		if (ms != null && ms == MagicSource.UNKNOWNMAGIC) {
			tooltip.add(stc.mergeStyle(TextFormatting.OBFUSCATED));
		} else {
			tooltip.add(stc);
		}
	}

	@Override
	public ItemStack getOutputItemStack(ItemStack infusableStack, ItemStack hasMana) {
		if (infusableStack != null && infusableStack.getItem() instanceof MagicalInkItem && hasMana != null) {
			if (hasMana.getItem() instanceof IHasMagic) {

				MagicSource manaSource = ((IHasMagic)hasMana.getItem()).getMagicSource(hasMana);
				
				return getStackForSource(manaSource, infusableStack);
			} else if (hasMana.getItem() instanceof IStoreMana) {
				
				List<MagicSource> sourceList = ((IStoreMana)hasMana.getItem()).getMagicSourceList(hasMana);
				
				for (MagicSource ms : sourceList) {
					if (((IStoreMana)hasMana.getItem()).getManaValue(hasMana, ms.getKey()) <= 0) {
						sourceList.remove(ms);
					}
				}
				
				Random rand = new Random();
				
				return getStackForSource(sourceList.get(rand.nextInt(sourceList.size())), infusableStack);
			} else {
				return infusableStack;
			}
		} else {
			return infusableStack;
		}
	}
	
	public static ItemStack getStackForSource(MagicSource sourceIn, ItemStack stackIn) {
		return stackIn.getItem() == ItemInit.MAGICAL_INK.get() ? new ItemStack(getInkForSource(sourceIn)) : stackIn;
	}
	
	public static Item getInkForSource(MagicSource sourceIn) {
		switch (sourceIn) {
		case DARKMAGIC:
			return ItemInit.MAGICAL_INK_DARK.get();
		case LIGHTMAGIC:
			return ItemInit.MAGICAL_INK_LIGHT.get();
		case UNKNOWNMAGIC:
			return ItemInit.MAGICAL_INK_UNKNOWN.get();
		case NEUTRALMAGIC:
			return ItemInit.MAGICAL_INK_NEUTRAL.get();
		default:
			return ItemInit.MAGICAL_INK.get();
		}
	}

	@Override
	public int getInfusionCost() {
		return ManaPower.MEDIUM.mana;
	}
}
