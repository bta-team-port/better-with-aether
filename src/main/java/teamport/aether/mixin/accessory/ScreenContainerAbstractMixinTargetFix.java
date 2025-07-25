package teamport.aether.mixin.accessory;

import net.minecraft.client.gui.container.ScreenContainerAbstract;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ScreenContainerAbstract.class)
public class ScreenContainerAbstractMixinTargetFix {
    //TODO implement a mixin into clickInventory that changes local variable target based on if its an accessory
}