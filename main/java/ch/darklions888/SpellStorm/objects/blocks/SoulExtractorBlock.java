package ch.darklions888.SpellStorm.objects.blocks;

import java.util.Random;
import java.util.stream.Stream;

import ch.darklions888.SpellStorm.init.ContainerTypesInit;
import ch.darklions888.SpellStorm.init.ParticlesInit;
import ch.darklions888.SpellStorm.init.SoundInit;
import ch.darklions888.SpellStorm.objects.containers.SoulExtractorContainer;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class SoulExtractorBlock extends BaseBlock
{
	private static final TranslationTextComponent text = new TranslationTextComponent("container.soul_extractor");
	
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	
	private static World world;
	
	private static final VoxelShape SHAPE = Stream.of(
			Block.makeCuboidShape(4, 14, 12, 12, 21, 13),
			Block.makeCuboidShape(12, 14, 12, 13, 21, 13),
			Block.makeCuboidShape(12, 14, 3, 13, 21, 4),
			Block.makeCuboidShape(3, 14, 3, 4, 21, 4),
			Block.makeCuboidShape(3, 14, 12, 4, 21, 13),
			Block.makeCuboidShape(4, 14, 3, 12, 21, 4),
			Block.makeCuboidShape(3, 14, 4, 4, 21, 12),
			Block.makeCuboidShape(12, 14, 4, 13, 21, 12),
			Block.makeCuboidShape(0, 0, 0, 16, 12, 16),
			Block.makeCuboidShape(3, 21, 3, 13, 22, 13),
			Block.makeCuboidShape(3, 13, 3, 13, 14, 13),
			Block.makeCuboidShape(1, 12, 1, 15, 13, 15)
			).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
	
	public SoulExtractorBlock(Properties properties) 
	{
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return SHAPE;
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
			NetworkHooks.openGui((ServerPlayerEntity) player, getContainer(state, worldIn, pos));
			
			world = worldIn;
			
			return ActionResultType.SUCCESS;
		}
	}
	
	//I'm sure this isn't the best solution to get the world for the container class
	public static World getWorld()
	{
		return world;
	}
	
	@Override
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) 
	{
		return new SimpleNamedContainerProvider((p_220272_2_, p_220272_3_, p_220272_4_) ->
		{
			return new SoulExtractorContainer(ContainerTypesInit.SOUL_EXTRACTOR.get(), p_220272_2_, p_220272_3_, IWorldPosCallable.of(worldIn, pos));
		}, text);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) 
	{
	      if (rand.nextInt(100) == 0) {
	          worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundInit.HAUNTED_SOULS.get(), SoundCategory.BLOCKS, 1.5F, rand.nextFloat() * 0.4F + 0.8F, false);
	       }
	      for(int i = 0; i < 2; ++i) 
	      {
	          int j = rand.nextInt(2) * 2 - 1;
	          int k = rand.nextInt(2) * 2 - 1;
	          double d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
	          double d1 = (double)((float)pos.getY() + rand.nextFloat()) + .5d;
	          double d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)k;
	          double d3 = (double)(rand.nextFloat() * (float)j);
	          double d4 = ((double)rand.nextFloat() - 0.5D) * 0.140D;
	          double d5 = (double)(rand.nextFloat() * (float)k);
	          worldIn.addParticle(ParticlesInit.SOULS_PARTICLE.get(), d0, d1, d2, d3, d4, d5);
	      }
	}
}
