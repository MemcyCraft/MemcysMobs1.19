package net.memcycraft.memcysmod.entity;

import net.memcycraft.memcysmod.MemcysMod;
import net.memcycraft.memcysmod.entity.sculk_cube.SculkCubeEntity;
import net.memcycraft.memcysmod.entity.sculk_golem.SculkGolem;
import net.memcycraft.memcysmod.entity.sculk_monstrosity.Sculk_Monstrosity;
import net.memcycraft.memcysmod.entity.soul_trapper.Soul_Trapper;
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

    public static final RegistryObject<EntityType<Sculk_Monstrosity>> SCULK_MONSTROSITY = register("sculk_monstrosity",
            EntityType.Builder.of(Sculk_Monstrosity::new, MobCategory.MONSTER).sized(1f, 1.5f));

    public static final RegistryObject<EntityType<Soul_Trapper>> SOUL_TRAPPER = register("soul_trapper",
            EntityType.Builder.of(Soul_Trapper::new, MobCategory.MONSTER).sized(1f, 1.5f));

    public static final RegistryObject<EntityType<SculkCubeEntity>> SCULK_CUBE = register("sculk_cube",
            EntityType.Builder.of(SculkCubeEntity::new, MobCategory.MONSTER).sized(1f, 1.5f));

    public static final <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder){
        return ENTITIES.register(name, () -> builder.build(MemcysMod.modLoc(name).toString()));
    }
}
