package ch.darklions888.SpellStorm.client.gui;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.containers.SoulExtractorContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class SoulExtractorScreen extends BaseScreen<SoulExtractorContainer> {

	public SoulExtractorScreen(SoulExtractorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn, 175, 165,  Lib.RegistryNames.SOUL_EXTRACTOR_SCREEN_BACKGROUND_TEXTURE);
	}
}
