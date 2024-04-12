package bta.aether;

import bta.aether.block.AetherBlocks;
import bta.aether.entity.*;
import bta.aether.entity.projectiles.*;
import bta.aether.entity.renderer.aetherArrowRenderer;
import bta.aether.gui.components.ComponentBossBar;
import bta.aether.gui.components.ComponentJumpBar;
import bta.aether.item.AetherItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.gui.hud.*;
import net.minecraft.client.render.entity.FallingSandRenderer;
import net.minecraft.client.render.entity.SnowballRenderer;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.SoundHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.ItemCoords;
import turniplabs.halplibe.util.TextureHandler;

import static turniplabs.halplibe.helper.TextureHelper.registeredItemTextures;
import static turniplabs.halplibe.helper.TextureHelper.textureHandlers;

public class AetherClient implements ClientStartEntrypoint {
    public static HudComponent BOSS_BAR = HudComponents.register(new ComponentBossBar("aether.boss.bar", new AbsoluteLayout(0.5f, 0.0f, ComponentAnchor.TOP_CENTER)));
    public static HudComponent JUMP_BAR = HudComponents.register(new ComponentJumpBar("aether.jump.bar", new SnapLayout(HudComponents.ARMOR_BAR, ComponentAnchor.TOP_RIGHT, ComponentAnchor.BOTTOM_RIGHT)));

    @Override
    public void beforeClientStart() {
        EntityHelper.Client.assignEntityRenderer(EntityFallingGravitite.class, new FallingSandRenderer());
        EntityHelper.Client.assignEntityRenderer(EntityLightningKnife.class, new SnowballRenderer(AetherItems.toolKnifeLightning.getIconIndex(new ItemStack(AetherItems.toolKnifeLightning))));
        EntityHelper.Client.assignEntityRenderer(EntityArrowFlaming.class, new aetherArrowRenderer("/assets/aether/other/FlamingArrows.png"));
        EntityHelper.Client.assignEntityRenderer(EntityGoldenDart.class, new aetherArrowRenderer("/assets/aether/mobs/entitygoldendart.png"));
        EntityHelper.Client.assignEntityRenderer(EntityPoisonDart.class, new aetherArrowRenderer("/assets/aether/mobs/entitypoisondart.png"));
        EntityHelper.Client.assignEntityRenderer(EntityEnchantedDart.class, new aetherArrowRenderer("/assets/aether/mobs/entityenchanteddart.png"));
        EntityHelper.Client.assignEntityRenderer(EntityZephyrSnowball.class, new SnowballRenderer(Item.ammoSnowball.getIconIndex(new ItemStack(Item.ammoSnowball))));

        // cursed, I know.
        final String hammerHeadTexture = "/assets/aether/other/NotchWave.png";
        int[] newCoords = ItemCoords.nextCoords();
        registeredItemTextures.put(Aether.MOD_ID + ":" + hammerHeadTexture, newCoords);
        textureHandlers.add(new TextureHandler("/gui/items.png", hammerHeadTexture, Block.texCoordToIndex(newCoords[0], newCoords[1]), 16, 1));
        EntityHelper.Client.assignEntityRenderer(EntityHammerHead.class, new SnowballRenderer(Block.texCoordToIndex(newCoords[0], newCoords[1])));

        new AetherEntities().initializeModels();

        MobInfoRegistry.register(EntityPhyg.class, "aether.phyg.name", "aether.phyg.desc",
                10, 100, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(new ItemStack(Item.foodPorkchopRaw),
                        1.0f, 0 ,2), new MobInfoRegistry.MobDrop(new ItemStack(Item.featherChicken),
                        1.0f, 0, 2)});

        MobInfoRegistry.register(EntityPhow.class, "aether.phow.name", "aether.phow.desc",
                10, 100, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(new ItemStack(Item.leather),
                        1.0f, 0 ,2), new MobInfoRegistry.MobDrop(new ItemStack(Item.featherChicken),
                        1.0f, 0, 2)});

        MobInfoRegistry.register(EntitySheepuff.class, "aether.sheepuff.name", "aether.sheepuff.desc",
                10, 100, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(new ItemStack(Block.wool),
                        1.0f, 0 ,2)});

        MobInfoRegistry.register(EntitySentry.class, "aether.sentry.name", "aether.sentry.desc",
                10, 100, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(new ItemStack(AetherBlocks.stoneCarved),
                        1.0f, 1 ,2), new MobInfoRegistry.MobDrop(new ItemStack(AetherBlocks.stoneCarvedLight),
                        1.0f, 1, 2)});

        MobInfoRegistry.register(EntityMimic.class, "aether.mimic.name", "aether.mimic.desc",
                10, 100, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(new ItemStack(AetherBlocks.chestSkyroot),
                        1.0f, 1 ,1), new MobInfoRegistry.MobDrop(new ItemStack(Block.chestPlanksOak),
                        1.0f, 1, 1)});

        MobInfoRegistry.register(EntityBossSlider.class, "aether.boss.slider.name", "aether.boss.slider.desc",
                500, 10000, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(new ItemStack(AetherItems.keyBronze),
                        0.66f * 0.8f, 1 ,1)});

        SoundHelper.Client.addSound("aether", "portal.ogg");
        SoundHelper.Client.addSound("aether", "travel.ogg");
        SoundHelper.Client.addSound("aether", "trigger.ogg");

        SoundHelper.Client.addSound("aether", "achievement_bronze.ogg");
        SoundHelper.Client.addSound("aether", "achievement_silver.ogg");
        SoundHelper.Client.addSound("aether", "achievement_gen.ogg");

        SoundHelper.Client.addSound("aether", "life_shard_chime.ogg");
        SoundHelper.Client.addSound("aether", "life_shard_chime_final.ogg");

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
