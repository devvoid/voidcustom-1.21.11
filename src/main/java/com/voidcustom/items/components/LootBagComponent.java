package com.voidcustom.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.voidcustom.data.ModLootTables;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.Consumer;

public record LootBagComponent(ResourceKey<LootTable> lootTable) {
    public static final Codec<LootBagComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(LootTable.KEY_CODEC.fieldOf("loot_table").forGetter(LootBagComponent::lootTable)).apply(instance, LootBagComponent::new)
    );

    public static final LootBagComponent COMMON = new LootBagComponent(ModLootTables.LOOT_BAG_COMMON);
    public static final LootBagComponent UNCOMMON = new LootBagComponent(ModLootTables.LOOT_BAG_UNCOMMON);
    public static final LootBagComponent RARE = new LootBagComponent(ModLootTables.LOOT_BAG_RARE);
}
