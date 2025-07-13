package teamport.aether.mixin.accessors;


import net.minecraft.client.gui.hud.component.HudComponent;
import net.minecraft.client.gui.hud.component.HudComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;


@Mixin(value = HudComponents.class, remap = false)
public interface HudComponentsAccessor {
    @Accessor("components")
    List<HudComponent> getComponents();
}
