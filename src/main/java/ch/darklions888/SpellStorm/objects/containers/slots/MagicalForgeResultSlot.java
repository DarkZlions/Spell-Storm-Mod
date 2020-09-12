package ch.darklions888.SpellStorm.objects.containers.slots;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MagicalForgeResultSlot extends Slot {
	private final PlayerEntity player;
	private int removeCount;

	public MagicalForgeResultSlot(PlayerEntity player, IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.player = player;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int amount) {
		if (this.getHasStack()) {
			this.removeCount += Math.min(amount, this.getStack().getCount());
		}

		return super.decrStackSize(amount);
	}
	
	@Override
	public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
		this.onCrafting(stack);
		super.onTake(thePlayer, stack);
		
		World world = thePlayer.getEntityWorld();
		if (!world.isRemote()) {
			ServerWorld serverWorld = (ServerWorld) world;
			
			serverWorld.playSound(null, thePlayer.getPosXRandom(0.6d), thePlayer.getPosY(), thePlayer.getPosZRandom(0.6d), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.3f, 1.0f);
		}
		
		return stack;
	}

	protected void onCrafting(ItemStack stack, int amount) {
		this.removeCount += amount;
		this.onCrafting(stack);
	}
	
	protected void onCrafting(ItemStack stack) {
		stack.onCrafting(this.player.world, this.player, this.removeCount);

		this.removeCount = 0;
	}
}
