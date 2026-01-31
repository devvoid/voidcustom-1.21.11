package com.voidcustom.datagen;

import com.voidcustom.items.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import org.jetbrains.annotations.NotNull;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators modelGen) {
        modelGen.generateBooleanDispatch(
                ModItems.HOMUNCULUS,
                ItemModelUtils.isUsingItem(),
                ItemModelUtils.plainModel(modelGen.createFlatItemModel(ModItems.HOMUNCULUS, "_squished", ModelTemplates.FLAT_ITEM)),
                ItemModelUtils.plainModel(modelGen.createFlatItemModel(ModItems.HOMUNCULUS, ModelTemplates.FLAT_ITEM))
        );
    }

    public @NotNull String getName() {
        return "VoidCustomModelProvider";
    }
}
