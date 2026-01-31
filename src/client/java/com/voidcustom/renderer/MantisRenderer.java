package com.voidcustom.renderer;

import com.voidcustom.Voidcustom;
import com.voidcustom.entities.MantisEntity;
import com.voidcustom.model.MantisModel;
import com.voidcustom.renderer.states.MantisRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class MantisRenderer extends MobRenderer<MantisEntity, MantisRenderState, MantisModel> {
    public static final Identifier MANTIS_NORMAL = Voidcustom.id("textures/entity/mantis/normal.png");

    public MantisRenderer(EntityRendererProvider.Context context) {
        super(context, new MantisModel(context.bakeLayer(MantisModel.MANTIS)), 0.7F);
    }

    @Override
    public @NotNull Identifier getTextureLocation(MantisRenderState livingEntityRenderState) {
        return MANTIS_NORMAL;
    }

    @Override
    public MantisRenderState createRenderState() {
        return new MantisRenderState();
    }

    @Override
    public void extractRenderState(MantisEntity mantis, MantisRenderState state, float f) {
        super.extractRenderState(mantis, state, f);
        state.idleAnimationState.copyFrom(mantis.idleAnimationState);
    }
}
