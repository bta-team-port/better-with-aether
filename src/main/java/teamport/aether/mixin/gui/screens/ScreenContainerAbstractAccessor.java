package teamport.aether.mixin.gui.screens;

import net.minecraft.client.gui.TooltipElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.gui.container.ScreenContainerAbstract;

@Mixin(ScreenContainerAbstract.class)
public interface ScreenContainerAbstractAccessor {
    @Accessor("tooltipElement")
    TooltipElement getTooltipElement();
}