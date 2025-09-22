package teamport.aether.mixin.accessory.cape.invisibilitycape.render;

import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.accessory.AetherInvisibility;

@Mixin(value = MobRendererPlayer.class, remap = false)
public class MobRenderPlayerMixinInvisibility {

    @Inject(method = "drawFirstPersonHand", at = @At("HEAD"), cancellable = true)
    public void callDrawFirstPersonHandBefore(Player player, boolean isLeft, CallbackInfo ci) {
        if (((AetherInvisibility)player).aether$isInvisible()) ci.cancel();
    }

    @Inject(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/player/Player;DDDFF)V", at = @At("HEAD"), cancellable = true)
    public void renderPlayer(Tessellator tessellator, Player entity, double x, double y, double z, float yaw, float partialTick, CallbackInfo ci) {
        if (((AetherInvisibility)entity).aether$isInvisible()) ci.cancel();
    }
}
