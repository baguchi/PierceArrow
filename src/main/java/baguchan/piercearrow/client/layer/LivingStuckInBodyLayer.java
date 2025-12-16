package baguchan.piercearrow.client.layer;

import baguchan.piercearrow.PierceArrow;
import baguchan.piercearrow.api.IRandomModelPart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.context.ContextKey;

public abstract class LivingStuckInBodyLayer<M extends EntityModel<LivingEntityRenderState>, S> extends RenderLayer<LivingEntityRenderState, M> {
    public static final ContextKey<Integer> ID_KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(PierceArrow.MODID, "id"));

    private final Model<S> model;
    private final S modelState;
    private final Identifier texture;
    private final LivingStuckInBodyLayer.PlacementStyle placementStyle;

    public LivingStuckInBodyLayer(
            LivingEntityRenderer<?, LivingEntityRenderState, M> p_117564_,
            Model<S> p_360318_,
            S p_435809_,
            Identifier p_467956_,
            LivingStuckInBodyLayer.PlacementStyle p_364028_
    ) {
        super(p_117564_);
        this.model = p_360318_;
        this.modelState = p_435809_;
        this.texture = p_467956_;
        this.placementStyle = p_364028_;
    }

    protected abstract int numStuck(LivingEntityRenderState p_447316_);

    private void submitStuckItem(
            PoseStack p_433276_, SubmitNodeCollector p_435462_, int p_432864_, float p_433745_, float p_435402_, float p_434638_, int p_435395_
    ) {
        float f = Mth.sqrt(p_433745_ * p_433745_ + p_434638_ * p_434638_);
        float f1 = (float) (Math.atan2(p_433745_, p_434638_) * 180.0F / (float) Math.PI);
        float f2 = (float) (Math.atan2(p_435402_, f) * 180.0F / (float) Math.PI);
        p_433276_.mulPose(Axis.YP.rotationDegrees(f1 - 90.0F));
        p_433276_.mulPose(Axis.ZP.rotationDegrees(f2));
        p_435462_.submitModel(
                this.model, this.modelState, p_433276_, this.model.renderType(this.texture), p_432864_, OverlayTexture.NO_OVERLAY, p_435395_, null
        );
    }

    public void submit(PoseStack p_436030_, SubmitNodeCollector p_432880_, int p_432778_, LivingEntityRenderState p_446278_, float p_434298_, float p_434732_) {
        int i = this.numStuck(p_446278_);
        if (i > 0 && this.getParentModel() instanceof IRandomModelPart randomModelPart) {
            RandomSource randomsource = RandomSource.create(p_446278_.getRenderDataOrDefault(ID_KEY, 0));

            for (int j = 0; j < i; j++) {
                p_436030_.pushPose();
                ModelPart modelpart = randomModelPart.getRandomBodyPart(randomsource);
                ModelPart.Cube modelpart$cube = modelpart.getRandomCube(randomsource);
                modelpart.translateAndRotate(p_436030_);
                float f = randomsource.nextFloat();
                float f1 = randomsource.nextFloat();
                float f2 = randomsource.nextFloat();
                if (this.placementStyle == LivingStuckInBodyLayer.PlacementStyle.ON_SURFACE) {
                    int k = randomsource.nextInt(3);
                    switch (k) {
                        case 0:
                            f = snapToFace(f);
                            break;
                        case 1:
                            f1 = snapToFace(f1);
                            break;
                        default:
                            f2 = snapToFace(f2);
                    }
                }

                p_436030_.translate(
                        Mth.lerp(f, modelpart$cube.minX, modelpart$cube.maxX) / 16.0F,
                        Mth.lerp(f1, modelpart$cube.minY, modelpart$cube.maxY) / 16.0F,
                        Mth.lerp(f2, modelpart$cube.minZ, modelpart$cube.maxZ) / 16.0F
                );
                this.submitStuckItem(p_436030_, p_432880_, p_432778_, -(f * 2.0F - 1.0F), -(f1 * 2.0F - 1.0F), -(f2 * 2.0F - 1.0F), p_446278_.outlineColor);
                p_436030_.popPose();
            }
        }
    }

    private static float snapToFace(float p_361108_) {
        return p_361108_ > 0.5F ? 1.0F : 0.5F;
    }

    public static enum PlacementStyle {
        IN_CUBE,
        ON_SURFACE;
    }
}
