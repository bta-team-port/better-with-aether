package teamport.aether.mixin.fix;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

import static teamport.aether.AetherMod.LOGGER;

@Mixin(value = World.class, remap = false, priority = 0)
public abstract class WorldGetCubesMixin {

    @Shadow
    @Nullable
    public abstract Block<?> getBlock(int x, int y, int z);

    @Unique
    public boolean isBrokenAABB(AABB aabb) {
        double diffX = Math.abs(aabb.maxX - aabb.minX);
        double diffY = Math.abs(aabb.maxY - aabb.minY);
        double diffZ = Math.abs(aabb.maxZ - aabb.minZ);

        return diffX > 1_000_000
                || diffY > 1_000_000
                || diffZ > 1_000_000
                || Double.isNaN(diffX)
                || Double.isNaN(diffY)
                || Double.isNaN(diffZ);
    }


    @Unique
    public void preventStupidShit(Entity entity, AABB aabb, CallbackInfoReturnable<List<Entity>> cir) {
        if (isBrokenAABB(aabb)) {
            if (entity != null) {
                LOGGER.error("{} is moving too fast. Entity at {} {} {} with speed {}! Please send this to a developer!",
                        Entity.getNameFromEntity(entity, true),
                        entity.x, entity.y, entity.z,
                        Math.sqrt(entity.xd * entity.xd + entity.yd * entity.yd + entity.zd * entity.zd)
                );
                Block<?> block = this.getBlock((int) Math.round(entity.x), (int) Math.round(entity.y - 1), (int) Math.round(entity.z));
                String name = block == null ? "air" : block.getLanguageKey(0);
                LOGGER.error("Currently standing on: {} at ", name);
                LOGGER.error("Please send this log to a BWA developer!");
                Thread.dumpStack();
                entity.absMoveTo(0, 255, 0, 0f, 0f);
                entity.xo = 0;
                entity.yo = 0;
                entity.zo = 0;
            } else {
                LOGGER.error("Something is moving too fast! Please send this to a developer!");
                Thread.dumpStack();
            }

            cir.setReturnValue(new ArrayList<>());
            cir.cancel();
        }
    }

    @Inject(method = "getEntitiesWithinAABB", at = @At("HEAD"), cancellable = true)
    public <T extends Entity> void getEntitiesWithinAABB(Class<T> ofClass, AABB aabb, CallbackInfoReturnable<List<Entity>> cir) {
        preventStupidShit(null, aabb, cir);
    }

    @Inject(method = "getEntitiesWithinAABBExcludingEntity", at = @At("HEAD"), cancellable = true)
    public void getEntitiesWithinAABBExcludingEntity(Entity entity, AABB aabb, CallbackInfoReturnable<List<Entity>> cir) {
        preventStupidShit(entity, aabb, cir);
    }

    @Inject(method = "getCubes", at = @At("HEAD"), cancellable = true)
    public void getCubes(Entity entity, AABB aabb, CallbackInfoReturnable<List<AABB>> cir) {
        if (isBrokenAABB(aabb)) {
            LOGGER.error("{} is moving too fast!! Please send this to a developer!", Entity.getNameFromEntity(entity, true));
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
