package teamport.aether.mixin.gui.screens;

import net.minecraft.client.gui.TooltipElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.gui.container.ScreenContainerAbstract;

@Mixin(value = ScreenContainerAbstract.class, remap = false)
public interface ScreenContainerAbstractAccessor {
    @Accessor
    TooltipElement getTooltipElement();
}
