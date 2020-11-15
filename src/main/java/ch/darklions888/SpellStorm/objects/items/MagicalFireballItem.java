package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.lib.ManaPower;
import ch.darklions888.SpellStorm.objects.entities.projectiles.MagicalFireballEntity;
import ch.darklions888.SpellStorm.objects.items.spells.PageOfFireballs;
import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class MagicalFireballItem extends BaseItem {

	public MagicalFireballItem(Properties properties) {
		super(((PageOfFireballs)ItemInit.PAGE_OF_FIREBALLS.get()).getMagicSourceList(null).get(0), ManaPower.MEDIUM, null, false, properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.NEUTRAL, 0.5f, 4.0f / random.nextFloat() * 4.0f + 0.8f);
		
		if (!worldIn.isRemote()) {
			double x = playerIn.getPosX();
			//double y = playerIn.getPosY();
			double z = playerIn.getPosZ();
			double accel = 0.1d;
			
			MagicalFireballEntity entity = new MagicalFireballEntity(worldIn, x + playerIn.getLookVec().x, playerIn.getPosY() + 1.2d, z + playerIn.getLookVec().z, playerIn.getLookVec().x * accel, playerIn.getLookVec().y * accel, playerIn.getLookVec().z * accel);
			worldIn.addEntity(entity);
			
			playerIn.getCooldownTracker().setCooldown(stack.getItem(), 10);
		}
		
		if (!playerIn.isCreative()) {
			stack.shrink(1);
		}
		
		return ActionResult.func_233538_a_(stack, worldIn.isRemote());
	}
}
