package teamport.aether.mixin.entity.player;

import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import teamport.aether.accessory.api.ContainerHelper;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Player.class, remap = false)
public class NeptuneArmorMining {

    @ModifyConstant(
            method = "getCurrentPlayerStrVsBlock",
            constant = @Constant(floatValue = 5.0F)
    )
    private float modifyWaterSlowdownConstant(float original) {
        Player player = (Player) (Object) this;
        if (ContainerHelper.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.NEPTUNE) < 4) {
            return original;
        }
        return 1.0F;
    }
}
