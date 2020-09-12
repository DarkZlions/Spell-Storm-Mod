package ch.darklions888.SpellStorm.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public interface IPacket {
	
	void encode(PacketBuffer buf);
	
	void handle(Supplier<Context> ctx);
}
