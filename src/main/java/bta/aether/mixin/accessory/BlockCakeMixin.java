package bta.aether.mixin.accessory;

import bta.aether.accessory.API.HealthHelper;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.BlockCake;
import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BlockCake.class, remap = false)
public class BlockCakeMixin {

    @ModifyExpressionValue(method = "eatCakeSlice", at=@At(value = "CONSTANT", args = "intValue=20"))
    private int modifyHardcodedHealth(int player_health,  @Local EntityPlayer player) {
        return HealthHelper.getTotalHealth(player);
    }

}
