package ch.darklions888.SpellStorm.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class  BaseScreen <T extends Container> extends ContainerScreen<T> {

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
	public void func_230430_a_(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
		this.func_230446_a_(matrix);
		super.func_230430_a_(matrix, mouseX, mouseY, partialTicks);
		this.func_230459_a_(matrix, mouseX, mouseY);
	}

	@Override
	protected void func_230459_a_(MatrixStack matrix, int mouseX, int mouseY) {
		this.field_230712_o_.func_238405_a_(matrix, this.field_230704_d_.getString(), 3.0f, 3.0f, 4210752);
	}
	 
	@Deprecated
	@Override
	protected void func_230450_a_(MatrixStack matrix, float particalTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.field_230706_i_.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int x = (this.field_230708_k_ - this.xSize) / 2;
		int y = (this.field_230709_l_ - this.ySize) / 2;

		this.func_238474_b_(matrix, x, y, 0, 0, this.xSize, this.ySize);
	}
}
