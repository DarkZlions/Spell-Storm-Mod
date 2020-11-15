package ch.darklions888.SpellStorm.objects.items;

import java.util.ArrayList;
import java.util.List;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.util.helpers.ItemNBTHelper;
import ch.darklions888.SpellStorm.util.helpers.formatting.FormattingHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BottledSoulsItem extends Item implements IStoreMana, IHasSoul {

	private static final String SOURCE_TAG = "source_tag_soulbottle";
	private static final String POWER_TAG = "power_tag_soulbottle";
	
	public BottledSoulsItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public void setSoulAndId(ItemStack stack, Entity entity) {
		IHasSoul.super.setSoulAndId(stack, entity);
		if (entity instanceof MobEntity) {
			ItemNBTHelper.setInt(stack, POWER_TAG, (int)((MobEntity)entity).getHealth());
			ItemNBTHelper.setString(stack, SOURCE_TAG, this.getSourceFromEntity(entity).getKey()); // Save the false source id
			this.setManaValue(stack, this.getSourceFromEntity(entity).getId(), (int)((MobEntity)entity).getHealth());
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		if (!this.isEmpty(stack)) {
			ITextComponent entityName = this.getEntityType(stack).create(worldIn).getDisplayName();
			StringTextComponent text1 = new StringTextComponent(String.valueOf(Lib.TextComponents.SOULCATCHER_MOB_HAS.getString()));
			String text2 = new String(FormattingHelper.GetSourceColor(this.getSource(stack)) + FormattingHelper.GetFontFormat(this.getSource(stack)) + this.getSource(stack).getSourceName().getString());
			String value = String.valueOf((int) Math.ceil(((MobEntity)this.getEntityType(stack).create(worldIn)).getHealth()));
			IFormattableTextComponent manaValue = new StringTextComponent(String.valueOf(this.getManaValue(stack, this.getSource(stack).getId()))).mergeStyle(TextFormatting.LIGHT_PURPLE);
			
			tooltip.add(entityName);
			tooltip.add(text1.appendString(value + " " + text2).appendString(" " + Lib.TextComponents.MANA.getString()));
			tooltip.add(new StringTextComponent("").append(manaValue).append(new StringTextComponent("/")).appendString(value).append(new StringTextComponent(Lib.TextComponents.MANA_LEFT.getString()).mergeStyle(TextFormatting.RESET)));
			
		} else {
			
		}
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		
		if (worldIn.isRemote()) {
			return;
		}
		
		if (!this.isEmpty(stack) && entityIn instanceof ServerPlayerEntity) {
			if (this.getManaValue(stack, this.getSource(stack).getId()) <= 0) {
				ServerPlayerEntity player = (ServerPlayerEntity) entityIn;
				player.replaceItemInInventory(itemSlot, new ItemStack(Items.GLASS_BOTTLE));
			}
		}
	}
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		return super.getDisplayName(stack);
	}
	
	private int getMana(ItemStack stackIn) {
		return ItemNBTHelper.getInt(stackIn, POWER_TAG, 0);
	}
	
	private MagicSource getSource(ItemStack stackIn) {
		String key = ItemNBTHelper.getString(stackIn, SOURCE_TAG, "");
		return MagicSource.getSourceByKey(key);
	}

	@Override
	public int getMaxMana(ItemStack stackIn) {
		return this.getMana(stackIn);
	}

	@Override
	public List<MagicSource> getMagicSourceList(ItemStack stackIn) {
		List<MagicSource> sourceList = new ArrayList<MagicSource>();
		MagicSource magicSource = this.getSource(stackIn);
		sourceList.add(magicSource);
		return sourceList;
	}

	@Override
	public boolean hasMagicSource(ItemStack stackIn, MagicSource sourceIn) {
		Lib.LOGGER.debug(this.getMagicSourceList(stackIn).contains(sourceIn));
		return this.getMagicSourceList(stackIn).contains(sourceIn);
	}
	
	@Override
	public boolean canChangeToEmpty(ItemStack stackIn) {
		return stackIn.getItem() == this && this.getManaValue(stackIn, this.getSource(stackIn).getId()) <= 0;
	}
	
	@Override
	public ItemStack getChangedEmptyItem(ItemStack stackIn) {
		return new ItemStack(Items.GLASS_BOTTLE);
	}
}
