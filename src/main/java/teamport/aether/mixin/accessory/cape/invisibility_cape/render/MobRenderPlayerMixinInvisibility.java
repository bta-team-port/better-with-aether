package teamport.aether.mixin.accessory.cape.invisibility_cape.render;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.items.accessory.AetherInvisibility;

@Mixin(value = MobRendererPlayer.class, remap = false)
public abstract class MobRenderPlayerMixinInvisibility {
    @SuppressWarnings("MixinExtrasOperationParameters")
    @WrapMethod(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/player/Player;DDDFF)V")
    public void renderPlayer(Tessellator tessellator, Player entity, double x, double y, double z, float yaw, float partialTick, Operation<Void> original) {
        if (((AetherInvisibility) entity).aether$isInvisible()) return;
        original.call(tessellator, entity, x, y, z, yaw, partialTick);
    }
}
