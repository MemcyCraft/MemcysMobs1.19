package net.memcycraft.memcysmod.entity.sculk_golem;

import net.memcycraft.memcysmod.MemcysMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SculkGolemModel extends AnimatedGeoModel<SculkGolem> {

    @Override
    public ResourceLocation getModelResource(SculkGolem object) {
        return new ResourceLocation(MemcysMod.MOD_ID, "geo/sculk_golem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SculkGolem object) {
        return new ResourceLocation(MemcysMod.MOD_ID, "textures/entity/sculk_golem/sculk_golem.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SculkGolem animatable) {
        return new ResourceLocation(MemcysMod.MOD_ID, "animations/sculk_golem.animation.json");
    }
}