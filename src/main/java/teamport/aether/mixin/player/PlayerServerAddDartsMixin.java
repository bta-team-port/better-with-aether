package teamport.aether.mixin.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.PlayerServer;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.SERVER)
@Mixin(PlayerServer.class)
public abstract class PlayerServerAddDartsMixin extends PlayerGetNextDartMixin {

    protected PlayerServerAddDartsMixin(@NonNull World world) {
        super(world);
    }

    @Inject(method = "onLivingUpdate", at = @At("TAIL"))
    private void syncDartId(CallbackInfo ci) {
        if (tickCount % 10 == 0) {
            Item dart = better_with_aether$getNextDart();
            entityData.set(19, dart == null ? -1 : dart.id);
        }
    }
}
