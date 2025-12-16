package baguchan.piercearrow.client.layer;

import baguchan.piercearrow.PierceArrow;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextKey;

public class LivingArrowLayer<M extends EntityModel<LivingEntityRenderState>> extends LivingStuckInBodyLayer<M, ArrowRenderState> {
    public static final ContextKey<Integer> ARROW_KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(PierceArrow.MODID, "arrow"));

    public LivingArrowLayer(LivingEntityRenderer<?, LivingEntityRenderState, M> p_174466_, EntityRendererProvider.Context p_174465_) {
        super(
                p_174466_,
                new ArrowModel(p_174465_.bakeLayer(ModelLayers.ARROW)),
                new ArrowRenderState(),
                TippableArrowRenderer.NORMAL_ARROW_LOCATION,
                LivingStuckInBodyLayer.PlacementStyle.IN_CUBE
        );
    }

    @Override
    protected int numStuck(LivingEntityRenderState p_445491_) {
        return p_445491_.getRenderDataOrDefault(ARROW_KEY, 0);
    }
}
