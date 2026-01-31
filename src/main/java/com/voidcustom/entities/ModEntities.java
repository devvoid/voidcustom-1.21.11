package com.voidcustom.entities;

import com.voidcustom.Voidcustom;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ModEntities {
    public static final EntityType<@NotNull MantisEntity> MANTIS = register(
            "mantis",
            EntityType.Builder.of(MantisEntity::new, MobCategory.CREATURE)
                    .sized(0.4F, 0.7F)
                    .eyeHeight(0.644F)
                    .passengerAttachments(new Vec3(0.0, 0.7, -0.1))
                    .clientTrackingRange(10)
    );

    public static void initialize()
    {
        FabricDefaultAttributeRegistry.register(MANTIS, MantisEntity.createAttributes());
    }

    private static <T extends Entity> EntityType<@NotNull T> register(String name, EntityType.Builder<T> builder)
    {
        var id = Identifier.fromNamespaceAndPath(Voidcustom.MOD_ID, name);
        var key = ResourceKey.create(Registries.ENTITY_TYPE, id);
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }
}
