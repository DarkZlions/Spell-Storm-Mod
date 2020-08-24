package ch.darklions888.SpellStorm.objects.items.pages;

import ch.darklions888.SpellStorm.enums.MagicSource;
import ch.darklions888.SpellStorm.enums.ManaContainerSize;
import ch.darklions888.SpellStorm.enums.ManaPower;
import ch.darklions888.SpellStorm.objects.items.BasePageItem;
import ch.darklions888.SpellStorm.util.helpers.mathhelpers.RayTraceHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PageOfMining extends BasePageItem {

	public PageOfMining(ManaContainerSize size, MagicSource source, ManaPower mana, TextFormatting format, boolean hasEffect, Properties properties) {
		super(size, source, mana, format, hasEffect, properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		return getAbilities(worldIn, playerIn, handIn, playerIn.getHeldItem(handIn));
	}
	
	@Override
	public ActionResult<ItemStack> getAbilities(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack stackIn) {
		
		if(worldIn.isRemote) {
			return ActionResult.resultPass(stackIn);
		}else {
			
			if(this.getMana(stackIn) > 0) {
				
				ServerWorld serverWorld = (ServerWorld) worldIn;
				
				RayTraceResult result = RayTraceHelper.CustomrayTrace(worldIn, playerIn, FluidMode.SOURCE_ONLY, 10d);

				int lx = (int)Math.floor(playerIn.getLookVec().getX());
				int ly = (int)Math.floor(playerIn.getLookVec().getY());
				int lz = (int)Math.floor(playerIn.getLookVec().getZ());
				
				BlockPos pos = new BlockPos(
						result.getHitVec().getX() + (lx),
						result.getHitVec().getY() + (ly),
						result.getHitVec().getZ() + (lz)
						);
				
				BlockState state = serverWorld.getBlockState(pos);
				
				if(state != null && !state.isAir(serverWorld, pos) && state.getBlockHardness(worldIn, pos) >= 0.0001d) {
					
					IFluidState ifluidstate = serverWorld.getFluidState(pos);
					Block.spawnDrops(state.getBlockState(), worldIn, pos);
					serverWorld.setBlockState(pos, ifluidstate.getBlockState(), 3);
					
					this.addMana(stackIn, -1);
				}
	
				return ActionResult.resultSuccess(stackIn);
			}else {
				return ActionResult.resultPass(stackIn);
			}

		}
		
	}
	
}
