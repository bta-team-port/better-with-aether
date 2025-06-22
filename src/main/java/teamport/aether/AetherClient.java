package teamport.aether;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ClientStartEntrypoint;

@Environment(EnvType.CLIENT)
public class AetherClient implements ClientModInitializer, ClientStartEntrypoint {
    public static final String MOD_ID = "aether|client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void beforeClientStart() {

    }

    @Override
    public void afterClientStart() {
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Aether client initialized.");
    }
}
