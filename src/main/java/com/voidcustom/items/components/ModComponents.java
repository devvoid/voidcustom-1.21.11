package com.voidcustom.items.components;

import com.voidcustom.Voidcustom;
import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.enchantment.Enchantable;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

public class ModComponents {
    public static final DataComponentType<@NotNull HomunculusBelly> HOMUNCULUS_BELLY = register(
            "homunculus_belly",
            builder -> builder.persistent(HomunculusBelly.CODEC).networkSynchronized(HomunculusBelly.STREAM_CODEC).cacheEncoding()
            );

    public static void initialize() {
        ComponentTooltipAppenderRegistry.addAfter(
                DataComponents.DAMAGE,
                HOMUNCULUS_BELLY
        );
    }

    private static <T> DataComponentType<@NotNull T> register(String string, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                Identifier.fromNamespaceAndPath(Voidcustom.MOD_ID, string),
                unaryOperator.apply(DataComponentType.builder()).build()
        );
    }
}
