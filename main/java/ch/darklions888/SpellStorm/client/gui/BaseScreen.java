package ch.darklions888.SpellStorm.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class  BaseScreen <T extends Container> extends DisplayEffectsScreen<T>{

	protected final ResourceLocation BACKGROUND_TEXTURE;
	
	public BaseScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn, int xSize, int ySize, ResourceLocation textureLocation) {
		super(screenContainer, inv, titleIn);
		this.xSize = xSize;
		this.ySize = ySize;
		this.guiLeft = 0;
		this.guiTop = 0;
		this.BACKGROUND_TEXTURE = textureLocation;
	}

	@Override
	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrix);
		super.render(matrix, mouseX, mouseY, partialTicks);
		this.func_230459_a_(matrix, mouseX, mouseY);
	}

	
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrix, int mouseX, int mouseY) {
		this.font.drawString(matrix, this.title.getString(), 3.0f, 3.0f, 4210752);
	}
	 
	@SuppressWarnings("deprecation")
	@Override
	protected final void drawGuiContainerBackgroundLayer(MatrixStack matrix, float particalTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		this.blit(matrix, x, y, 0, 0, this.xSize, this.ySize);
	}
}
