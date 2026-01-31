package com.voidcustom.items;

import com.voidcustom.Voidcustom;
import com.voidcustom.items.components.HomunculusBelly;
import com.voidcustom.items.components.ModComponents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ModItems {
    public static Item HOMUNCULUS = registerItem("homunculus", Homunculus::new, new Item.Properties().stacksTo(1).component(ModComponents.HOMUNCULUS_BELLY, HomunculusBelly.EMPTY));

    public static Item ILIX_MEAT = registerItem("ilix_meat", new Item.Properties().food(new FoodProperties(10, 10, false)).fireResistant());

    public static void initialize() {}

    private static ResourceKey<@NotNull Item> makeId(String name)
    {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Voidcustom.MOD_ID, name));
    }

    private static Item registerItem(String string, Item.Properties properties)
    {
        return registerItem(makeId(string), Item::new, properties);
    }

    private static Item registerItem(String string, Function<Item.Properties, Item> function, Item.Properties properties)
    {
        return registerItem(makeId(string), function, properties);
    }

    public static Item registerItem(ResourceKey<@NotNull Item> resourceKey, Function<Item.Properties, Item> function, Item.Properties properties) {
        Item item = function.apply(properties.setId(resourceKey));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }

        return Registry.register(BuiltInRegistries.ITEM, resourceKey, item);
    }
}
