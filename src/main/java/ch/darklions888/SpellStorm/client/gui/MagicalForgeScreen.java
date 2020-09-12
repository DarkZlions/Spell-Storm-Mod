package ch.darklions888.SpellStorm.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.containers.MagicalForgeContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class MagicalForgeScreen extends BaseScreen<MagicalForgeContainer> {

	public MagicalForgeScreen(MagicalForgeContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn, 175, 165, Lib.RegistryNames.MAGICAL_FORGE_SCREEN_BACKGROUND_TEXTURE);
	}
	
	@Deprecated
	@Override
	protected void func_230450_a_(MatrixStack matrixStack, float partialTicks, int x, int y) {
		
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.field_230706_i_.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.func_238474_b_(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
		if (this.container.isBurning()) {
			int k = this.container.getBurnLeftScaled();
			this.func_238474_b_(matrixStack, i + 41, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
		}

		int l = this.container.getMergProgressionScaled();
		this.func_238474_b_(matrixStack, i + 79, j + 34, 176, 14, l + 1, 16);
	}
}
