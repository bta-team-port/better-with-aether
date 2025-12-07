package teamport.aether.mixin.dimension;

import net.minecraft.client.Minecraft;
import net.minecraft.core.world.type.WorldTypeGroups;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;

@Mixin(value = Minecraft.class)
public class ClearLevelDataMixin {
    @Inject(method = "startWorld(Ljava/lang/String;Ljava/lang/String;JLnet/minecraft/core/world/type/WorldTypeGroups$Group;)V", at = @At("HEAD"))
    public void clearData(String worldDirName, String worldName, long seed, WorldTypeGroups.Group worldTypeGroup, CallbackInfo ci) {
        AetherDimension.setDimensionDataDefaults();
        AetherDimension.setWorldDataDefaults();
    }

}
