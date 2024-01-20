package bta.aether;

import bta.aether.entity.*;
import bta.aether.entity.renderer.aetherArrowRenderer;
import bta.aether.gui.components.ComponentBossBar;
import bta.aether.gui.components.ComponentJumpBar;
import bta.aether.item.AetherItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.*;
import net.minecraft.client.render.entity.FallingSandRenderer;
import net.minecraft.client.render.entity.SnowballRenderer;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.SoundHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;

public class AetherClient implements ClientStartEntrypoint {
    public static HudComponent BOSS_BAR = HudComponents.register(new ComponentBossBar("aether.boss.bar", new AbsoluteLayout(0.5f, 0.0f, ComponentAnchor.TOP_CENTER)));
    public static HudComponent JUMP_BAR = HudComponents.register(new ComponentJumpBar("aether.jump.bar", new SnapLayout(HudComponents.ARMOR_BAR, ComponentAnchor.TOP_RIGHT, ComponentAnchor.BOTTOM_RIGHT)));

    @Override
    public void beforeClientStart() {
        EntityHelper.Client.assignEntityRenderer(EntityFallingGravitite.class, new FallingSandRenderer());
        EntityHelper.Client.assignEntityRenderer(EntityLightningKnife.class, new SnowballRenderer(AetherItems.toolKnifeLightning.getIconIndex(new ItemStack(AetherItems.toolKnifeLightning))));
        EntityHelper.Client.assignEntityRenderer(EntityArrowFlaming.class, new aetherArrowRenderer("/assets/aether/other/FlamingArrows.png"));
        EntityHelper.Client.assignEntityRenderer(EntityGoldenDart.class, new aetherArrowRenderer("/assets/aether/mobs/entitygoldendart.png"));

        new AetherEntities().initializeModels();
        SoundHelper.Client.addSound("aether", "portal.ogg");
        SoundHelper.Client.addSound("aether", "travel.ogg");
        SoundHelper.Client.addSound("aether", "trigger.ogg");

        SoundHelper.Client.addSound("aether", "achievement_bronze.ogg");
        SoundHelper.Client.addSound("aether", "achievement_silver.ogg");
        SoundHelper.Client.addSound("aether", "achievement_gen.ogg");

        SoundHelper.Client.addStreaming("aether", "aether_tune.ogg");
        SoundHelper.Client.addStreaming("aether", "a_morning_wish.ogg");
        SoundHelper.Client.addStreaming("aether", "ascending_dawn.ogg");
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
