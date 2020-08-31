package ch.darklions888.SpellStorm.network;

import ch.darklions888.SpellStorm.lib.Lib;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
	private static final String PROTOCOL = "6";
	
	public static final SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(
			Lib.ResourceLocations.NETWORK_CHANNEL,
			() -> PROTOCOL,
			PROTOCOL::equals,
			PROTOCOL::equals);
	
	public static void init() {
		int id = 0;
		HANDLER.registerMessage(id++, PacketRotateBookSlot.class, PacketRotateBookSlot::encode, PacketRotateBookSlot::new, PacketRotateBookSlot::handle);

	}
	
	// Send a message to all within 64 blocks that have this chunk loaded
	@SuppressWarnings("resource")
	public static void sendToNearby(World world, BlockPos pos, Object toSend) {
		if (world instanceof ServerWorld) {
			ServerWorld ws = (ServerWorld) world;
			
			ws.getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false)
				.filter(p -> p.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64)
				.forEach(p -> HANDLER.send(PacketDistributor.PLAYER.with(() -> p), toSend));
		}
	}
	
	public static void sendToNearby(World world, Entity entityIn, Object toSend) {
		sendToNearby(world, new BlockPos(entityIn.getPositionVec()), toSend);
	}
	
	public static void sendTo(ServerPlayerEntity sPlayer, Object toSend) {
		HANDLER.sendTo(toSend, sPlayer.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
	}
	
	public static void sendNonLocal(ServerPlayerEntity sPlayer, Object toSend) {
		if (sPlayer.server.isDedicatedServer() || !sPlayer.getGameProfile().getName().equals(sPlayer.server.getServerOwner())) {
			sendTo(sPlayer, toSend);
		}
	}
	
	public static void sendToServer(Object msg) {
		HANDLER.sendToServer(msg);
	}
	
	private PacketHandler() {
		// Empty Constructor
	}

}
