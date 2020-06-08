package ch.darklions888.SpellStorm.network;

import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketSendKey {

	public void fromBytes(ByteBuf buf) {
	}
	
	public void toBytes(ByteBuf buf) {
	}
	
	public PacketSendKey() {
		
	}
	
	public static void handle(Supplier<NetworkEvent.Context> ctx) {
	    ctx.get().enqueueWork(() -> {
	        // Work that needs to be threadsafe (most work)
	        ServerPlayerEntity sender = ctx.get().getSender(); // the client that sent this packet
	        // do stuff
	        sender.remove();
	    });
	    ctx.get().setPacketHandled(true);
	}
	
}
