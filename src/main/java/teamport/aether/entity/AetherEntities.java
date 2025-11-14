package teamport.aether.entity;

import net.minecraft.core.util.collection.NamespaceID;
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
import teamport.aether.entity.tile.*;
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

    public static String entityKey(String string) {
        return MOD_ID + ".entity." + string;
    }

    public static void initializeEntities() {
        EntityHelper.createEntity(MobSentry.class, NamespaceID.getPermanent(MOD_ID, "sentry"), entityKey("sentry"));
        EntityHelper.createEntity(MobZephyr.class, NamespaceID.getPermanent(MOD_ID, "zephyr"), entityKey("zephyr"));
        EntityHelper.createEntity(MobAechorPlant.class, NamespaceID.getPermanent(MOD_ID, "aechorplant"), entityKey("aechorplant"));
        EntityHelper.createEntity(MobMimic.class, NamespaceID.getPermanent(MOD_ID, "mimic"), entityKey("mimic"));
        EntityHelper.createEntity(MobSwet.class, NamespaceID.getPermanent(MOD_ID, "swet"), entityKey("swet"));
        EntityHelper.createEntity(MobSwetGold.class, NamespaceID.getPermanent(MOD_ID, "swet_gold"), entityKey("swet_gold"));
        EntityHelper.createEntity(MobCockatrice.class, NamespaceID.getPermanent(MOD_ID, "cockatrice"), entityKey("cockatrice"));
        EntityHelper.createEntity(MobValkyrie.class, NamespaceID.getPermanent(MOD_ID, "valkyrie"), entityKey("valkyrie"));
        EntityHelper.createEntity(MobWhirly.class, NamespaceID.getPermanent(MOD_ID, "whirly"), entityKey("whirly"));
        EntityHelper.createEntity(MobFireMinion.class, NamespaceID.getPermanent(MOD_ID, "fire_minion"), entityKey("minion_fire"));

        EntityHelper.createEntity(MobBossSlider.class, NamespaceID.getPermanent(MOD_ID, "boss_slider"), entityKey("boss_slider"));
        EntityHelper.createEntity(MobBossValkyrie.class, NamespaceID.getPermanent(MOD_ID, "boss_valkyrie"), entityKey("boss_valkyrie"));
        EntityHelper.createEntity(MobBossSunspirit.class, NamespaceID.getPermanent(MOD_ID, "boss_sunspirit"), entityKey("boss_sunspirit"));

        EntityHelper.createEntity(MobSheepuff.class, NamespaceID.getPermanent(MOD_ID, "sheepuff"), entityKey("sheepuff"));
        EntityHelper.createEntity(MobPhow.class, NamespaceID.getPermanent(MOD_ID, "phow"), entityKey("phow"));
        EntityHelper.createEntity(MobPhyg.class, NamespaceID.getPermanent(MOD_ID, "phyg"), entityKey("phyg"));
        EntityHelper.createEntity(MobAerwhale.class, NamespaceID.getPermanent(MOD_ID, "aerwhale"), entityKey("aerwhale"));
        EntityHelper.createEntity(MobAerbunny.class, NamespaceID.getPermanent(MOD_ID, "aerbunny"), entityKey("aerbunny"));

        EntityHelper.createEntity(MobMoaBlue.class, NamespaceID.getPermanent(MOD_ID, "moa_blue"), entityKey("moa_blue"));
        EntityHelper.createEntity(MobMoaWhite.class, NamespaceID.getPermanent(MOD_ID, "moa_white"), entityKey("moa_white"));
        EntityHelper.createEntity(MobMoaBlack.class, NamespaceID.getPermanent(MOD_ID, "moa_black"), entityKey("moa_black"));


        EntityHelper.createEntity(EntityParachute.class, NamespaceID.getPermanent(MOD_ID, "parachute"), entityKey("parachute"));
        EntityHelper.createEntity(EntityParachuteGold.class, NamespaceID.getPermanent(MOD_ID, "parachute_gold"), entityKey("parachute_gold"));

        EntityHelper.createEntity(EntityFloatingBlock.class, NamespaceID.getPermanent(MOD_ID, "floating_block"), entityKey("floating_block"));


        EntityHelper.createTileEntity(TileEntityEnchanter.class, NamespaceID.getPermanent(MOD_ID, "enchanter"));
        EntityHelper.createTileEntity(TileEntityFreezer.class, NamespaceID.getPermanent(MOD_ID, "freezer"));
        EntityHelper.createTileEntity(TileEntityIncubator.class, NamespaceID.getPermanent(MOD_ID, "incubator"));
        EntityHelper.createTileEntity(TileEntitySignSkyroot.class, NamespaceID.getPermanent(MOD_ID, "sign_skyroot"));
        EntityHelper.createTileEntity(TileEntityMimic.class, NamespaceID.getPermanent(MOD_ID, "chest_mimic"));

        EntityHelper.createEntity(ProjectileKnifeLightning.class, NamespaceID.getPermanent(MOD_ID, "knife_lightning"), entityKey("knife_lightning"));
        EntityHelper.createEntity(ProjectileDart.class, NamespaceID.getPermanent(MOD_ID, "dart"), entityKey("dart"));
        EntityHelper.createEntity(ProjectileNeedle.class, NamespaceID.getPermanent(MOD_ID, "needle"), entityKey("needle"));
        EntityHelper.createEntity(ProjectileArrowFlaming.class, NamespaceID.getPermanent(MOD_ID, "arrow_flaming"), entityKey("arrow_flaming"));
        EntityHelper.createEntity(ProjectileHammerHead.class, NamespaceID.getPermanent(MOD_ID, "hammer_head"), entityKey("hammer_head"));
        EntityHelper.createEntity(ProjectileWindball.class, NamespaceID.getPermanent(MOD_ID, "windball"), entityKey("windball"));
        EntityHelper.createEntity(ProjectileElementFire.class, NamespaceID.getPermanent(MOD_ID, "projectile_fire"), entityKey("projectile_fire"));
        EntityHelper.createEntity(ProjectileElementIce.class, NamespaceID.getPermanent(MOD_ID, "projectile_ice"), entityKey("projectile_ice"));
        EntityHelper.createEntity(ProjectileElementLightning.class, NamespaceID.getPermanent(MOD_ID, "projectile_lightning"), entityKey("projectile_lightning"));
    }
}
