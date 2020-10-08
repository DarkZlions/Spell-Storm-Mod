package ch.darklions888.SpellStorm.util.helpers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

public class ItemNBTHelper 
{
	public static void set(ItemStack stack, String key, INBT nbt)
	{
		stack.getOrCreateTag().put(key, nbt);
	}
	
	public static void setInt(ItemStack stack, String key, int i)
	{
		stack.getOrCreateTag().putInt(key, i);
	}
	
	public static void setFloat(ItemStack stack, String key, float f)
	{
		stack.getOrCreateTag().putFloat(key, f);
	}
	
	public static void setBoolean(ItemStack stack, String key, boolean b)
	{
		stack.getOrCreateTag().putBoolean(key, b);
	}
	
	public static void setString(ItemStack stack, String key, String s)
	{
		stack.getOrCreateTag().putString(key, s);
	}
	
	public static void setCompound(ItemStack stack, String key, CompoundNBT cNBT)
	{
		if(!key.equalsIgnoreCase("ench"))
		{
			stack.getOrCreateTag().put(key, cNBT);
		}
	}
	
	public static void removeEntry(ItemStack stack, String key)
	{
		stack.getOrCreateTag().remove(key);
	}
	
	public static INBT get(ItemStack stack, String key)
	{ 
		return ifExist(stack, key) ? stack.getOrCreateTag().get(key) : null;
	}
	
	public static int getInt(ItemStack stack, String key, int i)
	{
		return ifExist(stack, key) ? stack.getOrCreateTag().getInt(key) : i;
	}
	
	public static boolean getBoolean(ItemStack stack, String key, boolean b)
	{
		return ifExist(stack, key) ? stack.getOrCreateTag().getBoolean(key) : b;
	}
	
	public static String getString(ItemStack stack, String key, String s)
	{
		return ifExist(stack, key) ? stack.getOrCreateTag().getString(key) : s;
	}
	
	public static CompoundNBT getCompound(ItemStack stack, String key, boolean b)
	{
		return ifExist(stack, key) ? stack.getOrCreateTag().getCompound(key) : b ? null : new CompoundNBT();
	}
	
	public static boolean ifExist(ItemStack stack, String key)
	{
		return !stack.isEmpty() && stack.getOrCreateTag().contains(key);
	}
}
