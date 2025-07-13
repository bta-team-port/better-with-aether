package teamport.aether.mixin;


import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.HudComponent;
import net.minecraft.client.gui.hud.component.HudComponents;
import net.minecraft.client.gui.hud.component.layout.LayoutSnap;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.gui.ComponentExtraHealthBar;

import static teamport.aether.AetherMod.EXTRA_HEALTH;

@Mixin(value = HudComponents.class)
public class HudComponentsMixinExtraHealth {

    static {
        int extraBars = (int) Math.ceil(EXTRA_HEALTH / 20.0f);
        HudComponent previousComponent = HudComponents.HEALTH_BAR;
        for(int i = 0; i< extraBars; i++){
            previousComponent = HudComponents.register(
                    new ComponentExtraHealthBar(
                            "aetherExtraHealth_bar" +  i,
                            new LayoutSnap(
                                    previousComponent,
                                    ComponentAnchor.TOP_LEFT,
                                    ComponentAnchor.BOTTOM_LEFT), i + 1));
        }

        // in case we ever decide to move the oxygena nd firebar above armor
//        HudComponent oxygen = HudComponents.INSTANCE.getComponent("oxygen_bar");
//        HudComponents.INSTANCE.getComponents().remove(oxygen);
//        HudComponents.register(new HudComponentOxygenBar("oxygen_bar", new LayoutSnap(HudComponents.ARMOR_BAR, ComponentAnchor.TOP_LEFT, ComponentAnchor.BOTTOM_LEFT)));
    }

}
