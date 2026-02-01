package com.voidcustom.datagen;

import com.voidcustom.blocks.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {

    public BlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropSelf(ModBlocks.COBBLESTONE_ROCK_ROAST);
        dropSelf(ModBlocks.COPPER_ROCK_ROAST);
        dropSelf(ModBlocks.IRON_ROCK_ROAST);
        dropSelf(ModBlocks.GOLD_ROCK_ROAST);
    }
}
