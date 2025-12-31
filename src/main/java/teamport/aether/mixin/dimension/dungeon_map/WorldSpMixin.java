package teamport.aether.mixin.dimension.dungeon_map;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.feature.util.map.DungeonMap;

@Environment(EnvType.CLIENT)
@Mixin(value = World.class, remap = false)
public abstract class WorldSpMixin {
    @Inject(method = "tick", at = @At("RETURN"))
    private void onTick(CallbackInfo ci) {
        DungeonMap.onWorldTick((World) (Object) this);
    }
}
