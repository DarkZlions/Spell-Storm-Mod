package ch.darklions888.SpellStorm.client.renderer.tileentity;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import ch.darklions888.SpellStorm.lib.Lib;
import ch.darklions888.SpellStorm.objects.tileentities.GateWayCoreTileEntity;
import ch.darklions888.SpellStorm.util.helpers.mathhelpers.RayTraceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GateWayCoreTileEntityRenderer<T extends GateWayCoreTileEntity> extends TileEntityRenderer <T> {

	   public static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
	   public static final ResourceLocation END_PORTAL_TEXTURE = Lib.RegistryNames.GATEWAY_TEXTURE;
	   private static final Random RANDOM = new Random(31100L);
	   private static final List<RenderType> RENDER_TYPES = IntStream.range(0, 16).mapToObj((i) -> {
	      return RenderType.getEndPortal(i + 1);
	   }).collect(ImmutableList.toImmutableList());
	
	public GateWayCoreTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(T tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		
		if (tileEntityIn.isActivated()) {
			RANDOM.setSeed(31100L);
			double d0 = tileEntityIn.getPos().distanceSq(this.renderDispatcher.renderInfo.getProjectedView(), true);
			int i = this.getPasses(d0);
			float offset = this.getOffset();
			Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
			this.renderCube(tileEntityIn, offset, 0.15F, matrix4f, bufferIn.getBuffer(RENDER_TYPES.get(0)));

			for (int j = 1; j < i; ++j) {
				this.renderCube(tileEntityIn, offset, 2.0F / (float) (18 - j), matrix4f, bufferIn.getBuffer(RENDER_TYPES.get(j)));
			}
		}

		Entity entity = Minecraft.getInstance().getRenderViewEntity();
		BlockRayTraceResult  result = (BlockRayTraceResult)RayTraceHelper.CustomrayTrace(tileEntityIn.getWorld(), entity, FluidMode.NONE, 10d);

		if (result != null && result.getType() == RayTraceResult.Type.BLOCK) {
			BlockPos blockpos = ((BlockRayTraceResult) result).getPos();
			if (tileEntityIn.getName() != null && blockpos.equals(tileEntityIn.getPos())) {
				this.renderName(tileEntityIn, tileEntityIn.getName(), matrixStackIn, bufferIn, combinedLightIn);
			}
		}
	}

	private void renderCube(T tileEntityIn, float offset, float colorF, Matrix4f matrix4fIn, IVertexBuilder bufferIn) {
		float r = (RANDOM.nextFloat() * 0.5F + 0.1F) * colorF;
		float g = (RANDOM.nextFloat() * 0.5F + 0.4F) * colorF;
		float b = (RANDOM.nextFloat() * 0.5F + 0.5F) * colorF;
		this.renderFace(tileEntityIn, matrix4fIn, bufferIn, 0.25F, 0.75F, 0.25F, 0.75F, 0.75F, 0.75F, 0.75F, 0.75F, r, g, b, Direction.SOUTH);
		this.renderFace(tileEntityIn, matrix4fIn, bufferIn, 0.25F, 0.75F, 0.75F, 0.25F, 0.25F, 0.25F, 0.25F, 0.25F, r, g, b, Direction.NORTH);
		this.renderFace(tileEntityIn, matrix4fIn, bufferIn, 0.75F, 0.75F, 0.75F, 0.25F, 0.25F, 0.75F, 0.75F, 0.25F, r, g, b, Direction.EAST);
		this.renderFace(tileEntityIn, matrix4fIn, bufferIn, 0.25F, 0.25F, 0.25F, 0.75F, 0.25F, 0.75F, 0.75F, 0.25F, r, g, b, Direction.WEST);
		this.renderFace(tileEntityIn, matrix4fIn, bufferIn, 0.25F, 0.75F, 0.25F, 0.25F, 0.25F, 0.25F, 0.75F, 0.75F, r, g, b, Direction.DOWN);
		this.renderFace(tileEntityIn, matrix4fIn, bufferIn, 0.25F, 0.75F, 0.75F, 0.75F, 0.75F, 0.75F, 0.25F, 0.25F, r, g, b, Direction.UP);
	}

	// v = vertex
	private void renderFace(T tileEntityIn, Matrix4f matrix4fIn, IVertexBuilder bufferIn, float v1, float v2, float v3, float v4, float v5, float v6, float v7, float v8, float r, float g, float b, Direction direction) {
			bufferIn.pos(matrix4fIn, v1, v3, v5).color(r, g, b, 1.0F).endVertex();
			bufferIn.pos(matrix4fIn, v2, v3, v6).color(r, g, b, 1.0F).endVertex();
			bufferIn.pos(matrix4fIn, v2, v4, v7).color(r, g, b, 1.0F).endVertex();
			bufferIn.pos(matrix4fIn, v1, v4, v8).color(r, g, b, 1.0F).endVertex();
	}
	
	protected void renderName(T tileEntityIn, ITextComponent displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		
		double distance = tileEntityIn.getPos().distanceSq(this.renderDispatcher.renderInfo.getProjectedView(), true);
	
		if (distance <= 64d) {			
			
			float renderHeight =  1.5F;
			
			matrixStackIn.push();
			matrixStackIn.translate(0.5d, (double) renderHeight, 0.5d);
			matrixStackIn.rotate(this.renderDispatcher.renderInfo.getRotation());
			matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
		
			Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
			int backgroundColor = 0;
			FontRenderer fontrenderer = this.renderDispatcher.fontRenderer;
			boolean transparent = false;
			int y = -10;
			float x = (float) (-fontrenderer.getStringPropertyWidth(displayNameIn) / 2);
			
			fontrenderer.func_243247_a(displayNameIn, x, (float) y, 0x80FFFFFF, false, matrix4f, bufferIn, transparent, backgroundColor, packedLightIn);
			
			if (transparent) {
				fontrenderer.func_243247_a(displayNameIn, x, (float) y, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
			}

			matrixStackIn.pop();
		}
	}

	protected int getPasses(double doubleIn) {
		if (doubleIn > 36864.0D) {
			return 1;
		} else if (doubleIn > 25600.0D) {
			return 3;
		} else if (doubleIn > 16384.0D) {
			return 5;
		} else if (doubleIn > 9216.0D) {
			return 7;
		} else if (doubleIn > 4096.0D) {
			return 9;
		} else if (doubleIn > 1024.0D) {
			return 11;
		} else if (doubleIn > 576.0D) {
			return 13;
		} else {
			return doubleIn > 256.0D ? 14 : 15;
		}
	}

	protected float getOffset() {
		return 1.0f;
	}
}
