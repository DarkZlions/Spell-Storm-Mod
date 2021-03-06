package ch.darklions888.SpellStorm.objects.items.spells;

import ch.darklions888.SpellStorm.lib.Lib.Tags;
import ch.darklions888.SpellStorm.lib.config.ConfigHandler;
import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.registries.ItemInit;
import ch.darklions888.SpellStorm.util.helpers.mathhelpers.RayTraceHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndGatewayBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PageOfMining extends AbstractPageItem {

	private double miningRange = 10.0d;
	
	public PageOfMining(Properties properties) {
		super(ConfigHandler.COMMON.pageOfMining_maxMana.get(), MagicSource.NEUTRALMAGIC, ConfigHandler.COMMON.pageOfMining_manaConsumption.get(), TextFormatting.DARK_GRAY, true, properties);
		this.miningRange = ConfigHandler.COMMON.pageOfMining_miningRange.get();
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		return getAbilities(worldIn, playerIn, handIn, playerIn.getHeldItem(handIn), null);
	}
	
	@Override	
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stackIn, ItemStack bookIn) {
		
		if(worldIn.isRemote) {
			return ActionResult.resultPass(stackIn);
		} else {
			
			if(playerIn.isCreative() || this.getManaValue(stackIn, this.defaultManaSource.getId()) >= this.manaConsumption) {
				
				ServerWorld serverWorld = (ServerWorld) worldIn;
				
				BlockRayTraceResult  result = (BlockRayTraceResult)RayTraceHelper.CustomrayTrace(worldIn, playerIn, FluidMode.NONE, this.miningRange);
				
				if (result != null && result.getType() == RayTraceResult.Type.BLOCK) {
					BlockPos pos = result.getPos();
					BlockState state = serverWorld.getBlockState(pos);
					
					if (state.getBlock() instanceof EndGatewayBlock) {
						boolean removed = serverWorld.removeBlock(pos, false);
						
						if (removed) {
							serverWorld.addEntity(new ItemEntity(serverWorld, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemInit.END_GATEWAY_FRAGMENT.get())));
						}
					}
					
					if (state.getBlockHardness(serverWorld, pos)  >= 0) {
						FluidState ifluidstate = serverWorld.getFluidState(pos);
					
						if (!(state.getBlock().getTags().contains(Tags.DUPE_GLITCH_BLOCKS.getName())))
							Block.spawnDrops(state, worldIn, pos, null, playerIn, stackIn);
							
						state.getBlock().onBlockHarvested(worldIn, pos, state, playerIn);
						serverWorld.setBlockState(pos, ifluidstate.getBlockState(), 3);
						
						serverWorld.spawnParticle(ParticleTypes.DRAGON_BREATH, pos.getX(), pos.getY(), pos.getZ(), 3, 0, 0, 0, 1f);
									
						if (!playerIn.isCreative())
							this.consumMana(stackIn, defaultManaSource);
						
						return ActionResult.resultSuccess(stackIn);
					}
				}
			}
		}
		
		return ActionResult.resultPass(stackIn);
	}

	@Override
	public int getInkColor() {
		return 0xc1c1c1;
	}
	
}
