package ch.darklions888.SpellStorm.objects.items;

import java.util.Random;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.lib.ManaPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ChargedCreeperPiecesItem extends BaseItem {

	public ChargedCreeperPiecesItem(Properties properties) {
		super(MagicSource.LIGHTMAGIC, ManaPower.VERYHIGH, TextFormatting.GOLD, false, properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);		
		if (!worldIn.isRemote()) {
			Random rand = new Random();
			if (worldIn.isThundering() && rand.nextInt(1000) == 1) {

				ServerWorld serverWorld = (ServerWorld) worldIn;
				LightningBoltEntity le = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);
				le.setPosition(entityIn.getPosX(), entityIn.getPosY(), entityIn.getPosZ());
				serverWorld.addEntity(le);
			}
		}
	}
}
