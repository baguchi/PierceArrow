package baguchan.piercearrow;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PierceArrow.MODID)
public class PierceArrow {
    public static final String MODID = "piercearrow";

    public PierceArrow(IEventBus modBusEvent, ModContainer modContainer) {
        // Register the setup method for modloading
        modBusEvent.addListener(this::setup);

    }

    private void setup(final FMLCommonSetupEvent event) {
    }
}
