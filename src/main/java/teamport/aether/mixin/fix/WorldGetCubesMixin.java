package teamport.aether.mixin.fix;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

import static teamport.aether.AetherMod.LOGGER;

@Mixin(value = World.class, remap = false, priority = 0)
public class WorldGetCubesMixin {

    @Inject(method = "getCubes", at = @At("HEAD"), cancellable = true)
    public void getCubes(Entity entity, AABB aabb, CallbackInfoReturnable<List<AABB>> cir) {
        double diffX = Math.abs(aabb.maxX - aabb.minX);
        double diffY = Math.abs(aabb.maxY - aabb.minY);
        double diffZ = Math.abs(aabb.maxZ - aabb.minZ);

        if (
               diffX > 1_000_000
            || diffY > 1_000_000
            || diffZ > 1_000_000
            || Double.isNaN(diffX)
            || Double.isNaN(diffY)
            || Double.isNaN(diffZ)
        ) {
            LOGGER.error("{} is moving too fast! Please send this to a developer!", Entity.getNameFromEntity(entity, true));
            Thread.dumpStack();

            entity.absMoveTo(0, 255, 0, 0f, 0f);
            entity.xo = 0;
            entity.yo = 0;
            entity.zo = 0;

            cir.setReturnValue(new ArrayList<>());
            cir.cancel();
        }
    }
}
