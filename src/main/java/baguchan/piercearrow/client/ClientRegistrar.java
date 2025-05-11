package baguchan.piercearrow.client;

import baguchan.piercearrow.PierceArrow;
import baguchan.piercearrow.client.layer.LivingArrowLayer;
import baguchan.piercearrow.client.layer.LivingBeeStingerLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = PierceArrow.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.AddLayers event) {
        event.getEntityTypes().stream().map(event::getRenderer).forEach(r -> {
                    if (r instanceof LivingEntityRenderer livingEntityRenderer) {
                        livingEntityRenderer.addLayer(new LivingArrowLayer(Minecraft.getInstance().getEntityRenderDispatcher(), livingEntityRenderer));
                        livingEntityRenderer.addLayer(new LivingBeeStingerLayer(livingEntityRenderer));
                    }
                }
        );
    }
}