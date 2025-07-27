package teamport.aether.world;

import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import sunsetsatellite.catalyst.core.util.DataInitializer;

import static org.apache.log4j.builders.appender.SocketAppenderBuilder.LOGGER;

public class AetherWorldTypes extends DataInitializer {

    public static WorldType AETHER;

    @Override
    public void init() {
        if(initialized) return;
        LOGGER.info("Initializing world types...");
        AETHER = WorldTypes.register("aether:aether", new WorldTypeAether("aether.aether") {

        });

        setInitialized(true);
    }
}
