package ch.darklions888.SpellStorm.objects.items;

import java.util.List;

import ch.darklions888.SpellStorm.init.ParticlesInit;
import ch.darklions888.SpellStorm.init.SoundInit;
import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.util.helpers.ItemNBTHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SoulCatcherItem extends Item {

	private static final String TAG = "catchedmob_soulcatcher";
	private static final String EMPTY = "empty";
	private static final String ID = "soulcathcer_id";

	public SoulCatcherItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {

		try {
			if (target instanceof MobEntity && getEntity(stack) == null) {
				World world = playerIn.getEntityWorld();

				if (!world.isRemote) {
					ServerWorld serverWorld = (ServerWorld) world;
					double x = playerIn.getPosX();
					double y = playerIn.getPosY();
					double z = playerIn.getPosZ();
					
					serverWorld.playSound(null, x, y, z, SoundInit.ETERNAL_SCREAMING.get(), SoundCategory.PLAYERS,
							0.2f, 1.0f);
					
					for (int i = 0; i < 15; i++) {
						serverWorld.spawnParticle(ParticlesInit.SOULS_PARTICLE.get(), target.getPosXRandom(.5d),
								target.getPosYRandom(), target.getPosZRandom(.5d), 2, 0.0d, .5d, 0.0d, 1.0d);
					}			
					
					setEntityId(stack, target.getEntityId());
					storeEntity(playerIn.getHeldItem(hand), target.getType());
				}


				target.remove();

				return ActionResultType.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ActionResultType.FAIL;
	}

	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		EntityType<?> entity = getEntity(stack);
		
		if (entity != null) {
			StringTextComponent text = new StringTextComponent(String.valueOf(Lib.TextComponents.SOULCATCHER_CONTAINS.getString()));

			tooltip.add(text.append(new StringTextComponent(
					": " + "\u00A7l" + getEntity(stack).getName().getString())));
		} else {
			TranslationTextComponent text = Lib.TextComponents.SOULCATCHER_IS_EMPTY;
			tooltip.add(text);
		}		
	}
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		TranslationTextComponent name = new TranslationTextComponent(this.getTranslationKey(stack));
		EntityType<?> entity = this.getEntity(stack);
		if (entity != null) {
			return new TranslationTextComponent(name.getString() + "[" + this.getEntity(stack).getName().getString() + "]");	
		} else {
			return name;
		}
	
	}

	private void storeEntity(ItemStack stack, EntityType<?> entity) {
		ItemNBTHelper.setString(stack, TAG, EntityType.getKey(entity).toString());
	}

	private void setEntityId(ItemStack stack, int id) {
		ItemNBTHelper.setInt(stack, ID, id);
	}

	public int getEntityId(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, ID, 0);
	}

	public void clearEntity(ItemStack stackIn) {
		ItemNBTHelper.setString(stackIn, TAG, EMPTY);
	}

	public EntityType<?> getEntity(ItemStack stack) {
		String key = ItemNBTHelper.getString(stack, TAG, EMPTY);

		if (!(key.equals(EMPTY))) {
			return EntityType.byKey(key).get();
		} else {
			return null;
		}
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		if (getEntity(stack) != null) {
			return true;
		} else {
			return false;
		}
	}
}
