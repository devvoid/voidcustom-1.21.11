package com.voidcustom;

import com.voidcustom.entities.MantisEntity;
import com.voidcustom.entities.ModEntities;
import com.voidcustom.items.ModItems;
import com.voidcustom.items.components.ModComponents;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Voidcustom implements ModInitializer {
	public static final String MOD_ID = "voidcustom";

    public static Identifier id(String name)
    {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModComponents.initialize();
		ModItems.initialize();
        ModEntities.initialize();

		LOGGER.info("Hello Fabric world!");
	}
}