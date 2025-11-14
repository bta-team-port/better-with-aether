package teamport.aether.mixin.accessors;

import net.minecraft.client.gui.container.ScreenContainerAbstract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = ScreenContainerAbstract.class, remap = false)
public interface ScreenContainerAbstractAccessor {
    @Invoker
    int invokeGetSlotId(int x, int y);
}
