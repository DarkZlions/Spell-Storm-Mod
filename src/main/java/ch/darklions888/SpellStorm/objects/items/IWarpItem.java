package ch.darklions888.SpellStorm.objects.items;

import org.apache.logging.log4j.LogManager;

import ch.darklions888.SpellStorm.util.helpers.ItemNBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWarpItem {
	static final String[] T_KEYS = {
			"tele_dest_x",
			"tele_dest_y",
			"tele_dest_z",
	};
	
	public static void setTeleportDestination(ItemStack stackIn, BlockPos pos, World dimension) {
		ItemNBTHelper.setInt(stackIn, T_KEYS[0], pos.getX());
		ItemNBTHelper.setInt(stackIn, T_KEYS[1], pos.getY());
		ItemNBTHelper.setInt(stackIn, T_KEYS[2], pos.getZ());
		String dimensionKey = dimension.getDimensionKey().toString();

	}
	
	public static BlockPos getTeleportDestination(ItemStack stackIn) {
		int x = ItemNBTHelper.getInt(stackIn, T_KEYS[0], 0);
		int y = ItemNBTHelper.getInt(stackIn, T_KEYS[1], 999);
		int z = ItemNBTHelper.getInt(stackIn, T_KEYS[2], 0);
		
		
		return x == 0 && y == 999 && z == 0 ? null : new BlockPos(x, y, z);
	}
}
