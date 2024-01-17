package bta.aether;

import bta.aether.entity.AetherEntities;
import bta.aether.entity.ArrowFlamingRenderer;
import bta.aether.entity.EntityArrowFlaming;
import bta.aether.entity.EntityFallingGravitite;
import bta.aether.entity.EntityGoldenDart;
import bta.aether.entity.GoldenDartRenderer;
import bta.aether.gui.components.ComponentBossBar;
import bta.aether.gui.components.ComponentJumpBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.*;
import net.minecraft.client.render.entity.FallingSandRenderer;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.SoundHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;

public class AetherClient implements ClientStartEntrypoint {
    public static HudComponent BOSS_BAR = HudComponents.register(new ComponentBossBar("aether.boss.bar", new AbsoluteLayout(0.5f, 0.0f, ComponentAnchor.TOP_CENTER)));
    public static HudComponent JUMP_BAR = HudComponents.register(new ComponentJumpBar("aether.jump.bar", new SnapLayout(HudComponents.ARMOR_BAR, ComponentAnchor.TOP_RIGHT, ComponentAnchor.BOTTOM_RIGHT)));

    @Override
    public void beforeClientStart() {
        EntityHelper.Client.assignEntityRenderer(EntityFallingGravitite.class, new FallingSandRenderer());
        EntityHelper.Client.assignEntityRenderer(EntityArrowFlaming.class, new ArrowFlamingRenderer());
        EntityHelper.Client.assignEntityRenderer(EntityGoldenDart.class, new GoldenDartRenderer());
        new AetherEntities().initializeModels();
        SoundHelper.Client.addSound("aether", "portal.ogg");
        SoundHelper.Client.addSound("aether", "travel.ogg");
        SoundHelper.Client.addSound("aether", "trigger.ogg");

        SoundHelper.Client.addSound("aether", "achievementBronze.ogg");
        SoundHelper.Client.addSound("aether", "achievementSilver.ogg");
        SoundHelper.Client.addSound("aether", "achievementGen.ogg");

        SoundHelper.Client.addStreaming("aether", "AetherTune.ogg");
        SoundHelper.Client.addStreaming("aether", "AMorningWish.ogg");
        SoundHelper.Client.addStreaming("aether", "AscendingDawn.ogg");
        SoundHelper.Client.addMusic("aether", "aether1.ogg");
        SoundHelper.Client.addMusic("aether", "aether2.ogg");
        SoundHelper.Client.addMusic("aether", "aether3.ogg");
        SoundHelper.Client.addMusic("aether", "aether4.ogg");
        SoundHelper.Client.addMusic("aether", "aether5.ogg");
        SoundHelper.Client.addMusic("aether", "aether6.ogg");
        SoundHelper.Client.addMusic("aether", "aether7.ogg");
        SoundHelper.Client.addMusic("aether", "aether8.ogg");
        SoundHelper.Client.addMusic("aether", "aether9.ogg");

        SoundHelper.Client.addMusic("aether", "sliderboss.ogg");
        SoundHelper.Client.addMusic("aether", "fireboss.ogg");

        SoundHelper.Client.addMusic("aether", "menu.ogg");
        SoundHelper.Client.addMusic("aether", "menunether.ogg");

    }

    @Override
    public void afterClientStart() {
    }
    public static Minecraft getMinecraft(){
        return Minecraft.getMinecraft(Minecraft.class);
    }
}
