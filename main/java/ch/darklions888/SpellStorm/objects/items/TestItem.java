package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import ch.darklions888.SpellStorm.util.helpers.mathhelpers.MathHelpers;
import ch.darklions888.SpellStorm.util.helpers.mathhelpers.Vec3;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class TestItem extends Item
{

	public TestItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		int x = (int) playerIn.getPosX();
		int y = (int) playerIn.getPosY();
		int z = (int) playerIn.getPosZ();
		
		List<Vec3> coords = MathHelpers.getSphereCoordinates(16, new Vec3(x, y, z), 64);
		
		for(Vec3 coord : coords)
		{
			worldIn.setBlockState(new BlockPos(coord.X(), coord.Y(), coord.Z()), Blocks.COBBLESTONE.getDefaultState());
			playerIn.sendMessage(new StringTextComponent(new BlockPos(coord.X(), coord.Y(), coord.Z()).toString()));
		}
		
		return ActionResult.resultSuccess(stack);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		ParticleType<?> particle = ParticleTypes.ENCHANT;
		
		double x = entityIn.getPosX();
		double y = entityIn.getPosY();
		double z = entityIn.getPosZ();
		
		List<Vec3> coords = MathHelpers.getCircleCoordinates(3, new Vec3(x, y + .2, z), 16, false, false);
		List<Vec3> coords2 = MathHelpers.getCircleCoordinates(3, new Vec3(x, y + .2, z), 16, true, false);
		List<Vec3> coords3 = MathHelpers.getCircleCoordinates(3, new Vec3(x, y + .2, z), 16, true, true);
		
		for(Vec3 coord : coords)
		{
			worldIn.addParticle((IParticleData) particle, coord.X(), coord.Y(), coord.Z(), 0, 0, 0);
		}
		
		for(Vec3 coord : coords2)
		{
			worldIn.addParticle((IParticleData) particle, coord.X(), coord.Y(), coord.Z(), 0, 0, 0);
		}
		
		for(Vec3 coord : coords3)
		{
			worldIn.addParticle((IParticleData) particle, coord.X(), coord.Y(), coord.Z(), 0, 0, 0);
		}
	}

}
