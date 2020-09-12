package ch.darklions888.SpellStorm.objects.items.pages;

import java.util.List;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.lib.MagicSource;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class MagicalInkItem extends Item {
	//public static final Map<CompoundNBT, AbstractPageItem> PAGE_MAP = new HashMap<>();
	private final MagicSource magicSource;
	
	public MagicalInkItem(MagicSource sourceIn, Properties properties) {
		super(properties);
		magicSource = sourceIn;
	}

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
			return new TranslationTextComponent(this.getTranslationKey()).func_240699_a_(TextFormatting.BLACK).func_240699_a_(TextFormatting.OBFUSCATED);
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
			tooltip.add(stc.func_240699_a_(TextFormatting.OBFUSCATED));
		} else {
			tooltip.add(stc);
		}
	}
/*
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		
		if (this.isInGroup(group)) {
			for (Item item : ForgeRegistries.ITEMS) {
				if (item instanceof AbstractPageItem) {
					items.add(addInkToItemStack(new ItemStack(this), (AbstractPageItem)item));
				}
			}
		}
	}

	public static int getColor(ItemStack stackIn) {
		if (stackIn != null) {
			if (PAGE_MAP.containsKey(stackIn.getTag())) {
				
				AbstractPageItem pageItem = PAGE_MAP.get(stackIn.getTag());				
				return pageItem.getInkColor();
			}
		}
		
		
		return 0xFF0000;	// return on default a white color in hex
	}

	private static ItemStack addInkToItemStack(ItemStack itemIn, AbstractPageItem pageIn) {
		ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(pageIn);
		itemIn.getOrCreateTag().putString("ink", resourceLocation.toString());
		CompoundNBT nbt = itemIn.getTag();
		PAGE_MAP.put(nbt, pageIn);
		
		return itemIn;
	}
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		TranslationTextComponent text = new TranslationTextComponent(this.getTranslationKey(stack));
		TranslationTextComponent text1 = null;
		if (PAGE_MAP.containsKey(stack.getTag())) {
			AbstractPageItem pageItem = PAGE_MAP.get(stack.getTag());
			
			text1 = new TranslationTextComponent(pageItem.getTranslationKey());
		}
		
		return text1 != null ? new StringTextComponent(text.getString() + "[" + "]") : text;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return PAGE_MAP.containsKey(stack.getTag());
	}
	*/
}
