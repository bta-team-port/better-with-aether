package teamport.aether.mixin.net;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.net.PropertyManager;
import net.minecraft.core.world.Dimension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import teamport.aether.world.AetherDimension;

@Environment(EnvType.SERVER)
@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixinExcludeAether {
    @Shadow
    public PropertyManager propertyManager;

    @ModifyExpressionValue(
        method = "initWorld(Lnet/minecraft/core/world/save/ISaveFormat;Ljava/lang/String;J)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/world/Dimension;getDimensionList()Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;"
        )
    )
    private Int2ObjectMap<Dimension> loadOverworldFirst(Int2ObjectMap<Dimension> dimensions) {
        Dimension overworld = dimensions.get(Dimension.OVERWORLD.id);
        if (overworld == null || dimensions.values().iterator().next() == overworld) {
            return dimensions;
        }

        // don't touch this
        Int2ObjectMap<Dimension> orderedDimensions = new Int2ObjectArrayMap<>(dimensions.size());
        orderedDimensions.put(overworld.id, overworld);
        for (Dimension dimension : dimensions.values()) {
            if (dimension != overworld) {
                orderedDimensions.put(dimension.id, dimension);
            }
        }
        return orderedDimensions;
    }

    @Definition(id = "dimension", field = "Lnet/minecraft/server/world/WorldServer;dimension:Lnet/minecraft/core/world/Dimension;")
    @Definition(id = "DRIFT", field = "Lnet/minecraft/core/world/Dimension;DRIFT:Lnet/minecraft/core/world/Dimension;")
    @Expression("?.dimension != DRIFT")
    @ModifyExpressionValue(method = "initWorld(Lnet/minecraft/core/world/save/ISaveFormat;Ljava/lang/String;J)V", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean disableAetherGenerationOne(boolean original, @Local(ordinal = 1) WorldServer worldServer) {
        return original && (worldServer.dimension != AetherDimension.getAether() || this.propertyManager.getBooleanProperty("allow-aether", true));
    }

    @Definition(id = "getBooleanProperty", method = "Lnet/minecraft/core/net/PropertyManager;getBooleanProperty(Ljava/lang/String;Z)Z")
    @Expression("?.getBooleanProperty('allow-drift', false)")
    @ModifyExpressionValue(method = "initWorld(Lnet/minecraft/core/world/save/ISaveFormat;Ljava/lang/String;J)V", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean disableAetherGenerationTwo(boolean original, @Local(ordinal = 1) WorldServer worldServer) {
        return original && (worldServer.dimension != AetherDimension.getAether() || this.propertyManager.getBooleanProperty("allow-aether", true));
    }

    @Definition(id = "dimension", field = "Lnet/minecraft/server/world/WorldServer;dimension:Lnet/minecraft/core/world/Dimension;")
    @Definition(id = "DRIFT", field = "Lnet/minecraft/core/world/Dimension;DRIFT:Lnet/minecraft/core/world/Dimension;")
    @Expression("?.dimension != DRIFT")
    @ModifyExpressionValue(method = "doTick()V", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean disableAetherTravelOne(boolean original, @Local WorldServer worldServer) {
        return original && (worldServer.dimension != AetherDimension.getAether() || this.propertyManager.getBooleanProperty("allow-aether", true));
    }

    @Definition(id = "getBooleanProperty", method = "Lnet/minecraft/core/net/PropertyManager;getBooleanProperty(Ljava/lang/String;Z)Z")
    @Expression("?.getBooleanProperty('allow-drift', false)")
    @ModifyExpressionValue(method = "doTick()V", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean disableAetherTravelTwo(boolean original, @Local WorldServer worldServer) {
        return original && (worldServer.dimension != AetherDimension.getAether() || this.propertyManager.getBooleanProperty("allow-aether", true));
    }

    @ModifyArg(
        method = "initWorld(Lnet/minecraft/core/world/save/ISaveFormat;Ljava/lang/String;J)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/world/type/WorldTypeGroups$Group;get(Lnet/minecraft/core/world/Dimension;)Lnet/minecraft/core/world/type/WorldType;"
        ),
        index = 0
    )
    private Dimension fixWorldType(Dimension dimension) {
        return Dimension.OVERWORLD;
    }
}
