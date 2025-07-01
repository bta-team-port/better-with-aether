package teamport.aether;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.sound.SoundTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.AetherItems;
import teamport.aether.particle.ParticleDartEnchanted;
import teamport.aether.particle.ParticleFlameAmbrosium;
import turniplabs.halplibe.helper.ParticleHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;

public class AetherMod implements GameStartEntrypoint, ModInitializer {
    public static final String MOD_ID = "aether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Aether initialized. Welcome to a hostile paradise.");
    }

    @Override
    public void beforeGameStart() {
        new AetherBlocks().initializeBlocks();
        new AetherItems().initializeItems();

        SoundTypes.loadSoundsJson(MOD_ID);

        ParticleHelper.createParticle("flameambrosium", (world, x, y, z, xa, ya, za, id) -> new ParticleFlameAmbrosium(world, x, y, z, xa, ya, za));
        ParticleHelper.createParticle("darttrail", (world, x, y, z, xa, ya, za, id) -> new ParticleDartEnchanted(world, x, y, z, xa, ya, za));

    }

    @Override
    public void afterGameStart() {
    }
}
