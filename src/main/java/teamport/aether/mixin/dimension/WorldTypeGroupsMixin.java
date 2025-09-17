package teamport.aether.mixin.dimension;

import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypeGroups;
import net.minecraft.core.world.type.WorldTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.type.AetherWorldTypes;

@Mixin(value = WorldTypeGroups.class, remap = false)
public class WorldTypeGroupsMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void injectAetherSkyblock(CallbackInfo ci) {
        for (WorldTypeGroups.Group group : WorldTypeGroups.GROUPS) {
            WorldType overworldType = group.get(Dimension.OVERWORLD);
            if (overworldType == WorldTypes.OVERWORLD_SKYBLOCK) {
                group.with(AetherDimension.AETHER, AetherWorldTypes.AETHER_SKYBLOCK);
                break;
            }
        }
        for (WorldTypeGroups.Group group : WorldTypeGroups.GROUPS) {
            WorldType overworldType = group.get(Dimension.OVERWORLD);
            if (overworldType == WorldTypes.OVERWORLD_DEFAULT) {
                group.with(AetherDimension.AETHER, AetherWorldTypes.AETHER_DEFAULT);
                break;
            }
        }
        for (WorldTypeGroups.Group group : WorldTypeGroups.GROUPS) {
            WorldType overworldType = group.get(Dimension.OVERWORLD);
            if (overworldType == WorldTypes.OVERWORLD_EXTENDED || overworldType == WorldTypes.OVERWORLD_AMPLIFIED ||
                    overworldType == WorldTypes.OVERWORLD_INLAND || overworldType == WorldTypes.OVERWORLD_PARADISE ||
                    overworldType == WorldTypes.OVERWORLD_WOODS || overworldType == WorldTypes.OVERWORLD_HELL ||
                    overworldType == WorldTypes.OVERWORLD_WINTER || overworldType == WorldTypes.OVERWORLD_ISLANDS ||
                    overworldType == WorldTypes.OVERWORLD_FLOATING || overworldType == WorldTypes.FLAT ||
                    overworldType == WorldTypes.EMPTY || overworldType == WorldTypes.DEBUG) {
                group.with(AetherDimension.AETHER, AetherWorldTypes.AETHER_EXTENDED);
                break;
            }
        }
        for (WorldTypeGroups.Group group : WorldTypeGroups.GROUPS) {
            WorldType overworldType = group.get(Dimension.OVERWORLD);
            if (overworldType == WorldTypes.OVERWORLD_RETRO || overworldType == WorldTypes.OVERWORLD_CLASSIC || overworldType == WorldTypes.OVERWORLD_INDEV) {
                group.with(AetherDimension.AETHER, AetherWorldTypes.AETHER_RETRO);
                break;
            }
        }
    }
}
