package teamport.aether.gui;

import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.HudComponents;
import net.minecraft.client.gui.hud.component.layout.LayoutSnap;

public class AetherHudComponent {

    public static void initializeHudClient(){
        HudComponents.register(new HudComponentExtraHealthBar("extra_health_bar", new LayoutSnap(HudComponents.HEALTH_BAR, ComponentAnchor.TOP_LEFT, ComponentAnchor.BOTTOM_LEFT)));
    }
}
