package teamport.aether.mixin.accessory.cape.invisibility_cape.render;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.entity.player.PlayerUtil;

@Environment(EnvType.CLIENT)
@Mixin(value = RenderGlobal.class, remap = false)
public class HideDebugBoxes {
    @WrapMethod(method = "drawInterpolatedEntityBoundingBox")
    private void hideInvisibleBoundingBox(Entity entity, ICamera camera, float partialTicks, Operation<Void> original) {
        if (PlayerUtil.isInvisible(entity)) return;
        original.call(entity, camera, partialTicks);
    }
}
