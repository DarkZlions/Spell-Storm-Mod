package ch.darklions888.SpellStorm.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.objects.containers.BookOfSpellsContainer;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BookOfSpellsScreen extends DisplayEffectsScreen<BookOfSpellsContainer> {
	
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(SpellStormMain.MODID, "textures/gui/container/bookofspells_container.png");

	public BookOfSpellsScreen(BookOfSpellsContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.xSize = 175;
		this.ySize = 187;
		this.guiLeft = 0;
		this.guiTop = 0;
	}

	@Override
	public void render( int mouseX, int mouseY, float partialTicks) 
	{
		super.render(mouseX, mouseY, partialTicks);
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX , mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		super.drawGuiContainerForegroundLayer(0, 0);
		this.font.drawString(this.title.getFormattedText(), 3.0f, 3.0f, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		this.blit(x, y, 0, 0, this.xSize, this.ySize);
	}

}
