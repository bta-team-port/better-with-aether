package teamport.aether.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.block.BlockLogicPortal;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.Dimension;
import net.minecraft.server.entity.player.PlayerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.world.AetherDimension;

@Mixin(value = PlayerServer.class, remap = false)
public class PlayerServerMixinExcludeAether {

    @Definition(id = "PARADISE", field = "Lnet/minecraft/core/world/Dimension;NETHER:Lnet/minecraft/core/world/Dimension;")
    @Expression("? == PARADISE")
    @ModifyExpressionValue(method = "onUpdateEntity", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean checkForValidDimensionFirstHalf(boolean original){
        PlayerServer asThis = (PlayerServer) (Object) this;
        Dimension targetDim = ((BlockLogicPortal) Blocks.blocksList[asThis.portalID].getLogic()).targetDimension;
        return original || asThis.mcServer.propertyManager.getBooleanProperty("allow-nether", true) && targetDim == AetherDimension.getAether();
    }


    @Definition(id = "PARADISE", field = "Lnet/minecraft/core/world/Dimension;NETHER:Lnet/minecraft/core/world/Dimension;")
    @Expression("? != PARADISE")
    @ModifyExpressionValue(method = "onUpdateEntity", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean checkForValidDimensionSecondHalf(boolean original){
        PlayerServer asThis = (PlayerServer) (Object) this;
        Dimension targetDim = ((BlockLogicPortal) Blocks.blocksList[asThis.portalID].getLogic()).targetDimension;
        return original && targetDim == AetherDimension.getAether();
    }
}
