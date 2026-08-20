package teamport.aether.mixin.accessors;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.TooltipElement;
import net.minecraft.client.gui.container.ScreenContainerAbstract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Environment(EnvType.CLIENT)
@Mixin(ScreenContainerAbstract.class)
public interface ScreenContainerAbstractAccessor {
    @Invoker
    int invokeGetSlotId(int x, int y);

    @Accessor
    TooltipElement getTooltipElement();
}
