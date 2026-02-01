package com.voidcustom.blocks;

import com.voidcustom.Voidcustom;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.List;
import java.util.function.Function;

public class ModBlocks {
    //region Rock roasts
    public static BlockBehaviour.Properties ROCK_ROAST_PROPERTIES = BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .strength(1.5F, 6.0F)
            .sound(SoundType.STONE)
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY)
            .requiresCorrectToolForDrops();

    public static Block COBBLESTONE_ROCK_ROAST = register("cobblestone_rock_roast", RockRoastBlock::new, ROCK_ROAST_PROPERTIES,
            new Item.Properties().food(Foods.APPLE));
    public static Block COPPER_ROCK_ROAST = register("copper_rock_roast", RockRoastBlock::new, ROCK_ROAST_PROPERTIES,
            new Item.Properties().food(Foods.BAKED_POTATO));
    public static Block IRON_ROCK_ROAST = register("iron_rock_roast", RockRoastBlock::new, ROCK_ROAST_PROPERTIES,
            new Item.Properties().food(Foods.COOKED_BEEF));
    public static Block GOLD_ROCK_ROAST = register("gold_rock_roast", RockRoastBlock::new, ROCK_ROAST_PROPERTIES,
            new Item.Properties().food(Foods.COOKED_BEEF).component(DataComponents.CONSUMABLE, Consumables.GOLDEN_APPLE));
    //endregion

    public static void initialize() {};

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties) {
        return register(name, function, properties, null);
    }

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        var id = Voidcustom.id(name);
        var block_key = ResourceKey.create(Registries.BLOCK, id);
        var block = function.apply(properties.setId(block_key));

        if (itemProperties != null) {
            var item_key = ResourceKey.create(Registries.ITEM, id);
            var block_item = new BlockItem(block, itemProperties.setId(item_key).useBlockDescriptionPrefix());
            Registry.register(BuiltInRegistries.ITEM, item_key, block_item);
        }
        return Registry.register(BuiltInRegistries.BLOCK, block_key, block);
    }
}
