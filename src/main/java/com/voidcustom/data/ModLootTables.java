package com.voidcustom.data;

import com.voidcustom.Voidcustom;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class ModLootTables {
    public static ResourceKey<LootTable> ILIX_DEATH = ResourceKey.create(Registries.LOOT_TABLE, Voidcustom.id("entities/ilix"));
}
