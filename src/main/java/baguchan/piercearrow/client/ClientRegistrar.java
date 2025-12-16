package baguchan.piercearrow.client;

import baguchan.piercearrow.PierceArrow;
import baguchan.piercearrow.client.layer.LivingArrowLayer;
import baguchan.piercearrow.client.layer.LivingBeeStingerLayer;
import baguchan.piercearrow.client.layer.LivingStuckInBodyLayer;
import com.google.common.reflect.TypeToken;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

@EventBusSubscriber(modid = PierceArrow.MODID, value = Dist.CLIENT)
public class ClientRegistrar {

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.AddLayers event) {
        event.getEntityTypes().stream().map(event::getRenderer).forEach(r -> {
                    if (r instanceof LivingEntityRenderer livingEntityRenderer) {
                        livingEntityRenderer.addLayer(new LivingArrowLayer(livingEntityRenderer, event.getContext()));
                        livingEntityRenderer.addLayer(new LivingBeeStingerLayer(livingEntityRenderer, event.getContext()));
                    }
                }
        );
    }
    @SubscribeEvent
    public static void registerState(RegisterRenderStateModifiersEvent event) {
        event.registerEntityModifier(new TypeToken<LivingEntityRenderer<LivingEntity, LivingEntityRenderState, ?>>() {}, (entity, renderState) -> {
            if(entity instanceof LivingEntity){
                renderState.setRenderData(LivingStuckInBodyLayer.ID_KEY, entity.getId());
                renderState.setRenderData(LivingArrowLayer.ARROW_KEY, ((LivingEntity) entity).getArrowCount());
                renderState.setRenderData(LivingBeeStingerLayer.STINGER_KEY, ((LivingEntity) entity).getStingerCount());
            }
        });
    }

}