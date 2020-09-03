package ch.darklions888.SpellStorm.objects.itemgroup;

import ch.darklions888.SpellStorm.init.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpellStormItemGroup extends ItemGroup {

	public static SpellStormItemGroup INSTANCE = new SpellStormItemGroup(ItemGroup.GROUPS.length, "spellstormtab");

	public SpellStormItemGroup(int index, String label) {
		super(index, label);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public ItemStack createIcon() {
		return new ItemStack(ItemInit.CRYSTAL.get());
	}

}
