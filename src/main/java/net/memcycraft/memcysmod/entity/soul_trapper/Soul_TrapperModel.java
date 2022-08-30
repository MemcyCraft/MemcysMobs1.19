package net.memcycraft.memcysmod.entity.soul_trapper;

import net.memcycraft.memcysmod.MemcysMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Soul_TrapperModel extends AnimatedGeoModel<Soul_Trapper> {

    @Override
    public ResourceLocation getModelResource(Soul_Trapper object) {
        return new ResourceLocation(MemcysMod.MOD_ID, "geo/soul_trapper.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Soul_Trapper object) {
        return new ResourceLocation(MemcysMod.MOD_ID, "textures/entity/soul_trapper/soul_trapper.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Soul_Trapper animatable) {
        return new ResourceLocation(MemcysMod.MOD_ID, "animations/soul_trapper.animation.json");
    }
}