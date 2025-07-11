package teamport.aether;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.entity.animal.MobFireflyCluster;
import net.minecraft.core.sound.SoundTypes;
import net.minecraft.core.world.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.AetherEntities;
import teamport.aether.items.AetherItems;
import turniplabs.halplibe.util.GameStartEntrypoint;

import static net.minecraft.core.entity.animal.MobFireflyCluster.FireflyColor.register;

public class AetherMod implements GameStartEntrypoint, ModInitializer {
    public static final String MOD_ID = "aether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static MobFireflyCluster.FireflyColor SILVER;

    @Override
    public void onInitialize() {
        LOGGER.info("Aether initialized. Welcome to a hostile paradise.");
    }

    @Override
    public void beforeGameStart() {
        SILVER = register(new MobFireflyCluster.FireflyColor(10, "fireflySilver", new Biome[]{}, new float[]{0.5F, 1.0F, 0.88F}));
        //TODO Replace biome here with aether biome once added

        AetherEntities.init();
        AetherBlocks.init();
        AetherItems.init();

        SoundTypes.loadSoundsJson(MOD_ID);

        /** these are client-side  only! */
//        ParticleDispatcher dispatcher = ParticleDispatcher.getInstance();

//        dispatcher.addDispatch("flameambrosium", (world, x, y, z, xa, ya, za, id) -> new ParticleFlameAmbrosium(world, x, y, z, xa, ya, za));
//        dispatcher.addDispatch("darttrail", (world, x, y, z, xa, ya, za, id) -> new ParticleDartEnchanted(world, x, y, z, xa, ya, za));
//        dispatcher.addDispatch("fireflySilver", (world, x, y, z, motionX, motionY, motionZ, data) -> new ParticleFirefly(world, x, y, z, motionX, motionY, motionZ, AetherMod.SILVER.getId()));

    }

    @Override
    public void afterGameStart() {
    }
}
