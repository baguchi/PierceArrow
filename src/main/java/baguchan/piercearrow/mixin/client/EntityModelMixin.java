package baguchan.piercearrow.mixin.client;

import baguchan.piercearrow.api.IRandomModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;
import java.util.function.Function;

@Mixin(EntityModel.class)
public abstract class EntityModelMixin<T extends EntityRenderState> extends Model<T> implements IRandomModelPart {
    private List<ModelPart> parts;

    public EntityModelMixin(ModelPart p_368583_, Function<Identifier, RenderType> p_103110_) {
        super(p_368583_, p_103110_);
    }

    public ModelPart getRandomBodyPart(RandomSource p_103407_) {
        if (this.parts == null) {
            this.parts = this.root().getAllParts().stream().filter((p_170824_) -> {
                return !p_170824_.isEmpty();
            }).collect(ImmutableList.toImmutableList());
        }

        return this.parts.get(p_103407_.nextInt(this.parts.size()));
    }

}
