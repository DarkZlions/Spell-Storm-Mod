package ch.darklions888.SpellStorm.client.gui;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.containers.ManaInfuserContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ManaInfuserScreen <T extends ManaInfuserContainer>extends BaseScreen<T> {

	public ManaInfuserScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn, 165, 175, Lib.ResourceLocations.MANA_INFUSER_SCREEN_BACKGROUND_TEXTURE);
	}
}
