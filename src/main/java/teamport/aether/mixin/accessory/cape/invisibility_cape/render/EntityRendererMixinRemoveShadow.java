package teamport.aether.mixin.accessory.cape.invisibility_cape.render;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.entity.player.PlayerUtil;

@Environment(EnvType.CLIENT)
@Mixin(value = EntityRenderer.class)
public abstract class EntityRendererMixinRemoveShadow<T extends Entity> {
    @WrapMethod(method = "renderShadow")
    private void removeShadow(TessellatorGeneral tessellator, T entity, double posX, double posY, double posZ, float opacity, float partialTick, Operation<Void> original) {
        if (PlayerUtil.isInvisible(entity)) return;
        original.call(tessellator, entity, posX, posY, posZ, opacity, partialTick);
    }
}
