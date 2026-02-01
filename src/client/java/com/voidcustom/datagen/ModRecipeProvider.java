package com.voidcustom.datagen;

import com.voidcustom.blocks.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                roastBuilder(ModBlocks.COBBLESTONE_ROCK_ROAST, Items.COBBLESTONE, Items.COBBLESTONE).save(output);
                roastBuilder(ModBlocks.COPPER_ROCK_ROAST, Items.RAW_COPPER, Items.COBBLESTONE).save(output);
                roastBuilder(ModBlocks.IRON_ROCK_ROAST, Items.RAW_IRON, Items.COBBLESTONE).save(output);
                roastBuilder(ModBlocks.GOLD_ROCK_ROAST, Items.RAW_GOLD, Items.COBBLESTONE).save(output);
            }

            public RecipeBuilder roastBuilder(ItemLike roast, ItemLike meat, ItemLike bone) {
                return this.shaped(RecipeCategory.FOOD, roast)
                        .pattern("###")
                        .pattern("#%#")
                        .pattern("###")
                        .define('#', meat)
                        .define('%', bone)
                        .unlockedBy("has_meat", has(meat))
                        .unlockedBy("has_bone", has(bone));
            }
        };
    }

    @Override
    public String getName() {
        return "VoidcustomRecipeProvider";
    }
}
