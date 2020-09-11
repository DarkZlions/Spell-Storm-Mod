package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.objects.containers.BaseInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AbstractContainerItem extends Item {
	protected final int inventorySize;

	public AbstractContainerItem(Properties properties, int inventorySize) {
		super(properties);
		this.inventorySize = inventorySize;
	}

	protected void openGui(PlayerEntity player, EquipmentSlotType slot) {
		ItemStack stack = player.getItemStackFromSlot(slot);
		NetworkHooks.openGui((ServerPlayerEntity) player, getContainer(stack),
				buffer -> buffer.writeInt(slot.ordinal()));
	}

	protected BaseInventory getInventory(ItemStack stackIn) {
		BaseInventory inv = new BaseInventory(stackIn, getSizeInventory());
		return inv;
	}

	public int getSizeInventory() {
		return this.inventorySize;
	}

	protected abstract INamedContainerProvider getContainer(ItemStack stackIn);
}
