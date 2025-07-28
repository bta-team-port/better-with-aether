package teamport.aether.mixin.armor.player.neptune;

import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import teamport.aether.helper.ContainerHelper;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Player.class, remap = false)
public class PlayerMixinMining {

    @ModifyConstant(
            method = "getCurrentPlayerStrVsBlock",
            constant = @Constant(floatValue = 5.0F)
    )
    public float modifyWaterSlowdownConstant(float original) {
        Player player = (Player) (Object) this;
        if (ContainerHelper.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.NEPTUNE) < 5) {
            return original;
        }
        return 1.0F;
    }
}
