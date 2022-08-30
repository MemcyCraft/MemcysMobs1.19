package net.memcycraft.memcysmod.entity.sculk_cube;

import net.memcycraft.memcysmod.MemcysMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SculkCubeModel extends AnimatedGeoModel<SculkCubeEntity> {

    @Override
    public ResourceLocation getModelResource(SculkCubeEntity object) {
        return new ResourceLocation(MemcysMod.MOD_ID, "geo/sculk_cube.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SculkCubeEntity object) {
        return new ResourceLocation(MemcysMod.MOD_ID, "textures/entity/sculk_cube/sculkcube.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SculkCubeEntity animatable) {
        return new ResourceLocation(MemcysMod.MOD_ID, "animations/sculk_cube.animation.json");
    }
}