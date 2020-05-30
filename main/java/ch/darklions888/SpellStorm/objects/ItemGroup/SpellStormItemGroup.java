package ch.darklions888.SpellStorm.objects.ItemGroup;

import ch.darklions888.SpellStorm.init.BlockInit;
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
		return new ItemStack(BlockInit.MANAINFUSER.get());
	}

}
