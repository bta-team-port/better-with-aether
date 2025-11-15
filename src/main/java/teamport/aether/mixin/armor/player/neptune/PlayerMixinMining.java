package teamport.aether.mixin.armor.player.neptune;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.helper.ContainerHelper;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixinMining {
    @ModifyExpressionValue(method = "getCurrentPlayerStrVsBlock", at = @At(value = "CONSTANT", args = "floatValue=5.0F"))
    private float modifyWaterSlowdownConstant(float original) {
        Player player = (Player) (Object) this;
        if (ContainerHelper.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.NEPTUNE) < 5) {
            return original;
        }
        return 1.0F;
    }
}
