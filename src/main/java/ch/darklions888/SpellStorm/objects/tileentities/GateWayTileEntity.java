package ch.darklions888.SpellStorm.objects.tileentities;

import ch.darklions888.SpellStorm.objects.blocks.GateWayBlock;
import ch.darklions888.SpellStorm.registries.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class GateWayTileEntity extends TileEntity {
	

	
	public GateWayTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public GateWayTileEntity() {
		this(TileEntityInit.GATEWAY_TILEENTITY.get());
	}

	public boolean isActivated() {
		BlockState blockState = this.world.getBlockState(pos);
		
		if (blockState.hasProperty(GateWayBlock.ACTIVATED)) {
			boolean isActivated = blockState.get(GateWayBlock.ACTIVATED).booleanValue();
			return isActivated;
		} else {
			return false;
		}
	}
}
