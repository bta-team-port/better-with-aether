package teamport.aether.mixin.accessors;

import net.minecraft.client.gui.hud.component.HudComponent;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.gui.hud.component.layout.LayoutSnap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = HudComponent.class, remap = false)
public interface HudComponentAccessor {

    @Accessor("layout")
    void setLayout(Layout layout);

}
