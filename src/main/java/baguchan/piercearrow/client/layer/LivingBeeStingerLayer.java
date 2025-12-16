package baguchan.piercearrow.client.layer;

import baguchan.piercearrow.PierceArrow;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.animal.bee.BeeStingerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.context.ContextKey;

public class LivingBeeStingerLayer<M extends EntityModel<LivingEntityRenderState>> extends LivingStuckInBodyLayer<M, Unit> {
    private static final Identifier BEE_STINGER_LOCATION = Identifier.withDefaultNamespace("textures/entity/bee/bee_stinger.png");
    public static final ContextKey<Integer> STINGER_KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(PierceArrow.MODID, "stinger"));

    public LivingBeeStingerLayer(LivingEntityRenderer<?, LivingEntityRenderState, M> p_116580_, EntityRendererProvider.Context p_361959_) {
        super(
                p_116580_,
                new BeeStingerModel(p_361959_.bakeLayer(ModelLayers.BEE_STINGER)),
                Unit.INSTANCE,
                BEE_STINGER_LOCATION,
                LivingStuckInBodyLayer.PlacementStyle.ON_SURFACE
        );
    }

    @Override
    protected int numStuck(LivingEntityRenderState p_446215_) {
        return p_446215_.getRenderDataOrDefault(STINGER_KEY, 0);
    }
}
