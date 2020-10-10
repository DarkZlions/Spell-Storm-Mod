package ch.darklions888.SpellStorm.client.renderer.tileentity;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import ch.darklions888.SpellStorm.client.renderer.entity.model.EndGateWayFragmentModel;
import ch.darklions888.SpellStorm.registries.ItemInit;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ISTER extends ItemStackTileEntityRenderer {
	
	public static final ISTER INSTANCE = new ISTER();
	private final EndGateWayFragmentModel fragmentModel = new EndGateWayFragmentModel();
	private static final Random RANDOM = new Random(31100L);
	
	// func_239207_a = render
	@Override
	public void func_239207_a_(ItemStack stack, TransformType transform, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		Item item = stack.getItem();
		if (item == ItemInit.END_GATEWAY_FRAGMENT.get()) {
			matrixStack.push();
			matrixStack.scale(1.0F, -1.0F, -1.0F);
			
			final List<RenderType> render_types = IntStream.range(0, 16).mapToObj((i) -> {
			      return RenderType.getEndPortal(i + 1);
			   }).collect(ImmutableList.toImmutableList());
			
			RANDOM.setSeed(31100L);
			float r = (RANDOM.nextFloat() * 0.5F + 0.1F) * 0.15f;
			float g = (RANDOM.nextFloat() * 0.5F + 0.4F) * 0.15f;
			float b = (RANDOM.nextFloat() * 0.5F + 0.5F) * 0.15f;
			
			IVertexBuilder vertBuilder = buffer.getBuffer(render_types.get(0));
			fragmentModel.render(matrixStack, vertBuilder, combinedLight, combinedOverlay, r, g, b, 1.0f);
			
			for (int j = 1; j < 15; j++) {
				float colorF = 2.0F / (float) (18 - j);
				r = (RANDOM.nextFloat() * 0.5F + 0.1F) * colorF;
				g = (RANDOM.nextFloat() * 0.5F + 0.4F) * colorF;
				b = (RANDOM.nextFloat() * 0.5F + 0.5F) * colorF;
				fragmentModel.render(matrixStack, buffer.getBuffer(render_types.get(j)), combinedLight, combinedOverlay, r, g, b, 1.0f);
			}

			matrixStack.pop();
		}
	}
}
