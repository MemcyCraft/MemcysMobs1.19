package net.memcycraft.memcysmod.entity.sculk_monstrosity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.memcycraft.memcysmod.MemcysMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class Sculk_MonstrosityRenderer extends GeoEntityRenderer<Sculk_Monstrosity> {
    public Sculk_MonstrosityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Sculk_MonstrosityModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(Sculk_Monstrosity instance) {
        return new ResourceLocation(MemcysMod.MOD_ID, "textures/entity/sculk_monstrosity/sculk_monstrosity.png");
    }

    @Override
    public RenderType getRenderType(Sculk_Monstrosity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(0.8F, 0.8F, 0.8F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}