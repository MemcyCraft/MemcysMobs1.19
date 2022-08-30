package net.memcycraft.memcysmod.entity.sculk_monstrosity;

import net.memcycraft.memcysmod.MemcysMod;
import net.memcycraft.memcysmod.entity.sculk_golem.SculkGolem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Sculk_MonstrosityModel extends AnimatedGeoModel<Sculk_Monstrosity> {

    @Override
    public ResourceLocation getModelResource(Sculk_Monstrosity object) {
        return new ResourceLocation(MemcysMod.MOD_ID, "geo/sculk_monstrosity.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Sculk_Monstrosity object) {
        return new ResourceLocation(MemcysMod.MOD_ID, "textures/entity/sculk_monstrosity/sculk_monstrosity.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Sculk_Monstrosity animatable) {
        return new ResourceLocation(MemcysMod.MOD_ID, "animations/sculk_monstrosity.animation.json");
    }
}