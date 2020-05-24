package ch.darklions888.SpellStorm.util.helpers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

public class ItemNBTHelper 
{
	public static void set(ItemStack stack, String tag, INBT nbt)
	{
		stack.getOrCreateTag().put(tag, nbt);
	}
	
	public static void setInt(ItemStack stack, String tag, int i)
	{
		stack.getOrCreateTag().putInt(tag, i);
	}
	
	public static void setFloat(ItemStack stack, String tag, float f)
	{
		stack.getOrCreateTag().putFloat(tag, f);
	}
	
	public static void setCompound(ItemStack stack, String tag, CompoundNBT cNBT)
	{
		if(!tag.equalsIgnoreCase("ench"))
		{
			stack.getOrCreateTag().put(tag, cNBT);
		}
	}
	
	public static void removeEntry(ItemStack stack, String tag)
	{
		stack.getOrCreateTag().remove(tag);
	}
	
	public static INBT get(ItemStack stack, String tag)
	{ 
		return ifExist(stack, tag) ? stack.getOrCreateTag().get(tag) : null;
	}
	
	public static int getInt(ItemStack stack, String tag, int i)
	{
		return ifExist(stack, tag) ? stack.getOrCreateTag().getInt(tag) : i;
	}
	
	public static CompoundNBT getCompound(ItemStack stack, String tag, boolean b)
	{
		return ifExist(stack, tag) ? stack.getOrCreateTag().getCompound(tag) : b ? null : new CompoundNBT();
	}
	
	public static boolean ifExist(ItemStack stack, String tag)
	{
		return !stack.isEmpty() && stack.getOrCreateTag().contains(tag);
	}
}
