package teamport.aether.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.discord.RichPresenceHandlerThread;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.biome.AetherBiomes;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(RichPresenceHandlerThread.class)
public abstract class DiscordRichPresenceMixin {

    @Shadow
    @Final
    private static Map<Dimension, String> dimensionNamesMap;

    @Shadow
    @Final
    private static Map<Biome, String> biomeNamesMap;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void attackTargetEntityWithCurrentItem(CallbackInfo ci) {
        dimensionNamesMap.put(AetherDimension.getAether(), "the Aether");
        biomeNamesMap.put(AetherBiomes.AETHER_PLAINS, "sky plains");
    }
}
