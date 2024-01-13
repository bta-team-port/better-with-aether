package bta.aether;

import bta.aether.gui.components.ComponentBossBar;
import net.minecraft.client.gui.hud.AbsoluteLayout;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.HudComponent;
import net.minecraft.client.gui.hud.HudComponents;
import turniplabs.halplibe.util.ClientStartEntrypoint;

public class AetherClient implements ClientStartEntrypoint {
    public static HudComponent BOSS_BAR = HudComponents.register(new ComponentBossBar("aether.boss.bar", new AbsoluteLayout(0.5f, 0.0f, ComponentAnchor.TOP_CENTER)));

    @Override
    public void beforeClientStart() {

    }

    @Override
    public void afterClientStart() {

    }
}
