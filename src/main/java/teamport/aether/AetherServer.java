package teamport.aether;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.SoundRepository;
import teamport.aether.command.AetherCommand;

import static org.apache.log4j.builders.appender.SocketAppenderBuilder.LOGGER;
import static teamport.aether.AetherMod.MOD_ID;

@Environment(EnvType.SERVER)
public class AetherServer implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        SoundRepository.registerNamespace(MOD_ID);
        AetherCommand.registerServerCommands();

        LOGGER.info("AetherMod server initialized.");
    }
}
