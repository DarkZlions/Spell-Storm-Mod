package ch.darklions888.SpellStorm.client.proxy;

import ch.darklions888.SpellStorm.client.renderer.tileentity.ISTER;
import ch.darklions888.SpellStorm.registries.IProxy;
import net.minecraft.item.Item;

public class ClientProxy implements IProxy {

	@Override
	public Item.Properties propertiesWithRenderer(Item.Properties properties) {
		return properties.setISTER(() -> () -> ISTER.INSTANCE);
	}
}
