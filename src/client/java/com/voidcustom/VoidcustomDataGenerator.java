package com.voidcustom;

import com.voidcustom.datagen.ModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class VoidcustomDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();
        pack.addProvider(ModelProvider::new);
	}
}
