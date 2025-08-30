package teamport.aether.mixin.gui.hud;


import net.minecraft.client.gui.hud.component.HudComponents;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.AetherClient;

@Mixin(value = HudComponents.class)
public class HudComponentEntrypointMixin {
    // this is silly, and, if you are a BTA dev, you should feel bad. >:(
    static { AetherClient.registerHUDComponents(); }
}
