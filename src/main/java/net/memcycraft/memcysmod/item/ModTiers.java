package net.memcycraft.memcysmod.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static final ForgeTier SCULK_CRYSTAL = new ForgeTier(2, 14000, 1.5f,
             2f, 22, BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(ModItems.SCULK_CRYSTAL.get()));
}
