package teamport.aether.mixin.dimension.dungeon_map;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.feature.util.map.DungeonMap;

@Environment(EnvType.SERVER)
@Mixin(value = WorldServer.class, remap = false)
public abstract class WorldMpMixin {
    @Inject(method = "tick", at = @At("RETURN"))
    public void onTick(CallbackInfo ci) {
        DungeonMap.onWorldTick((WorldServer) (Object) this);
    }
}
