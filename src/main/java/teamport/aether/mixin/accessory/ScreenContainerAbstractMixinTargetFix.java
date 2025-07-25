package teamport.aether.mixin.accessory;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.container.ScreenContainerAbstract;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @implNote
 * So this hurts. Unlike the enchanter, freezer and incubator we cannot
 * resolve target by having changing inheritance for MenuInventory.
 * As such we have to mix into this class.
 */
@Mixin(value = ScreenContainerAbstract.class)
public class ScreenContainerAbstractMixinTargetFix {


    /**
     * @implNote This function resolve what slot to target. This is done in a stack of if-else.
     * This mixin is to ensure the correct slot is targeted when an accessory is clicked.
     */

//    @ModifyVariable(
//            method = "clickInventory",
//            at = @At(
//            )
//    )
}