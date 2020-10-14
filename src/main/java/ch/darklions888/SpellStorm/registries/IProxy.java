package ch.darklions888.SpellStorm.registries;

import net.minecraft.item.Item;

public interface IProxy {
	
	default Item.Properties propertiesWithRenderer(Item.Properties properties) {
		return properties;
	}
}
