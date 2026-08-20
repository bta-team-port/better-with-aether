package teamport.aether.mixin.accessory.cape.invisibility_cape.render;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.core.entity.Entity;
import org.joml.primitives.AABBdc;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.entity.player.PlayerUtil;

@Environment(EnvType.CLIENT)
@Mixin(RenderGlobal.class)
public class HideDebugBoxes {
    @WrapMethod(method = "drawInterpolatedEntityBoundingBox(Lnet/minecraft/core/entity/Entity;Lorg/joml/primitives/AABBdc;Lnet/minecraft/client/render/camera/ICamera;F)V")
    private void hideInvisibleBoundingBox(Entity entity, AABBdc boundingBox, ICamera camera, float partialTicks, Operation<Void> original) {
        if (PlayerUtil.isInvisible(entity)) return;
        original.call(entity, boundingBox, camera, partialTicks);
    }
}
