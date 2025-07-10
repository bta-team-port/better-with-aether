package teamport.aether.gui;


import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.HudComponent;
import net.minecraft.client.gui.hud.component.HudComponents;
import net.minecraft.client.gui.hud.component.layout.LayoutSnap;
import static net.minecraft.client.gui.hud.component.HudComponents.HEALTH_BAR;
import static net.minecraft.client.gui.hud.component.HudComponents.HOTBAR;

/**
 * This file is intended to initialize all gui/hud components, to keep AetherClient manageable
 * */
public class AetherHudComponents {
    public static HudComponent EXTRA_HEALTH_BAR;


    public static void registerHudComponents() {
        EXTRA_HEALTH_BAR = HudComponents.register(new ComponentExtraHealthBar("aetherExtraHealth", new LayoutSnap(HOTBAR, ComponentAnchor.TOP_LEFT, ComponentAnchor.BOTTOM_LEFT)));
    }

}
