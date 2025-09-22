package teamport.aether.mixin.accessory.cape.invisibilitycape;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.accessory.AetherInvisibility;

@Mixin(value = EntityRenderer.class, remap = false)
abstract public class EntityRendererMixinRemoveShadow<T extends Entity> {

    @Inject(method = "renderShadow", at = @At("HEAD"), cancellable = true)
    public void removeShadow(Tessellator tessellator, T entity, double posX, double posY, double posZ, float opacity, float partialTick, CallbackInfo ci) {
        if (entity instanceof Player && ((AetherInvisibility)entity).aether$isInvisible()) {
            ci.cancel();
        }
    }
}
