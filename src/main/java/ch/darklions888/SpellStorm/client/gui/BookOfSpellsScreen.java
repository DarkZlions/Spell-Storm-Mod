package ch.darklions888.SpellStorm.client.gui;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.containers.BookOfSpellsContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class BookOfSpellsScreen extends BaseScreen<BookOfSpellsContainer> {

	public BookOfSpellsScreen(BookOfSpellsContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn, 175, 187, Lib.RegistryNames.BOOK_OF_SPELLS_SCREEN_BACKGROUND_TEXUTRE);
	}
}
