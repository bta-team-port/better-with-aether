package teamport.aether.mixins.mixin.gui.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.component.HudComponents;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.AetherClient;

@Environment(EnvType.CLIENT)
@Mixin(HudComponents.class)
public abstract class HudComponentEntrypointMixin {
    // If you don't have this run at the point of creation of the hud component class, the reset button just won't work.
    static {
        AetherClient.registerHUDComponents();
    }
}
