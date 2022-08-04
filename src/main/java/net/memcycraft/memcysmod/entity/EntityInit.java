package net.memcycraft.memcysmod.entity;

import net.memcycraft.memcysmod.MemcysMod;
import net.memcycraft.memcysmod.entity.sculk_golem.SculkGolem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {

        public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
                MemcysMod.MOD_ID);

        public static final RegistryObject<EntityType<SculkGolem>> SCULK_GOLEM = register("sculk_golem",
                EntityType.Builder.of(SculkGolem::new, MobCategory.MONSTER).sized(1f, 1.5f));

    public static final <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder){
        return ENTITIES.register(name, () -> builder.build(MemcysMod.modLoc(name).toString()));
    }
}
