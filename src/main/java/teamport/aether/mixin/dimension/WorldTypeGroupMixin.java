package teamport.aether.mixin.dimension;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypeGroups;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.AetherMod;

import java.util.Map;

@Mixin(WorldTypeGroups.Group.class)
public abstract class WorldTypeGroupMixin {
    @Shadow
    @Final
    private Map<Dimension, WorldType> worldTypes;

    @WrapMethod(method = "get")
    private WorldType provideMissingWorldType(Dimension dimension, Operation<WorldType> original) {
        if (dimension == null || this.worldTypes.containsKey(dimension)) {
            return original.call(dimension);
        }
        AetherMod.LOGGER.warn("World type group is missing dimension {} (id {}); using default", dimension.languageKey, dimension.id);
        return dimension.defaultWorldType;
    }
}
