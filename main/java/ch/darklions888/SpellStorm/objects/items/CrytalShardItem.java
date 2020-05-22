package ch.darklions888.SpellStorm.objects.items;

import javax.annotation.Nullable;

import net.minecraft.util.text.TextFormatting;

public class CrytalShardItem extends BaseItem
{

	public CrytalShardItem(int mana,
			@Nullable String displayName,
			@Nullable TextFormatting format,
			@Nullable boolean hasEffect,
			Properties properties) 
	{
		super(mana, displayName, format, hasEffect, properties);
	}
}
