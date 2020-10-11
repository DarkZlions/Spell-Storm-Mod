package ch.darklions888.SpellStorm.objects.items;

import java.util.function.Function;

import org.apache.logging.log4j.LogManager;

import ch.darklions888.SpellStorm.util.helpers.ItemNBTHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ITeleporter;

public interface IWarpItem {
	static final String[] T_KEYS = {
			"tele_dest_x",
			"tele_dest_y",
			"tele_dest_z",
			"tele_key_loc",
			"tele_key_loc_n",
	};
	
	default void setTeleportDestination(ItemStack stackIn, BlockPos pos, PlayerEntity playerIn) {
		ResourceLocation dimensionKeyLocation = playerIn.getEntityWorld().getDimensionKey().getLocation();
		
		ItemNBTHelper.setInt(stackIn, T_KEYS[0], pos.getX());
		ItemNBTHelper.setInt(stackIn, T_KEYS[1], pos.getY());
		ItemNBTHelper.setInt(stackIn, T_KEYS[2], pos.getZ());
		ItemNBTHelper.setString(stackIn, T_KEYS[3], dimensionKeyLocation.getPath());
		ItemNBTHelper.setString(stackIn, T_KEYS[4], dimensionKeyLocation.getNamespace());		
	}
	
	default BlockPos getTeleportDestination(ItemStack stackIn) {
		int x = ItemNBTHelper.getInt(stackIn, T_KEYS[0], Integer.MIN_VALUE);
		int y = ItemNBTHelper.getInt(stackIn, T_KEYS[1], Integer.MIN_VALUE);
		int z = ItemNBTHelper.getInt(stackIn, T_KEYS[2], Integer.MIN_VALUE);
		
		
		return x == Integer.MIN_VALUE && y == Integer.MIN_VALUE && z == Integer.MIN_VALUE ? null : new BlockPos(x, y, z);
	}
	
	default RegistryKey<World> getDestinationWorld(ItemStack stackIn) {
		String dimensionKeyLocationPath = ItemNBTHelper.getString(stackIn, T_KEYS[3], "");
		String dimensionKeyLocationNamespace = ItemNBTHelper.getString(stackIn, T_KEYS[4], "");
		
		ResourceLocation dimensionKeyLocation = new ResourceLocation(dimensionKeyLocationNamespace, dimensionKeyLocationPath);
		
		RegistryKey<World> world = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, dimensionKeyLocation);
		
		return world; 
	}
	
	default boolean teleportPlayer(ItemStack stackIn, ServerPlayerEntity playerIn) {
		return teleportPlayer(stackIn, playerIn, new ITeleporter()
	        {
				BlockPos pos = playerIn.getPosition();
	            @Override
	            public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity)
	            {
	                Entity repositionedEntity = repositionEntity.apply(false);
	                repositionedEntity.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
	                return repositionedEntity;
	            }
	        });
	}
	
	default boolean teleportPlayer(ItemStack stackIn, ServerPlayerEntity playerIn, ITeleporter teleporter) {
		BlockPos pos = getTeleportDestination(stackIn);
		RegistryKey<World> registryWorld = getDestinationWorld(stackIn);
		ServerWorld sworld = playerIn.getServer().getWorld(registryWorld);

		if (pos == null || registryWorld == null || sworld == null) {
			return false;
		}
		
		if (sworld != playerIn.getEntityWorld()) {
            playerIn.setPortal(pos);
			playerIn.changeDimension(sworld, teleporter);
		}
		
		boolean changeDim = ForgeHooks.onTravelToDimension(playerIn, registryWorld);
		LogManager.getLogger().debug(pos);
		playerIn.setPositionAndUpdate(pos.getX() + .5, pos.getY(), pos.getZ() + .5);
		if (changeDim) {
			playerIn.fallDistance = 0;
		}
		
		return changeDim;
	}
	
	@SuppressWarnings("deprecation")
	default boolean canTeleport(ServerPlayerEntity playerIn, ServerWorld destinationWorld, BlockPos destinationPosition, boolean changeState) {
		      boolean flag = false;
		      World world = destinationWorld;
		      if (world.isBlockLoaded(destinationPosition)) {
		         boolean flag1 = false;

		         while(!flag1 && destinationPosition.getY() > 0) {
		            BlockPos blockpos1 = destinationPosition.down();
		            BlockState blockstate = world.getBlockState(blockpos1);
		            if (blockstate.getMaterial().blocksMovement()) {
		               flag1 = true;
		            } else {
		               destinationPosition = blockpos1;
		            }
		         }

		         if (flag1) {
		            if (world.hasNoCollisions(playerIn) && !world.containsAnyLiquid(playerIn.getBoundingBox())) {
		               flag = true;
		            }
		         }
		      }

		      if (!flag) {
		         return false;
		      } else {

		         return true;
		      }
		   }
}