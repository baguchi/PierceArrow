package baguchan.piercearrow.mixin.client;

import baguchan.piercearrow.api.IRandomModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin implements IRandomModelPart {

	private List<ModelPart> parts;

	@Inject(method = "<init>(Lnet/minecraft/client/model/geom/ModelPart;Ljava/util/function/Function;)V", at = @At("TAIL"))
	public void onConstructor(ModelPart p_170679_, Function<ResourceLocation, RenderType> p_170680_, CallbackInfo callbackInfo) {
		this.parts = p_170679_.getAllParts().filter((p_170824_) -> {
			return !p_170824_.isEmpty();
		}).collect(ImmutableList.toImmutableList());
	}

	public ModelPart getRandomModelPart(Random p_103407_) {
		return this.parts.get(p_103407_.nextInt(this.parts.size()));
	}
}
