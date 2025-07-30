package teamport.aether.mixin.accessory.cape.invisibilitycape;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.IAetherAccessories;

@Mixin(value = EntityRenderer.class, remap = false)
abstract public class EntityRendererMixin<T extends Entity> {

    @Inject(method = "renderShadow", at = @At("HEAD"), cancellable = true)
    private void onRenderShadow(Tessellator tessellator, T entity, double posX, double posY, double posZ, float opacity, float partialTick, CallbackInfo ci) {
        if (entity instanceof Player && ((IAetherAccessories)entity).aether$getInvisible()) {
            ci.cancel();
        }
    }
}
