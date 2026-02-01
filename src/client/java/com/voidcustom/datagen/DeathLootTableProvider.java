package com.voidcustom.datagen;

import com.voidcustom.data.ModLootTables;
import com.voidcustom.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class DeathLootTableProvider extends SimpleFabricLootTableProvider {
    public DeathLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup, LootContextParamSets.ENTITY);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        consumer.accept(ModLootTables.ILIX_DEATH, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(ModItems.ILIX_MEAT)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 5.0f)))
                        ))
        );

        consumer.accept(ModLootTables.LOOT_BAG_COMMON, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.ILIX_MEAT))
                        .add(LootItem.lootTableItem(ModItems.ILIX_MILK_BUCKET))
                )
        );

        consumer.accept(ModLootTables.LOOT_BAG_UNCOMMON, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT))
                )
        );

        consumer.accept(ModLootTables.LOOT_BAG_RARE, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.DIAMOND)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(5))))
                        .add(LootItem.lootTableItem(ModItems.HOMUNCULUS))
                )
        );
    }
}
