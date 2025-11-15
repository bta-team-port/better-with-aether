package teamport.aether.mixin.accessory.cape.invisibility_cape.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.items.accessory.AetherInvisibility;

@Environment(EnvType.CLIENT)
@Mixin(value = ItemModelStandard.class, remap = false)
public abstract class MakeHeldItemInvisible {
    @WrapOperation(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/model/ItemModelStandard;renderItemInWorld(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/item/ItemStack;FFZ)V"))
    private void makeItemInvisible(ItemModelStandard instance, Tessellator tessellator, Entity entity, ItemStack itemStack, float brightness, float alpha, boolean worldTransform, Operation<Void> original) {
        original.call(instance, tessellator, entity, itemStack, brightness, entity instanceof Player && (entity != Minecraft.getMinecraft().thePlayer || Minecraft.getMinecraft().gameSettings.thirdPersonView.value != 0)  && ((AetherInvisibility) entity).aether$isInvisible() ? 0.05F : alpha, worldTransform);
    }
}
