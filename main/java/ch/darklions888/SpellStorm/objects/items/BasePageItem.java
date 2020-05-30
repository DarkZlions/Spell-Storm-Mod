package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaContainerSize;
import ch.darklions888.SpellStorm.enums.ManaPower;
import ch.darklions888.SpellStorm.interfaces.IMagicalItem;
import ch.darklions888.SpellStorm.interfaces.IMagicalPageItem;
import ch.darklions888.SpellStorm.util.helpers.ItemNBTHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class BasePageItem extends BaseItem implements IMagicalPageItem
{
	protected static final String MANA_TAG = "mana";
	private MagicSource source;
	protected int size = 0;
	
	public BasePageItem(ManaContainerSize size, MagicSource source, ManaPower mana, TextFormatting format, boolean hasEffect, Properties properties)
	{
		super(source, mana, format, hasEffect, properties);
		this.size = size.size;
		this.source = source;
	}

	@Override
	public int getMana(ItemStack stackIn) 
	{
		return ItemNBTHelper.getInt(stackIn, MANA_TAG, 0);
	}

	@Override
	public int getMaxContainerSize(ItemStack stackIn) 
	{
		return this.size;
	}

	@Override
	public void addMana(ItemStack stackIn, int manaAmount) 
	{
		setMana(stackIn, Math.min(getMana(stackIn) + manaAmount, size));
	}

	@Override
	public boolean canReceiveManaFromtItem(ItemStack stack1, ItemStack stack2) 
	{
		if(stack1.getItem() instanceof IMagicalPageItem && stack2.getItem() instanceof IMagicalItem)
		{
			IMagicalPageItem page = (IMagicalPageItem) stack1.getItem();
			IMagicalItem base = (IMagicalItem) stack2.getItem();
			
			return page.magicSource() == base.magicSource();
		}
		else
		{
			return false;
		}
	}

	@Override
	public void setMana(ItemStack stackIn, int manaAmount) 
	{
		ItemNBTHelper.setInt(stackIn, MANA_TAG, manaAmount);
	}

	@Override
	public MagicSource magicSource() 
	{
		return this.source;
	}
	
	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		return null;
	}
	
	
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) 
	{
		tooltip.add(new StringTextComponent(String.valueOf(this.getMana(stack)) + "/" + this.size + " Mana left"));
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
			return new TranslationTextComponent(format + translationText.getString() + "  [" + String.valueOf(this.getMana(stack)) + "/" + this.size + "]");
		}
	}
}
