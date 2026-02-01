package com.voidcustom.data;

import com.voidcustom.Voidcustom;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class ModLootTables {
    public static ResourceKey<LootTable> ILIX_DEATH = ResourceKey.create(Registries.LOOT_TABLE, Voidcustom.id("entities/ilix"));

    public static ResourceKey<LootTable> LOOT_BAG_COMMON = ResourceKey.create(Registries.LOOT_TABLE, Voidcustom.id("bag/common"));
    public static ResourceKey<LootTable> LOOT_BAG_UNCOMMON = ResourceKey.create(Registries.LOOT_TABLE, Voidcustom.id("bag/uncommon"));
    public static ResourceKey<LootTable> LOOT_BAG_RARE = ResourceKey.create(Registries.LOOT_TABLE, Voidcustom.id("bag/rare"));
}
