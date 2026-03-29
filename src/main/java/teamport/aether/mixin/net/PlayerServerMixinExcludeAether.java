package teamport.aether.mixin.net;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.world.Dimension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.world.AetherDimension;

@Environment(EnvType.SERVER)
@Mixin(value = PlayerServer.class)
public abstract class PlayerServerMixinExcludeAether {
    @Shadow
    public MinecraftServer mcServer;

    @Definition(id = "paradiseAllowed", local = @Local(type = boolean.class, ordinal = 1))
    @Expression("paradiseAllowed")
    @ModifyExpressionValue(method = "onUpdateEntity", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean checkForValidDimensionDenyAether(boolean original, @Share("deny") LocalBooleanRef deny, @Local Dimension targetDim) {
        if (original && targetDim == Dimension.PARADISE) {
            deny.set(false);
            return true;
        }
        deny.set(mcServer.propertyManager.getBooleanProperty("allow-aether", true) && targetDim == AetherDimension.getAether());
        return original || deny.get();
    }

    @Definition(id = "PARADISE", field = "Lnet/minecraft/core/world/Dimension;PARADISE:Lnet/minecraft/core/world/Dimension;")
    @Expression("? == PARADISE")
    @ModifyExpressionValue(method = "onUpdateEntity", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 0))
    private boolean checkForValidDimensionFirstHalf(boolean original, @Share("deny") LocalBooleanRef deny, @Local(ordinal = 1) boolean paradiseAllowed) {
        return original && paradiseAllowed || deny.get();
    }

    @Definition(id = "PARADISE", field = "Lnet/minecraft/core/world/Dimension;PARADISE:Lnet/minecraft/core/world/Dimension;")
    @Expression("? != PARADISE")
    @ModifyExpressionValue(method = "onUpdateEntity", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 1))
    private boolean checkForValidDimensionSecondHalf(boolean original, @Local Dimension targetDim) {
        return original && targetDim != AetherDimension.getAether();
    }

}
