package net.memcycraft.memcysmod.entity.sculk_cube;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.memcycraft.memcysmod.MemcysMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SculkCubeRenderer extends GeoEntityRenderer<SculkCubeEntity> {
    public SculkCubeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SculkCubeModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(SculkCubeEntity instance) {
        return new ResourceLocation(MemcysMod.MOD_ID, "textures/entity/sculk_cube/sculkcube.png");
    }

    @Override
    public RenderType getRenderType(SculkCubeEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(0.8F, 0.8F, 0.8F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}