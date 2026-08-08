package teamport.aether.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererItem;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.item.AetherItemTags;

@Environment(EnvType.CLIENT)
@Mixin(value = EntityRendererItem.class, remap = false)
public abstract class EntityRendererBobDownMixin {

    @ModifyExpressionValue(method = "render(Lnet/minecraft/client/render/tessellator/TessellatorGeneral;Lnet/minecraft/core/entity/EntityItem;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/util/helper/MathHelper;sin(F)F"))
    private float reverseBobbingSine(float original, TessellatorGeneral tessellator, EntityItem entity, double x, double y, double z, float yaw, float partialTick) {
        ItemStack stack = entity.item;
        if (stack != null && stack.getItem().hasTag(AetherItemTags.FALLS_UPWARDS)) {
            return original - 4.0f;
        } else return original;
    }
}
