package ch.darklions888.SpellStorm.client.gui;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.containers.BookOfSpellsContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BookOfSpellsScreen <T extends BookOfSpellsContainer>extends BaseScreen<T> {

	public BookOfSpellsScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn, 175, 187, Lib.ResourceLocations.BOOK_OF_SPELLS_SCREEN_BACKGROUND_TEXUTRE);
	}
}
