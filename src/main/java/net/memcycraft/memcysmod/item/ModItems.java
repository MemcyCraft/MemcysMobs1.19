package net.memcycraft.memcysmod.item;
import net.memcycraft.memcysmod.MemcysMod;
import net.memcycraft.memcysmod.entity.EntityInit;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MemcysMod.MOD_ID);

    public static final RegistryObject<Item> SCULK_KLEAFER = ITEMS.register("sculk_kleafer",
            () -> new SwordItem(ModTiers.SCULK_CRYSTAL, 4, 6f,
                    new Item.Properties().tab(ModCreativeModeTab.MEMCYS_TAB)));

    public static final RegistryObject<Item> SCULK_CRYSTAL = ITEMS.register("sculk_crystal",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MEMCYS_TAB)));

    public static final RegistryObject<Item> SHRIEKERS_SHIELD = ITEMS.register("shriekers_shield",
            () -> new ShieldItem(new ShieldItem.Properties().tab(ModCreativeModeTab.MEMCYS_TAB)));

    public static final RegistryObject<Item> SHRIEKERS_SCYTHE = ITEMS.register("shriekers_scythe",
            () -> new SwordItem(ModTiers.SCULK_CRYSTAL, 2, 3f,
                    new Item.Properties().tab(ModCreativeModeTab.MEMCYS_TAB)));

    public static final RegistryObject<Item> SCULK_BOW = ITEMS.register("sculk_bow",
            () -> new BowItem(new Item.Properties().tab(ModCreativeModeTab.MEMCYS_TAB).durability(500)));

    public static final RegistryObject<Item> SCULK_TENDRIL = ITEMS.register("sculk_tendril",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MEMCYS_TAB)));

    public static final RegistryObject<Item> SCULK_BONE = ITEMS.register("sculk_bone",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MEMCYS_TAB)));

    public static final RegistryObject<Item> PESTILENT_SWORD = ITEMS.register("pestilent_sword",
            () -> new SwordItem(ModTiers.SCULK_CRYSTAL, 6, 9f,
                    new Item.Properties().tab(ModCreativeModeTab.MEMCYS_TAB)));

    public static final RegistryObject<Item> SCULK_GOLEM_SPAWN_EGG = ITEMS.register("sculk_golem_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.SCULK_GOLEM,0x948e8d, 0x3b3635,
                    new Item.Properties().tab(ModCreativeModeTab.MEMCYS_TAB)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}