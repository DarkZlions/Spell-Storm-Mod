package ch.darklions888.SpellStorm.client.gui;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.containers.ManaInfuserContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ManaInfuserScreen extends BaseScreen<ManaInfuserContainer> {

	public ManaInfuserScreen(ManaInfuserContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn, 175, 165, Lib.RegistryNames.MANA_INFUSER_SCREEN_BACKGROUND_TEXTURE);
	}
}
