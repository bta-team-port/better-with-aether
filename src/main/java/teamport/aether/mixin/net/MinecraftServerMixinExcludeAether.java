package teamport.aether.mixin.net;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.net.PropertyManager;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypeGroups;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import teamport.aether.world.AetherDimension;

@Environment(EnvType.SERVER)
@Mixin(value = MinecraftServer.class)
public abstract class MinecraftServerMixinExcludeAether {
    @Shadow
    public PropertyManager propertyManager;

    @Definition(id = "dimension", field = "Lnet/minecraft/server/world/WorldServer;dimension:Lnet/minecraft/core/world/Dimension;")
    @Definition(id = "PARADISE", field = "Lnet/minecraft/core/world/Dimension;PARADISE:Lnet/minecraft/core/world/Dimension;")
    @Expression("?.dimension != PARADISE")
    @ModifyExpressionValue(method = "initWorld", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean disableAetherGenerationOne(boolean original, @Local WorldServer worldServer) {
        return original && (worldServer.dimension != AetherDimension.getAether() || this.propertyManager.getBooleanProperty("allow-aether", true));
    }

    @Definition(id = "getBooleanProperty", method = "Lnet/minecraft/core/net/PropertyManager;getBooleanProperty(Ljava/lang/String;Z)Z")
    @Expression("?.getBooleanProperty('allow-paradise', false)")
    @ModifyExpressionValue(method = "initWorld", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean disableAetherGenerationTwo(boolean original, @Local WorldServer worldServer) {
        return original && (worldServer.dimension != AetherDimension.getAether() || this.propertyManager.getBooleanProperty("allow-aether", true));
    }

    @Definition(id = "dimension", field = "Lnet/minecraft/server/world/WorldServer;dimension:Lnet/minecraft/core/world/Dimension;")
    @Definition(id = "PARADISE", field = "Lnet/minecraft/core/world/Dimension;PARADISE:Lnet/minecraft/core/world/Dimension;")
    @Expression("?.dimension != PARADISE")
    @ModifyExpressionValue(method = "doTick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean disableAetherTravelOne(boolean original, @Local WorldServer worldServer) {
        return original && (worldServer.dimension != AetherDimension.getAether() || this.propertyManager.getBooleanProperty("allow-aether", true));
    }

    @Definition(id = "getBooleanProperty", method = "Lnet/minecraft/core/net/PropertyManager;getBooleanProperty(Ljava/lang/String;Z)Z")
    @Expression("?.getBooleanProperty('allow-paradise', false)")
    @ModifyExpressionValue(method = "doTick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean disableAetherTravelTwo(boolean original, @Local WorldServer worldServer) {
        return original && (worldServer.dimension != AetherDimension.getAether() || this.propertyManager.getBooleanProperty("allow-aether", true));
    }

    @ModifyArg(method = "initWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/WorldServerMulti;<init>(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/core/world/save/LevelStorage;Ljava/lang/String;ILnet/minecraft/core/world/type/WorldType;JLnet/minecraft/server/world/WorldServer;)V"), index = 4)
    private WorldType fixWorldType(WorldType worldType, @Local Dimension dimID) {
        WorldType defaultWorldType = ((MinecraftServer) (Object) this).defaultWorldType;

        for (WorldTypeGroups.Group group : WorldTypeGroups.GROUPS) {
            if (group.get(Dimension.OVERWORLD).getLanguageKey().equals(defaultWorldType.getLanguageKey())) {
                WorldType correctType = group.get(dimID);
                if (correctType != null) {
                    return correctType;
                }
            }
        }

        return worldType;
    }
}
