package ch.darklions888.SpellStorm.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class EndGateWayFragmentModel extends Model {
	private ModelRenderer modelRenderer;
	
	public EndGateWayFragmentModel() {
		super(RenderType::getEntitySolid);
		this.modelRenderer = new ModelRenderer(0, 35, 64, 64);
		this.modelRenderer.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F);
		this.modelRenderer.setRotationPoint(0, 0, 0);

	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}
