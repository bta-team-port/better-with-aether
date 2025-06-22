package teamport.aether;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;

public class Aether implements GameStartEntrypoint, ModInitializer {
    public static final String MOD_ID = "aether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Aether initialized. Welcome to a hostile paradise.");
    }

    @Override
    public void beforeGameStart() {
    }

    @Override
    public void afterGameStart() {
    }
}
