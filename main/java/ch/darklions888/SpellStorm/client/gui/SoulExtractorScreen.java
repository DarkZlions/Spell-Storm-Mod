package ch.darklions888.SpellStorm.client.gui;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.containers.SoulExtractorContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SoulExtractorScreen <T extends SoulExtractorContainer>extends BaseScreen<T> {

	public SoulExtractorScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn, 165, 175, Lib.ResourceLocations.SOUL_EXTRACTOR_SCREEN_BACKGROUND_TEXTURE);
	}
}
