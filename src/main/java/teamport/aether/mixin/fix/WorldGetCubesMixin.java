package teamport.aether.mixin.fix;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.helper.MixinHelper;

import java.util.ArrayList;
import java.util.List;

import static teamport.aether.AetherMod.LOGGER;

@Mixin(value = World.class, priority = 0)
public abstract class WorldGetCubesMixin {
    @Shadow
    @Nullable
    public abstract Block<?> getBlock(int x, int y, int z);
    @WrapMethod(method = "getEntitiesWithinAABB")
    private @NonNull <T extends Entity> List<@NonNull T> getEntitiesWithinAABB(Class<T> ofClass, AABB aabb, Operation<List<T>> original) {
        return MixinHelper.preventStupidShit((World) (Object) this, ofClass, null, aabb, original);
    }
    @WrapMethod(method = "getEntitiesWithinAABBExcludingEntity")
    private List<Entity> getEntitiesWithinAABBExcludingEntity(Entity entity, AABB aabb, Operation<List<Entity>> original) {
        return MixinHelper.preventStupidShit((World) (Object) this, null, entity, aabb, original);
    }
    @WrapMethod(method = "getCubes")
    private List<AABB> getCubes(Entity entity, AABB aabb, Operation<List<AABB>> original) {
        if (MixinHelper.isBrokenAABB(aabb)) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("{} is moving too fast!! Please send this to a developer!", Entity.getNameFromEntity(entity, true));
            }
            Thread.dumpStack();

            entity.absMoveTo(0, 255, 0, 0f, 0f);
            entity.xo = 0;
            entity.yo = 0;
            entity.zo = 0;

            return new ArrayList<>();
        }
        return original.call(entity, aabb);
    }
}
