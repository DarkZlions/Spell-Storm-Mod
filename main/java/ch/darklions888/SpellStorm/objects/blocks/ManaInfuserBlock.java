package ch.darklions888.SpellStorm.objects.blocks;

import java.util.stream.Stream;

import ch.darklions888.SpellStorm.init.ContainerTypesInit;
import ch.darklions888.SpellStorm.objects.containers.ManaInfuserContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ManaInfuserBlock extends BaseBlock
{
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	
	private static final VoxelShape SHAPE_N =  Stream.of(
			Block.makeCuboidShape(0, 0, 0, 16, 8, 16),
			Block.makeCuboidShape(3, 8, 3, 13, 11, 13),
			Block.makeCuboidShape(0, 8, 0, 1, 9, 1),
			Block.makeCuboidShape(1, 9, 1, 2, 10, 2),
			Block.makeCuboidShape(2, 10, 2, 3, 11, 3),
			Block.makeCuboidShape(0, 8, 15, 1, 9, 16),
			Block.makeCuboidShape(15, 8, 15, 16, 9, 16),
			Block.makeCuboidShape(15, 8, 0, 16, 9, 1),
			Block.makeCuboidShape(14, 9, 1, 15, 10, 2),
			Block.makeCuboidShape(1, 9, 14, 2, 10, 15),
			Block.makeCuboidShape(14, 9, 14, 15, 10, 15),
			Block.makeCuboidShape(13, 10, 2, 14, 11, 3),
			Block.makeCuboidShape(2, 10, 13, 3, 11, 14),
			Block.makeCuboidShape(13, 10, 13, 14, 11, 14),
			Block.makeCuboidShape(3, 11, 3, 4, 13, 4),
			Block.makeCuboidShape(12, 11, 3, 13, 13, 4),
			Block.makeCuboidShape(12, 11, 12, 13, 13, 13),
			Block.makeCuboidShape(3, 11, 12, 4, 13, 13)
			).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
	private static final TranslationTextComponent text = new TranslationTextComponent("container.mana_infuser");
	
	public ManaInfuserBlock(Properties properties) 
	{
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		switch(state.get(FACING))
		{
			case NORTH:
				return SHAPE_N;
			case SOUTH:
				return SHAPE_N;
			case EAST:
				return SHAPE_N;
			case WEST:
				return SHAPE_N;
			default:
				return SHAPE_N;
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rot) 
	{
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) 
	{
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(FACING);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) 
	{
		if(worldIn.isRemote)
		{
			return ActionResultType.SUCCESS;
		}
		else
		{
		    player.openContainer(state.getContainer(worldIn, pos));


			return ActionResultType.SUCCESS;
		}
	}
	
	@Override
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) 
	{
		return new SimpleNamedContainerProvider((p_220272_2_, p_220272_3_, p_220272_4_) ->
		{
			return new ManaInfuserContainer(ContainerTypesInit.MANA_INFUSER.get(), p_220272_2_, p_220272_3_, IWorldPosCallable.of(worldIn, pos));
		}, text);
	}

}
