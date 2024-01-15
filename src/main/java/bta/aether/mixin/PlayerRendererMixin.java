package bta.aether.mixin;


import bta.aether.entity.IAetherAccessories;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerRenderer.class, remap = false)

public class PlayerRendererMixin extends LivingRenderer<EntityPlayer> {
    public PlayerRendererMixin(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    @Inject(method = "drawFirstPersonHand", at = @At("HEAD"), cancellable = true)
    public void calldrawFirstPersonHand(EntityPlayer player, CallbackInfo info) {
        if (((IAetherAccessories)player).aether$getInvisible()) {
            info.cancel();
        }
    }

    @Inject(method = "renderPlayer", at = @At("HEAD"), cancellable = true)
    public void renderPlayer(EntityPlayer entity, double d, double d1, double d2, float yaw, float partialTick, CallbackInfo info) {
        if (((IAetherAccessories)entity).aether$getInvisible()) {
            info.cancel();
        }
    }
}