package teamport.aether.mixins.mixin.dimension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.world.settings.WorldConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public class ClearLevelDataMixin {
    @Inject(method = "startWorld(Ljava/lang/String;)V", at = @At("HEAD"))
    public void clearDataStartWorld(String worldDirName, CallbackInfo ci) {
        AetherDimension.setDimensionDataDefaults();
        AetherDimension.setWorldDataDefaults();
    }

    @Inject(method = "createAndStartWorld(Lnet/minecraft/core/world/settings/WorldConfiguration;)V", at = @At("HEAD"))
    public void clearDataCreateAndStartWorld(WorldConfiguration worldConfiguration, CallbackInfo ci) {
        AetherDimension.setDimensionDataDefaults();
        AetherDimension.setWorldDataDefaults();
    }

}
