package net.memcycraft.memcysmod;

import com.mojang.logging.LogUtils;
import net.memcycraft.memcysmod.block.ModBlocks;
import net.memcycraft.memcysmod.entity.EntityInit;
import net.memcycraft.memcysmod.entity.sculk_cube.SculkCubeEntity;
import net.memcycraft.memcysmod.entity.sculk_cube.SculkCubeRenderer;
import net.memcycraft.memcysmod.entity.sculk_golem.SculkGolem;
import net.memcycraft.memcysmod.entity.sculk_golem.SculkGolemRenderer;
import net.memcycraft.memcysmod.entity.sculk_monstrosity.Sculk_Monstrosity;
import net.memcycraft.memcysmod.entity.sculk_monstrosity.Sculk_MonstrosityRenderer;
import net.memcycraft.memcysmod.entity.soul_trapper.Soul_Trapper;
import net.memcycraft.memcysmod.entity.soul_trapper.Soul_TrapperRenderer;
import net.memcycraft.memcysmod.item.ModItems;
import net.memcycraft.memcysmod.util.ModItemProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MemcysMod.MOD_ID)
public class MemcysMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "memcysmod";

    private static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation modLoc(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public MemcysMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Add to the constructor
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        EntityInit.ENTITIES.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerEntityAttributes);
        modEventBus.addListener(this::clientSetup);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        GeckoLib.initialize();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Add to clientSetup method
        ModItemProperties.addCustomItemProperties();

    }

    private void clientSetup(FMLClientSetupEvent event){
        EntityRenderers.register(EntityInit.SCULK_GOLEM.get(), SculkGolemRenderer::new);
        EntityRenderers.register(EntityInit.SCULK_MONSTROSITY.get(), Sculk_MonstrosityRenderer::new);
        EntityRenderers.register(EntityInit.SOUL_TRAPPER.get(), Soul_TrapperRenderer::new);
        EntityRenderers.register(EntityInit.SCULK_CUBE.get(), SculkCubeRenderer::new);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event){
        event.put(EntityInit.SCULK_GOLEM.get(), SculkGolem.createAttributes());
        event.put(EntityInit.SCULK_MONSTROSITY.get(), Sculk_Monstrosity.createAttributes());
        event.put(EntityInit.SOUL_TRAPPER.get(), Soul_Trapper.createAttributes());
        event.put(EntityInit.SCULK_CUBE.get(), SculkCubeEntity.createAttributes());
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    //inner classes with event bus subscriber in main class r bad
    @Mod.EventBusSubscriber(modid = MemcysMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}


