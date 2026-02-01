package com.voidcustom.datagen;

import com.voidcustom.Voidcustom;
import com.voidcustom.blocks.ModBlocks;
import com.voidcustom.blocks.RockRoastBlock;
import com.voidcustom.items.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class ModelProvider extends FabricModelProvider {
    public static final ModelTemplate TEMPLATE_ROCK_ROAST = block("template_rock_roast", TextureSlot.TEXTURE);
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(@NotNull BlockModelGenerators modelGen) {
        registerRockRoast(modelGen, ModBlocks.COBBLESTONE_ROCK_ROAST, "cobblestone");
        registerRockRoast(modelGen, ModBlocks.COPPER_ROCK_ROAST, "copper");
        registerRockRoast(modelGen, ModBlocks.IRON_ROCK_ROAST, "iron");
        registerRockRoast(modelGen, ModBlocks.GOLD_ROCK_ROAST, "gold");
    }

    @Override
    public void generateItemModels(ItemModelGenerators modelGen) {
        modelGen.generateBooleanDispatch(
                ModItems.HOMUNCULUS,
                ItemModelUtils.isUsingItem(),
                ItemModelUtils.plainModel(modelGen.createFlatItemModel(ModItems.HOMUNCULUS, "_squished", ModelTemplates.FLAT_ITEM)),
                ItemModelUtils.plainModel(modelGen.createFlatItemModel(ModItems.HOMUNCULUS, ModelTemplates.FLAT_ITEM))
        );

        modelGen.generateFlatItem(ModItems.ILIX_MEAT, ModelTemplates.FLAT_ITEM);
        modelGen.generateFlatItem(ModItems.ILIX_MILK_BUCKET, ModelTemplates.FLAT_ITEM);
        modelGen.generateFlatItem(ModItems.LOOT_BAG, ModelTemplates.FLAT_ITEM);
    }

    public @NotNull String getName() {
        return "VoidCustomModelProvider";
    }

    private static BlockModelDefinitionGenerator createRockRoastStates(Block block, Identifier id)
    {
        var m = BlockModelGenerators.plainVariant(id);
        return MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(RockRoastBlock.FACING)
                        .select(Direction.EAST, m.with(Y_ROT_90))
                        .select(Direction.SOUTH, m.with(Y_ROT_180))
                        .select(Direction.WEST, m.with(Y_ROT_270))
                        .select(Direction.NORTH, m)
                );
    }

    private static void registerRockRoast(BlockModelGenerators gen, Block block, String variant)
    {
        var blk_name = String.format("block/%s_rock_roast", variant);
        var tex_name = String.format("block/rock_roast_%s", variant);
        var tex = new TextureMapping()
                .put(TextureSlot.TEXTURE, Voidcustom.id(tex_name));
        var id = TEMPLATE_ROCK_ROAST.create(block, tex, gen.modelOutput);
        gen.blockStateOutput.accept(createRockRoastStates(block, Voidcustom.id(blk_name)));
        gen.registerSimpleItemModel(block, id);
    }

    //helper method for creating Models
    private static ModelTemplate block(String parent, TextureSlot... requiredTextureKeys) {
        return new ModelTemplate(Optional.of(Voidcustom.id("block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    //helper method for creating Models with variants
    private static ModelTemplate block(String parent, String variant, TextureSlot... requiredTextureKeys) {
        return new ModelTemplate(Optional.of(Voidcustom.id("block/" + parent)), Optional.of(variant), requiredTextureKeys);
    }
}
