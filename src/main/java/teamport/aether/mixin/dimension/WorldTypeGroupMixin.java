package teamport.aether.mixin.dimension;

import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypeGroups;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.AetherMod;

import java.util.Map;

@Mixin(value = WorldTypeGroups.Group.class, remap = false)
public abstract class WorldTypeGroupMixin {
    @Shadow
    @Final
    private Map<Dimension, WorldType> worldTypes;

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void aether$provideMissingWorldType(Dimension dimension, CallbackInfoReturnable<WorldType> cir) {
        if (dimension != null && !this.worldTypes.containsKey(dimension)) {
            AetherMod.LOGGER.warn("World type group is missing dimension {} (id {}); using default", dimension.languageKey, dimension.id);
            cir.setReturnValue(dimension.defaultWorldType);
        }
    }
}
