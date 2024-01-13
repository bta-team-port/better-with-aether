package bta.aether;

import bta.aether.entity.AetherEntities;
import bta.aether.entity.ArrowFlamingRenderer;
import bta.aether.entity.EntityArrowFlaming;
import bta.aether.entity.EntityFallingGravitite;
import bta.aether.entity.EntityGoldenDart;
import bta.aether.entity.GoldenDartRenderer;
import bta.aether.gui.components.ComponentBossBar;
import net.minecraft.client.gui.hud.AbsoluteLayout;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.HudComponent;
import net.minecraft.client.gui.hud.HudComponents;
import net.minecraft.client.render.entity.FallingSandRenderer;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.SoundHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;

public class AetherClient implements ClientStartEntrypoint {
    public static HudComponent BOSS_BAR = HudComponents.register(new ComponentBossBar("aether.boss.bar", new AbsoluteLayout(0.5f, 0.0f, ComponentAnchor.TOP_CENTER)));

    @Override
    public void beforeClientStart() {
        EntityHelper.Client.assignEntityRenderer(EntityFallingGravitite.class, new FallingSandRenderer());
        EntityHelper.Client.assignEntityRenderer(EntityArrowFlaming.class, new ArrowFlamingRenderer());
        EntityHelper.Client.assignEntityRenderer(EntityGoldenDart.class, new GoldenDartRenderer());
        new AetherEntities().initializeModels();
        SoundHelper.Client.addSound("aether", "portal.ogg");
        SoundHelper.Client.addSound("aether", "travel.ogg");
        SoundHelper.Client.addSound("aether", "trigger.ogg");
    }

    @Override
    public void afterClientStart() {
    }
}
