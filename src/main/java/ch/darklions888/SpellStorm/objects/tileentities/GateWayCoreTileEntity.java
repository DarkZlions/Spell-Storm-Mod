package ch.darklions888.SpellStorm.objects.tileentities;

import ch.darklions888.SpellStorm.objects.blocks.GateWayCoreBlock;
import ch.darklions888.SpellStorm.registries.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.INameable;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.world.World;

public class GateWayCoreTileEntity extends TileEntity implements ITickableTileEntity, INameable {
	private ITextComponent customName;
	private int nameColor = 0xFFFFFF;
	
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
		if (!this.hasWorld()) return;
		World world = this.getWorld();
		if (world.isRemote) return;
		
		BlockState state = this.world.getBlockState(this.pos);
		
		if (state.getBlock() instanceof GateWayCoreBlock && state.hasProperty(GateWayCoreBlock.ACTIVATED)) {
			GateWayCoreBlock block = (GateWayCoreBlock) state.getBlock();
			boolean checkStructure = block.checkStructure(this.world, this.pos);
			
			if (!checkStructure) {
				this.world.setBlockState(this.pos, state.with(GateWayCoreBlock.ACTIVATED, Boolean.FALSE));
			}
		}
	}
	
	@Override
	public void markDirty() {
		super.markDirty();
		this.world.markAndNotifyBlock(pos, this.world.getChunkAt(pos), this.world.getBlockState(pos), this.world.getBlockState(pos), 3, 512); // markDirty doesn't update the block immediately
	}

	@Override
	public ITextComponent getName() {
		
		if (this.customName == null) return this.customName;
		
		ITextComponent newName = new StringTextComponent(this.customName.getString()).mergeStyle(Style.EMPTY.setColor(Color.fromInt(this.nameColor)));
		return newName;
	}
	
	public void setName(ITextComponent nameIn) {
		this.customName = nameIn;
		this.markDirty();
	}
	
	public Color getColor() {
		return Color.fromInt(this.nameColor);
	}
	
	public void setNameColor(int nameColor) {
		this.nameColor = nameColor;
		this.markDirty();
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		CompoundNBT nbt = pkt.getNbtCompound();
		this.read(this.world.getBlockState(pkt.getPos()), nbt);
	}
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbt = new CompoundNBT();
		this.write(nbt);

		return new SUpdateTileEntityPacket(this.pos, 42, nbt);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT nbt = new CompoundNBT();
		this.write(nbt);

		return nbt;
	}

	@Override
	public void handleUpdateTag(BlockState stateIn, CompoundNBT nbt) {
		this.read(stateIn, nbt);
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		if (nbt.contains("CustomName")) {
			this.customName = ITextComponent.Serializer.getComponentFromJson(nbt.getString("CustomName"));
		}
		
		if (nbt.contains("nameColor")) {
			this.nameColor = nbt.getInt("nameColor");
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		super.write(nbt);
		if (this.customName != null) {
			nbt.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
		}
		
		nbt.putInt("nameColor", this.nameColor);
		
		return nbt;
	}
}
