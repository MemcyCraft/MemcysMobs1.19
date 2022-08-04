package net.memcycraft.memcysmod.entity.sculk_golem;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.memcycraft.memcysmod.MemcysMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SculkGolemRenderer extends GeoEntityRenderer<SculkGolem> {
    public SculkGolemRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SculkGolemModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(SculkGolem instance) {
        return new ResourceLocation(MemcysMod.MOD_ID, "textures/entity/sculk_golem/sculk_golem.png");
    }

    @Override
    public RenderType getRenderType(SculkGolem animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(0.8F, 0.8F, 0.8F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}