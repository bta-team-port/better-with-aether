package teamport.aether.entity;

import net.minecraft.core.util.collection.NamespaceID;
import teamport.aether.block.entity.*;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;
import teamport.aether.entity.animal.aerwhale.MobAerwhale;
import teamport.aether.entity.animal.moa.MobMoaBlack;
import teamport.aether.entity.animal.moa.MobMoaBlue;
import teamport.aether.entity.animal.moa.MobMoaWhite;
import teamport.aether.entity.animal.phow.MobPhow;
import teamport.aether.entity.animal.phyg.MobPhyg;
import teamport.aether.entity.animal.sheepuff.MobSheepuff;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.boss.valkyrie.queen.MobBossValkyrie;
import teamport.aether.entity.floating_block.EntityFloatingBlock;
import teamport.aether.entity.monster.aechorplant.MobAechorPlant;
import teamport.aether.entity.monster.cockatrice.MobCockatrice;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.entity.monster.mimic.MobMimic;
import teamport.aether.entity.monster.sentry.MobSentry;
import teamport.aether.entity.monster.swet.MobSwet;
import teamport.aether.entity.monster.swet.MobSwetGold;
import teamport.aether.entity.monster.valkyrie.MobValkyrie;
import teamport.aether.entity.monster.whirly.MobWhirly;
import teamport.aether.entity.monster.zephyr.MobZephyr;
import teamport.aether.entity.projectile.*;
import teamport.aether.entity.vehicle.parachute.EntityParachute;
import teamport.aether.entity.vehicle.parachute.EntityParachuteGold;
import turniplabs.halplibe.helper.EntityHelper;

import static teamport.aether.AetherMod.MOD_ID;

public final class AetherEntities {
    private static boolean hasInit = false;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            initializeEntities();
        }

    }

    public static void initializeEntities() {
        EntityHelper.createEntity(MobSentry.class, NamespaceID.getPermanent(MOD_ID, "sentry"), "guidebook.section.mob.sentry.name");
        EntityHelper.createEntity(MobZephyr.class, NamespaceID.getPermanent(MOD_ID, "zephyr"), "guidebook.section.mob.zephyr.name");
        EntityHelper.createEntity(MobAechorPlant.class, NamespaceID.getPermanent(MOD_ID, "aechorplant"), "guidebook.section.mob.aechorplant.name");
        EntityHelper.createEntity(MobMimic.class, NamespaceID.getPermanent(MOD_ID, "mimic"), "guidebook.section.mob.mimic.name");
        EntityHelper.createEntity(MobSwet.class, NamespaceID.getPermanent(MOD_ID, "swet"), "guidebook.section.mob.swet.name");
        EntityHelper.createEntity(MobSwetGold.class, NamespaceID.getPermanent(MOD_ID, "swet_gold"), "guidebook.section.mob.swet.gold.name");
        EntityHelper.createEntity(MobCockatrice.class, NamespaceID.getPermanent(MOD_ID, "cockatrice"), "guidebook.section.mob.cockatrice.name");
        EntityHelper.createEntity(MobValkyrie.class, NamespaceID.getPermanent(MOD_ID, "valkyrie"), "guidebook.section.mob.valkyrie.name");
        EntityHelper.createEntity(MobWhirly.class, NamespaceID.getPermanent(MOD_ID, "whirly"), "guidebook.section.mob.whirly.name");
        EntityHelper.createEntity(MobFireMinion.class, NamespaceID.getPermanent(MOD_ID, "fire_minion"), "guidebook.section.mob.fireminion.name");

        EntityHelper.createEntity(MobBossSlider.class, NamespaceID.getPermanent(MOD_ID, "boss_slider"), "guidebook.section.mob.slider.name");
        EntityHelper.createEntity(MobBossValkyrie.class, NamespaceID.getPermanent(MOD_ID, "boss_valkyrie"), "guidebook.section.mob.valkyrie.queen.name");
        EntityHelper.createEntity(MobBossSunspirit.class, NamespaceID.getPermanent(MOD_ID, "boss_sunspirit"), "guidebook.section.mob.sunspirit.name");

        EntityHelper.createEntity(MobSheepuff.class, NamespaceID.getPermanent(MOD_ID, "sheepuff"), "guidebook.section.mob.sheepuff.name");
        EntityHelper.createEntity(MobPhow.class, NamespaceID.getPermanent(MOD_ID, "phow"), "guidebook.section.mob.phow.name");
        EntityHelper.createEntity(MobPhyg.class, NamespaceID.getPermanent(MOD_ID, "phyg"), "guidebook.section.mob.phyg.name");
        EntityHelper.createEntity(MobAerwhale.class, NamespaceID.getPermanent(MOD_ID, "aerwhale"), "guidebook.section.mob.aerwhale.name");
        EntityHelper.createEntity(MobAerbunny.class, NamespaceID.getPermanent(MOD_ID, "aerbunny"), "guidebook.section.mob.aerbunny.name");

        EntityHelper.createEntity(MobMoaBlue.class, NamespaceID.getPermanent(MOD_ID, "moa_blue"), "guidebook.section.mob.moa.blue.name");
        EntityHelper.createEntity(MobMoaWhite.class, NamespaceID.getPermanent(MOD_ID, "moa_white"), "guidebook.section.mob.moa.white.name");
        EntityHelper.createEntity(MobMoaBlack.class, NamespaceID.getPermanent(MOD_ID, "moa_black"), "guidebook.section.mob.moa.black.name");


        EntityHelper.createEntity(EntityParachute.class, NamespaceID.getPermanent(MOD_ID, "parachute"), null);
        EntityHelper.createEntity(EntityParachuteGold.class, NamespaceID.getPermanent(MOD_ID, "parachute_gold"), null);

        EntityHelper.createEntity(EntityFloatingBlock.class, NamespaceID.getPermanent(MOD_ID, "floating_block"), null);


        EntityHelper.createTileEntity(TileEntityEnchanter.class, NamespaceID.getPermanent(MOD_ID, "enchanter"));
        EntityHelper.createTileEntity(TileEntityFreezer.class, NamespaceID.getPermanent(MOD_ID, "freezer"));
        EntityHelper.createTileEntity(TileEntityIncubator.class, NamespaceID.getPermanent(MOD_ID, "incubator"));
        EntityHelper.createTileEntity(TileEntitySignSkyroot.class, NamespaceID.getPermanent(MOD_ID, "sign_skyroot"));
        EntityHelper.createTileEntity(TileEntityMimic.class, NamespaceID.getPermanent(MOD_ID, "chest_mimic"));

        EntityHelper.createEntity(ProjectileKnifeLightning.class, NamespaceID.getPermanent(MOD_ID, "knife_lightning"), null);
        EntityHelper.createEntity(ProjectileDart.class, NamespaceID.getPermanent(MOD_ID, "dart"), null);
        EntityHelper.createEntity(ProjectileNeedle.class, NamespaceID.getPermanent(MOD_ID, "needle"), null);
        EntityHelper.createEntity(ProjectileArrowFlaming.class, NamespaceID.getPermanent(MOD_ID, "arrow_flaming"), null);
        EntityHelper.createEntity(ProjectileHammerHead.class, NamespaceID.getPermanent(MOD_ID, "hammer_head"), null);
        EntityHelper.createEntity(ProjectileWindball.class, NamespaceID.getPermanent(MOD_ID, "windball"), null);
        EntityHelper.createEntity(ProjectileElementFire.class, NamespaceID.getPermanent(MOD_ID, "projectile_fire"), null);
        EntityHelper.createEntity(ProjectileElementIce.class, NamespaceID.getPermanent(MOD_ID, "projectile_ice"), null);
        EntityHelper.createEntity(ProjectileElementLightning.class, NamespaceID.getPermanent(MOD_ID, "projectile_lightning"), null);
    }
}
