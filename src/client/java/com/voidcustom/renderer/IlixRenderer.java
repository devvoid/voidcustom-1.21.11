package com.voidcustom.renderer;

import com.voidcustom.Voidcustom;
import com.voidcustom.entities.Ilix;
import com.voidcustom.model.IlixModel;
import com.voidcustom.model.MantisModel;
import com.voidcustom.renderer.states.IlixRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class IlixRenderer extends MobRenderer<Ilix, IlixRenderState, IlixModel> {
    private static final Identifier ILIX_TEXTURE = Voidcustom.id("textures/entity/ilix/ilix.png");

    public IlixRenderer(EntityRendererProvider.Context context) {
        super(context, new IlixModel(context.bakeLayer(IlixModel.ILIX)), 0.7F);
    }

    @Override
    public IlixRenderState createRenderState() {
        return new IlixRenderState();
    }

    @Override
    public Identifier getTextureLocation(IlixRenderState livingEntityRenderState) {
        return ILIX_TEXTURE;
    }
}
