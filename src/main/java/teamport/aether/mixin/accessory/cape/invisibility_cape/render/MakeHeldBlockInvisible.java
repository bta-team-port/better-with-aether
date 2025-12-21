package teamport.aether.mixin.accessory.cape.invisibility_cape.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.ItemRenderer;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.item.model.ItemModelBlock;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.item.accessory.AetherStatus;

@Environment(EnvType.CLIENT)
@Mixin(value = ItemModelBlock.class, remap = false)
public abstract class MakeHeldBlockInvisible {
    @WrapOperation(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/model/BlockModel;renderBlockOnInventory(Lnet/minecraft/client/render/tessellator/Tessellator;IFLjava/lang/Integer;)V"))
    private void makeItemInvisible(BlockModel<?> instance, Tessellator tessellator, int metadata, float brightness, Integer lightmapCoordinate, Operation<Void> original, Tessellator tessellatorTwo, ItemRenderer renderer, ItemStack itemstack, @Nullable Entity entity, float brightnessTwo, boolean handheldTransform) {
        if (entity instanceof Player && (entity != Minecraft.getMinecraft().thePlayer || Minecraft.getMinecraft().gameSettings.thirdPersonView.value != 0)  && ((AetherStatus) entity).aether$isInvisible()) {
            instance.renderBlockOnInventory(tessellator, metadata, brightness, 0.05F, lightmapCoordinate);
            return;
        }
        original.call(instance, tessellator, metadata, brightness, lightmapCoordinate);
    }
}
