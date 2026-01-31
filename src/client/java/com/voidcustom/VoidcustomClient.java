package com.voidcustom;

import com.voidcustom.entities.ModEntities;
import com.voidcustom.model.MantisModel;
import com.voidcustom.renderer.MantisRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class VoidcustomClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(MantisModel.MANTIS, MantisModel::createBodyLayer);
        EntityRenderers.register(ModEntities.MANTIS, MantisRenderer::new);
	}
}