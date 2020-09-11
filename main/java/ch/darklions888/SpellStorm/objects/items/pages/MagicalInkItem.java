package ch.darklions888.SpellStorm.objects.items.pages;

import java.awt.Color;
import java.awt.Transparency;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class MagicalInkItem extends Item {
	public static final Map<CompoundNBT, AbstractPageItem> PAGE_MAP = new HashMap<>();

	public MagicalInkItem(Properties properties) {
		super(properties);
	}

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
}
