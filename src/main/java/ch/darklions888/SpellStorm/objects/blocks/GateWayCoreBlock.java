package ch.darklions888.SpellStorm.objects.blocks;

import java.util.Random;
import java.util.stream.Stream;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.tileentities.GateWayCoreTileEntity;
import ch.darklions888.SpellStorm.registries.BlockInit;
import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GateWayCoreBlock extends ContainerBlock {

	public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");
	private static final VoxelShape SHAPE = Stream.of(
			Block.makeCuboidShape(4, 0, 0, 12, 4, 4),
			Block.makeCuboidShape(4, 0, 12, 12, 4, 16),
			Block.makeCuboidShape(12, 0, 4, 16, 4, 12),
			Block.makeCuboidShape(0, 0, 4, 4, 4, 12),
			Block.makeCuboidShape(0, 12, 4, 4, 16, 12),
			Block.makeCuboidShape(12, 12, 4, 16, 16, 12),
			Block.makeCuboidShape(4, 12, 0, 12, 16, 4),
			Block.makeCuboidShape(4, 12, 12, 12, 16, 16),
			Block.makeCuboidShape(0, 4, 0, 4, 12, 4),
			Block.makeCuboidShape(0, 4, 12, 4, 12, 16),
			Block.makeCuboidShape(12, 4, 0, 16, 12, 4),
			Block.makeCuboidShape(12, 4, 12, 16, 12, 16),
			Block.makeCuboidShape(0, 0, 0, 4, 4, 4),
			Block.makeCuboidShape(0, 0, 12, 4, 4, 16),
			Block.makeCuboidShape(12, 0, 12, 16, 4, 16),
			Block.makeCuboidShape(12, 0, 0, 16, 4, 4),
			Block.makeCuboidShape(12, 12, 0, 16, 16, 4),
			Block.makeCuboidShape(0, 12, 0, 4, 16, 4),
			Block.makeCuboidShape(0, 12, 12, 4, 16, 16),
			Block.makeCuboidShape(12, 12, 12, 16, 16, 16)
			).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
	private BlockPattern gatewayPattern;
	
	public GateWayCoreBlock(Properties builder) {
		super(builder);
	    this.setDefaultState(this.stateContainer.getBaseState().with(ACTIVATED, Boolean.valueOf(false)));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(ACTIVATED);
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new GateWayCoreTileEntity();
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack stack = player.getHeldItem(handIn);
		if (!worldIn.isRemote() && stack.getItem() == ItemInit.END_GATEWAY_FRAGMENT.get() && state.hasProperty(ACTIVATED)) {
			boolean checkStructure = this.checkStructure(worldIn, pos);
			
			if (checkStructure && !state.get(ACTIVATED).booleanValue()) {
				worldIn.setBlockState(pos, worldIn.getBlockState(pos).with(GateWayCoreBlock.ACTIVATED, Boolean.valueOf(checkStructure)));
				if (!player.isCreative()) stack.shrink(1);
				return ActionResultType.SUCCESS;
			} else
				return ActionResultType.PASS;
		} else {
			return ActionResultType.PASS;
		}
	}
	
	public boolean checkStructure(World worldIn, BlockPos pos) {
		int zOffset = 1;
		BlockPattern.PatternHelper pHelper = this.getGateWayPattern().match(worldIn, new BlockPos(pos.getX(), pos.getY(), pos.getZ() - zOffset));
		boolean activated = false;

		if (pHelper != null) {
			BlockState blockState = worldIn.getBlockState(pos);
			
			if (blockState.isIn(BlockInit.GATEWAY_CORE.get()) && blockState.hasProperty(GateWayCoreBlock.ACTIVATED)) {
				activated = true;

			}
		} else {
			BlockState blockState = worldIn.getBlockState(pos);
			
			if (blockState.isIn(BlockInit.GATEWAY_CORE.get()) && blockState.hasProperty(GateWayCoreBlock.ACTIVATED)) {
				activated = false;
			}
		}
		
		return activated;
	}
	
	private BlockPattern getGateWayPattern() {
		if (this.gatewayPattern == null) {
			this.gatewayPattern = BlockPatternBuilder
					.start()
					.aisle(" # ", " # ", " S ")
					.aisle("#G#", "#P#", "WPE")
					.aisle(" # ", " # ", " N ")
					.where('#', CachedBlockInfo.hasState(BlockMatcher.forBlock(Blocks.AIR)))
					.where('S', CachedBlockInfo.hasState(Lib.BlockStatePredicates.GATEWAY_PATTERN_STAIRS_SOUTH))
					.where('E', CachedBlockInfo.hasState(Lib.BlockStatePredicates.GATEWAY_PATTERN_STAIRS_EAST))
					.where('W', CachedBlockInfo.hasState(Lib.BlockStatePredicates.GATEWAY_PATTERN_STAIRS_WEST))
					.where('N', CachedBlockInfo.hasState(Lib.BlockStatePredicates.GATEWAY_PATTERN_STAIRS_NORTH))
					.where('G', CachedBlockInfo.hasState(Lib.BlockStatePredicates.GATEWAY_PATTERN_CORE))
					.where('P', CachedBlockInfo.hasState(Lib.BlockStatePredicates.GATEWAY_PATTERN_PILLAR))
					.build();  
		}
		
		return 	this.gatewayPattern;
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof GateWayCoreTileEntity && stateIn.get(ACTIVATED) == true) {
			int i = 6;

			for (int j = 0; j < i; ++j) {
				double d0 = (double) pos.getX() + rand.nextDouble();
				double d1 = (double) pos.getY() + rand.nextDouble();
				double d2 = (double) pos.getZ() + rand.nextDouble();
				double d3 = (rand.nextDouble() - 0.5D) * 0.5D;
				double d4 = (rand.nextDouble() - 0.5D) * 0.5D;
				double d5 = (rand.nextDouble() - 0.5D) * 0.5D;
				int k = rand.nextInt(2) * 2 - 1;
				if (rand.nextBoolean()) {
					d2 = (double) pos.getZ() + 0.5D + 0.25D * (double) k;
					d5 = (double) (rand.nextFloat() * 2.0F * (float) k);
				} else {
					d0 = (double) pos.getX() + 0.5D + 0.25D * (double) k;
					d3 = (double) (rand.nextFloat() * 2.0F * (float) k);
				}

				worldIn.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
			}

		}
	}
}
