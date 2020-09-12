package ch.darklions888.SpellStorm.network;

import java.util.function.Supplier;

import ch.darklions888.SpellStorm.objects.items.BookOfSpellsItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PacketRotateBookSlot implements IPacket {
	private final boolean forward;

	public PacketRotateBookSlot(boolean forward) {
		this.forward = forward;
	}

	public PacketRotateBookSlot(PacketBuffer buf) {
		this.forward = buf.readBoolean();
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeBoolean(this.forward);
	}

	@Override
	public void handle(Supplier<Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
			
			if (this.forward) {
				BookOfSpellsItem.nextSlot(stack);
			} else {
				BookOfSpellsItem.previousSlot(stack);
			}
		});

		ctx.get().setPacketHandled(true);
	}
}
