package net.memcycraft.memcysmod.entity.soul_trapper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.memcycraft.memcysmod.MemcysMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class Soul_TrapperRenderer extends GeoEntityRenderer<Soul_Trapper> {
    public Soul_TrapperRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Soul_TrapperModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(Soul_Trapper instance) {
        return new ResourceLocation(MemcysMod.MOD_ID, "textures/entity/soul_trapper/soul_trapper.png");
    }

    @Override
    public RenderType getRenderType(Soul_Trapper animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(0.8F, 0.8F, 0.8F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}