package teamport.aether.mixin.fix;

import net.minecraft.core.world.World;
import net.minecraft.core.world.save.LevelData;
import net.minecraft.core.world.type.WorldType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static teamport.aether.AetherMod.LOGGER;

@Mixin(value = WorldType.class, remap = false, priority = 0)
public abstract class WorldTypeOverworldRespawnMixin {

    @Shadow
    public abstract int getOceanY();

    @Inject(method = "getRespawnLocation", at = @At("HEAD"), cancellable = true)
    public void respawnLocation(World world, CallbackInfo ci) {
        LOGGER.debug("Commandeering respawnLocation...");

        LevelData levelData = world.getLevelData();
        if (levelData.getSpawnY() <= 0) levelData.setSpawnY(getOceanY());

        int attempts = 0;

        int x = levelData.getSpawnX();
        int z = levelData.getSpawnZ();
        while (world.getTopBlock(x, z) == 0) {
            if (attempts++ > 500) break;

            z += world.rand.nextInt(8) - world.rand.nextInt(8);
            x += world.rand.nextInt(8) - world.rand.nextInt(8);
        }

        if (attempts < 500) {
            levelData.setSpawnX(x);
            levelData.setSpawnZ(z);
        }

        ci.cancel();
    }
}
