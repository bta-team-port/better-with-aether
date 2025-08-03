package teamport.aether;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static teamport.aether.AetherMod.LOGGER;

@Environment(EnvType.SERVER)
public class AetherServer implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {

        LOGGER.info("AetherMod server initialized.");
    }
}
