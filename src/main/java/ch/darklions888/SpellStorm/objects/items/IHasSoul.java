package ch.darklions888.SpellStorm.objects.items;

import ch.darklions888.SpellStorm.lib.MagicSource;
import ch.darklions888.SpellStorm.util.helpers.ItemNBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;

public interface IHasSoul {
	
	static final String SOUL_TAG = "catchedmob_soulcatcher";
	static final String EMPTY = "empty";
	static final String TAG_ID = "soulcathcer_id";
	
	default void storeSoul(ItemStack stack, EntityType<?> entity) {
		ItemNBTHelper.setString(stack, SOUL_TAG, EntityType.getKey(entity).toString());
	}
	
	default void setSoulAndId(ItemStack stack, Entity entity) {
		this.storeSoul(stack, entity.getType());
		this.setEntitySoulId(stack, entity.getEntityId());
	}
	
	default void setEntitySoulId(ItemStack stack, int id) {
		ItemNBTHelper.setInt(stack, TAG_ID, id);
	}

	default int getEntitySoulId(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_ID, 0);
	}

	default void clearSoul(ItemStack stackIn) {
		ItemNBTHelper.setString(stackIn, SOUL_TAG, EMPTY);
	}

	default EntityType<?> getEntityType(ItemStack stack) {
		String key = ItemNBTHelper.getString(stack, SOUL_TAG, EMPTY);

		if (!(key.equals(EMPTY))) {
			return EntityType.byKey(key).get();
		} else {
			return null;
		}
	}
	
	default boolean isEmpty(ItemStack stackIn) {
		String key = ItemNBTHelper.getString(stackIn, SOUL_TAG, EMPTY);
		return key.equals(EMPTY);
	}
	
	default MagicSource getSourceFromEntity(Entity entityIn) {
		if (entityIn instanceof MonsterEntity && !(entityIn instanceof EndermanEntity) || entityIn instanceof SlimeEntity && !(entityIn instanceof EndermanEntity)) {
			return MagicSource.DARKMAGIC;
		} else if (entityIn instanceof AnimalEntity) {
			return MagicSource.LIGHTMAGIC;
		} else if (entityIn instanceof EndermanEntity) {
			return MagicSource.UNKNOWNMAGIC;
		} else {
			return MagicSource.NEUTRALMAGIC;
		}
	}
}
