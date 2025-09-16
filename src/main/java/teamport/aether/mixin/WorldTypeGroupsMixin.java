package teamport.aether.mixin;

import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypeGroups;
import net.minecraft.core.world.type.WorldTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;

@Mixin(value = WorldTypeGroups.class, remap = false)
public class WorldTypeGroupsMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void injectAetherSkyblock(CallbackInfo ci) {
        for (WorldTypeGroups.Group group : WorldTypeGroups.GROUPS) {
            WorldType overworldType = group.get(Dimension.OVERWORLD);
            if (overworldType == WorldTypes.OVERWORLD_SKYBLOCK) {
                group.with(AetherDimension.AETHER, AetherDimension.AETHER_SKYBLOCK);
                break;
            }
        }
    }
}
