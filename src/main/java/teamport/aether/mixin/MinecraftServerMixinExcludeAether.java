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

    @Shadow
    public PropertyManager propertyManager;

    @Definition(id = "dimension", field = "Lnet/minecraft/server/world/WorldServer;dimension:Lnet/minecraft/core/world/Dimension;")
    @Definition(id = "NETHER", field = "Lnet/minecraft/core/world/Dimension;NETHER:Lnet/minecraft/core/world/Dimension;")
    @Expression("?.dimension != NETHER")
    @ModifyExpressionValue(method = "initWorld", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean disableAetherGeneration(boolean origin, @Local WorldServer worldServer){
        return worldServer.dimension != AetherDimension.getAether() || this.propertyManager.getBooleanProperty("allow-aether", true) && origin;
    }


    @Definition(id = "dimension", field = "Lnet/minecraft/server/world/WorldServer;dimension:Lnet/minecraft/core/world/Dimension;")
    @Definition(id = "NETHER", field = "Lnet/minecraft/core/world/Dimension;NETHER:Lnet/minecraft/core/world/Dimension;")
    @Expression("?.dimension != NETHER")
    @ModifyExpressionValue(method = "doTick", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean disableAetherTravel(boolean origin, @Local WorldServer worldServer){
        return worldServer.dimension != AetherDimension.getAether() || this.propertyManager.getBooleanProperty("allow-aether", true) && origin;
    }


}
