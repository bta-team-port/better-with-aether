package teamport.aether.mixin.net;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
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
@Mixin(PlayerServer.class)
public abstract class PlayerServerMixinExcludeAether {
    @Shadow
    public MinecraftServer mcServer;

    @Definition(
        id = "DRIFT",
        field = "Lnet/minecraft/core/world/Dimension;DRIFT:Lnet/minecraft/core/world/Dimension;"
    )
    @Expression("? != DRIFT")
    @ModifyExpressionValue(method = "onLivingUpdate()V", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean checkForValidDimensionDenyAether(boolean original, @Local Dimension targetDim) {
        return original && (targetDim != AetherDimension.getAether()
            || mcServer.propertyManager.getBooleanProperty("allow-aether", true));
    }

}
