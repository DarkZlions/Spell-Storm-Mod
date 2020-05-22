package ch.darklions888.SpellStorm.objects.CustomItemGroup;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class SpellStormItemGroup extends ItemGroup
{
	
	public static SpellStormItemGroup INSTANCE = new SpellStormItemGroup(ItemGroup.GROUPS.length, "spellstormtab");
	
	public SpellStormItemGroup(int index, String label) 
	{
		super(index, label);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(Blocks.ENCHANTING_TABLE);
	}

}
