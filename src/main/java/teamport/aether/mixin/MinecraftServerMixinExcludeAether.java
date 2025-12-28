package teamport.aether.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.net.PropertyManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.world.AetherDimension;

@Mixin(value = MinecraftServer.class, remap = false)
public abstract class MinecraftServerMixinExcludeAether {

    //TODO Finish this after release
    @Shadow
    public PropertyManager propertyManager;

    @Definition(id = "dimension", field = "Lnet/minecraft/server/world/WorldServer;dimension:Lnet/minecraft/core/world/Dimension;")
    @Definition(id = "PARADISE", field = "Lnet/minecraft/core/world/Dimension;PARADISE:Lnet/minecraft/core/world/Dimension;")
    @Expression("?.dimension != PARADISE")
    @ModifyExpressionValue(method = "initWorld", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean disableAetherGenerationOne(boolean original, @Local WorldServer worldServer){
        return original && (worldServer.dimension != AetherDimension.getAether() || this.propertyManager.getBooleanProperty("allow-aether", true));
    }
    @Definition(id = "getBooleanProperty", method = "Lnet/minecraft/core/net/PropertyManager;getBooleanProperty(Ljava/lang/String;Z)Z")
    @Expression("?.getBooleanProperty('allow-paradise', false)")
    @ModifyExpressionValue(method = "initWorld", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean disableAetherGenerationTwo(boolean original, @Local WorldServer worldServer){
        return original && (worldServer.dimension != AetherDimension.getAether() || this.propertyManager.getBooleanProperty("allow-aether", true));
    }
    @Definition(id = "dimension", field = "Lnet/minecraft/server/world/WorldServer;dimension:Lnet/minecraft/core/world/Dimension;")
    @Definition(id = "PARADISE", field = "Lnet/minecraft/core/world/Dimension;PARADISE:Lnet/minecraft/core/world/Dimension;")
    @Expression("?.dimension != PARADISE")
    @ModifyExpressionValue(method = "doTick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean disableAetherTravelOne(boolean original, @Local WorldServer worldServer){
        return original && (worldServer.dimension != AetherDimension.getAether() || this.propertyManager.getBooleanProperty("allow-aether", true));
    }
    @Definition(id = "getBooleanProperty", method = "Lnet/minecraft/core/net/PropertyManager;getBooleanProperty(Ljava/lang/String;Z)Z")
    @Expression("?.getBooleanProperty('allow-paradise', false)")
    @ModifyExpressionValue(method = "doTick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean disableAetherTravelTwo(boolean original, @Local WorldServer worldServer){
        return original && (worldServer.dimension != AetherDimension.getAether() || this.propertyManager.getBooleanProperty("allow-aether", true));
    }
}
