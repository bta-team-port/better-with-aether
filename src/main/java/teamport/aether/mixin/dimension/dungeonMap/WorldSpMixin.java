package teamport.aether.mixin.dimension.dungeonMap;

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
public class WorldSpMixin {

    @Inject(method = "tick", at = @At("RETURN"))
    public void onTick(CallbackInfo ci) {
        DungeonMap.onWorldTick(World.class.cast(this));
    }

}
