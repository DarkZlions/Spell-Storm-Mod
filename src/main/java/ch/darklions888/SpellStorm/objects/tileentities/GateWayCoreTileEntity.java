package ch.darklions888.SpellStorm.objects.tileentities;

import ch.darklions888.SpellStorm.objects.blocks.GateWayCoreBlock;
import ch.darklions888.SpellStorm.registries.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class GateWayCoreTileEntity extends TileEntity implements ITickableTileEntity {
	

	
	public GateWayCoreTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public GateWayCoreTileEntity() {
		this(TileEntityInit.GATEWAY_CORE_TILEENTTIY.get());
	}

	public boolean isActivated() {
		BlockState blockState = this.world.getBlockState(pos);
		
		if (blockState.hasProperty(GateWayCoreBlock.ACTIVATED)) {
			boolean isActivated = blockState.get(GateWayCoreBlock.ACTIVATED).booleanValue();
			return isActivated;
		} else {
			return false;
		}
	}

	@Override
	public void tick() {
		BlockState state = this.world.getBlockState(this.pos);
		
		if (state.getBlock() instanceof GateWayCoreBlock && state.hasProperty(GateWayCoreBlock.ACTIVATED)) {
			GateWayCoreBlock block = (GateWayCoreBlock) state.getBlock();
			boolean checkStructure = block.checkStructure(this.world, this.pos);
			
			if (!checkStructure) {
				this.world.setBlockState(this.pos, state.with(GateWayCoreBlock.ACTIVATED, Boolean.FALSE));
			}
		}
	}
}
