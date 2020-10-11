package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.blocks.GateWayCoreBlock;
import ch.darklions888.SpellStorm.registries.EffectInit;
import ch.darklions888.SpellStorm.util.helpers.ItemNBTHelper;
import ch.darklions888.SpellStorm.util.helpers.mathhelpers.RayTraceHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;

public class CorruptedEnderEyeItem extends Item implements IWarpItem {

	public CorruptedEnderEyeItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);

		ActionResult<ItemStack> ret = ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, true);
		if (ret != null)
			return ret;

		playerIn.setActiveHand(handIn);
		return ActionResult.resultConsume(itemstack);		
	}
	
	public int getCooldownPeriod() {
		return 40;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {		
		PlayerEntity playerIn;
		if (entityLiving instanceof PlayerEntity) {
			playerIn = (PlayerEntity) entityLiving;
		} else {
			return;
		}
		
		int time = this.getUseDuration(stack) - timeLeft;
		
		if (!worldIn.isRemote() && time >= 10) {					
			BlockRayTraceResult result = (BlockRayTraceResult) RayTraceHelper.CustomrayTrace(worldIn, playerIn, FluidMode.NONE, 6d);
			BlockState gatewayState = worldIn.getBlockState(result.getPos());
			ServerWorld destWorld = worldIn.getServer().getWorld(this.getDestinationWorld(stack));
			BlockState gatewayStateDestination = null;
			if (destWorld != null) {
				BlockPos gatewayDestPos = this.getGateWayPos(stack);
				if (gatewayDestPos != null)
				gatewayStateDestination = worldIn.getServer().getWorld(this.getDestinationWorld(stack)).getBlockState(this.getGateWayPos(stack));
			}
			 
			BlockPos destinationPos = playerIn.getPosition();
			destinationPos = destinationPos.up();
			
			if (playerIn.isSneaking() && gatewayState.getBlock() instanceof GateWayCoreBlock && gatewayState.hasProperty(GateWayCoreBlock.ACTIVATED) && this.canTeleport((ServerPlayerEntity)playerIn, (ServerWorld)worldIn, destinationPos, true)) {
				if (gatewayState.get(GateWayCoreBlock.ACTIVATED)) {
					this.setTeleportDestination(stack, destinationPos, playerIn);
					this.storeGateWayPos(stack, result.getPos());
				}
			} else if (gatewayStateDestination != null) {
				if (gatewayState.getBlock() instanceof GateWayCoreBlock && gatewayState.hasProperty(GateWayCoreBlock.ACTIVATED) && gatewayStateDestination.getBlock() instanceof GateWayCoreBlock && gatewayStateDestination.hasProperty(GateWayCoreBlock.ACTIVATED)) {			
					if (gatewayState.get(GateWayCoreBlock.ACTIVATED)) {
						this.teleportPlayer(stack, (ServerPlayerEntity) playerIn);
						playerIn.getCooldownTracker().setCooldown(this, this.getCooldownPeriod());
					} else {
						playerIn.sendMessage(new StringTextComponent(Lib.TextComponents.DESC_CORRUPTED_EYE_NOT_ACTIVATED.getString()), playerIn.getUniqueID());
					}
				} else if (gatewayStateDestination.getBlock() instanceof GateWayCoreBlock && gatewayStateDestination.hasProperty(GateWayCoreBlock.ACTIVATED)) {
					if (gatewayStateDestination.get(GateWayCoreBlock.ACTIVATED)) {
						this.teleportPlayer(stack, (ServerPlayerEntity) playerIn);
						playerIn.getCooldownTracker().setCooldown(this, (int) (this.getCooldownPeriod() * 1.5));
						playerIn.addPotionEffect(new EffectInstance(EffectInit.DISINTERGRATED.get(), 600));
						
					} else {
						playerIn.sendMessage(new StringTextComponent(Lib.TextComponents.DESC_CORRUPTED_EYE_NOT_ACTIVATED.getString()), playerIn.getUniqueID());
					}
				} else if (this.isBound(stack)) {
					playerIn.sendMessage(new StringTextComponent(Lib.TextComponents.DESC_CORRUPTED_EYE_IS_DESTROYED.getString()), playerIn.getUniqueID());
				}
			} else {
				playerIn.sendMessage(new StringTextComponent(Lib.TextComponents.DESC_CORRUPTED_EYE_IS_UNBOUND.getString()), playerIn.getUniqueID());
			}			
		}
	}
	
	private void storeGateWayPos(ItemStack stackIn, BlockPos pos) {
		ItemNBTHelper.setInt(stackIn, "gate_pos_x", pos.getX());
		ItemNBTHelper.setInt(stackIn, "gate_pos_y", pos.getY());
		ItemNBTHelper.setInt(stackIn, "gate_pos_z", pos.getZ());
	}
	
	private BlockPos getGateWayPos(ItemStack stackIn) {
		int x = ItemNBTHelper.getInt(stackIn, "gate_pos_x", Integer.MIN_VALUE);
		int y = ItemNBTHelper.getInt(stackIn, "gate_pos_y", Integer.MIN_VALUE);
		int z = ItemNBTHelper.getInt(stackIn, "gate_pos_z", Integer.MIN_VALUE);
		
		return x == Integer.MIN_VALUE && y == Integer.MIN_VALUE && z == Integer.MIN_VALUE ? null : new BlockPos(x, y, z);
	}
	
	public boolean isBound(ItemStack stackIn) {
		BlockPos pos = this.getTeleportDestination(stackIn);
		RegistryKey<World> key = this.getDestinationWorld(stackIn);
		
		return pos != null && key != null;
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		BlockPos pos = this.getTeleportDestination(stack);
			
		if (this.isBound(stack)) {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			StringTextComponent text1 = new StringTextComponent(Lib.TextComponents.DESC_CORRUPTED_EYE_IS_BOUND.getString() + String.valueOf(x) + "x " + String.valueOf(y) + "y " + String.valueOf(z)+ "z");
			tooltip.add(text1);
			tooltip.add(new StringTextComponent(Lib.TextComponents.DESC_CORRUPTED_EYE_IN_DIM.getString()).append(new TranslationTextComponent(this.getDestinationWorld(stack).getLocation().getPath()).mergeStyle(TextFormatting.BOLD)));
		} else {
			tooltip.add(Lib.TextComponents.DESC_CORRUPTED_EYE_IS_UNBOUND);
		}

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return this.isBound(stack);
	}
}
