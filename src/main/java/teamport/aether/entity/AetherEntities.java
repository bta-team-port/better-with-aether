package teamport.aether.entity;

import net.minecraft.core.util.collection.NamespaceID;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;
import teamport.aether.entity.animal.aerwhale.MobAerwhale;
import teamport.aether.entity.monster.cockatrice.MobCockatrice;
import teamport.aether.entity.monster.mimic.MobMimic;
import teamport.aether.entity.monster.whirly.MobWhirly;
import teamport.aether.entity.vehicle.minicloud.MobMinicloud;
import teamport.aether.entity.animal.moa.MobMoa;
import teamport.aether.entity.animal.moa.MobMoaBlack;
import teamport.aether.entity.animal.moa.MobMoaWhite;
import teamport.aether.entity.vehicle.parachute.EntityParachute;
import teamport.aether.entity.vehicle.parachute.EntityParachuteGold;
import teamport.aether.entity.animal.phow.MobPhow;
import teamport.aether.entity.animal.phyg.MobPhyg;
import teamport.aether.entity.projectile.*;
import teamport.aether.entity.monster.sentry.MobSentry;
import teamport.aether.entity.animal.sheepuff.MobSheepuff;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.monster.swet.MobSwet;
import teamport.aether.entity.boss.valkyrie.queen.MobBossValkyrie;
import teamport.aether.entity.monster.valkyrie.MobValkyrie;
import teamport.aether.entity.monster.zephyr.MobZephyr;
import teamport.aether.tile.TileEntityEnchanter;
import teamport.aether.tile.TileEntityFreezer;
import teamport.aether.tile.TileEntityIncubator;
import teamport.aether.tile.TileEntitySignSkyroot;
import turniplabs.halplibe.helper.EntityHelper;

import static teamport.aether.AetherMod.MOD_ID;

public final class AetherEntities {
    public static boolean hasInit = false;

    public static void init() {
        if(!hasInit){
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
        EntityHelper.createEntity(MobMimic.class, NamespaceID.getPermanent(MOD_ID, "mimic"), entityKey("mimic"));
        EntityHelper.createEntity(MobCockatrice.class, NamespaceID.getPermanent(MOD_ID, "cockatrice"), entityKey("cockatrice"));
        EntityHelper.createEntity(MobValkyrie.class, NamespaceID.getPermanent(MOD_ID, "valkyrie"), entityKey("valkyrie"));
        EntityHelper.createEntity(MobWhirly.class, NamespaceID.getPermanent(MOD_ID, "whirly"), entityKey("whirly"));

        EntityHelper.createEntity(MobBossSlider.class, NamespaceID.getPermanent(MOD_ID, "boss_slider"), entityKey("boss_slider"));
        EntityHelper.createEntity(MobBossValkyrie.class, NamespaceID.getPermanent(MOD_ID, "boss_valkyrie"), entityKey("boss_valkyrie"));
        EntityHelper.createEntity(MobBossSunspirit.class, NamespaceID.getPermanent(MOD_ID, "boss_sunspirit"), entityKey("boss_sunspirit"));

        EntityHelper.createEntity(MobSheepuff.class, NamespaceID.getPermanent(MOD_ID, "sheepuff"), entityKey("sheepuff"));
        EntityHelper.createEntity(MobPhow.class, NamespaceID.getPermanent(MOD_ID, "phow"), entityKey("phow"));
        EntityHelper.createEntity(MobPhyg.class, NamespaceID.getPermanent(MOD_ID, "phyg"), entityKey("phyg"));
        EntityHelper.createEntity(MobSwet.class, NamespaceID.getPermanent(MOD_ID, "swet"), entityKey("swet"));
        EntityHelper.createEntity(MobMoa.class, NamespaceID.getPermanent(MOD_ID, "moa"), entityKey("moa"));
        EntityHelper.createEntity(MobMoaWhite.class, NamespaceID.getPermanent(MOD_ID, "moa_white"), entityKey("moa_white"));
        EntityHelper.createEntity(MobMoaBlack.class, NamespaceID.getPermanent(MOD_ID, "moa_black"), entityKey("moa_black"));
        EntityHelper.createEntity(MobAerwhale.class, NamespaceID.getPermanent(MOD_ID, "aerwhale"), entityKey("aerwhale"));
        EntityHelper.createEntity(MobAerbunny.class, NamespaceID.getPermanent(MOD_ID, "aerbunny"), entityKey("aerbunny"));


        EntityHelper.createEntity(EntityParachute.class, NamespaceID.getPermanent(MOD_ID, "parachute"), entityKey("parachute"));
        EntityHelper.createEntity(EntityParachuteGold.class, NamespaceID.getPermanent(MOD_ID, "parachute_gold"), entityKey("parachute_gold"));
        EntityHelper.createEntity(MobMinicloud.class, NamespaceID.getPermanent(MOD_ID, "minicloud"), entityKey("minicloud"));


        EntityHelper.createTileEntity(TileEntityEnchanter.class, NamespaceID.getPermanent(MOD_ID, "enchanter"));
        EntityHelper.createTileEntity(TileEntityFreezer.class, NamespaceID.getPermanent(MOD_ID, "freezer"));
        EntityHelper.createTileEntity(TileEntityIncubator.class, NamespaceID.getPermanent(MOD_ID, "incubator"));
        EntityHelper.createTileEntity(TileEntitySignSkyroot.class, NamespaceID.getPermanent(MOD_ID, "sign_skyroot"));

        EntityHelper.createEntity(ProjectileKnifeLightning.class, NamespaceID.getPermanent(MOD_ID, "knife_lightning"), entityKey("knife_lightning"));
        EntityHelper.createEntity(ProjectileDartEnchanted.class, NamespaceID.getPermanent(MOD_ID, "dart_enchanted"), entityKey("dart_enchanted"));
        EntityHelper.createEntity(ProjectileDart.class, NamespaceID.getPermanent(MOD_ID, "dart"), entityKey("dart"));
        EntityHelper.createEntity(ProjectileArrowFlaming.class, NamespaceID.getPermanent(MOD_ID, "arrow_flaming"), entityKey("arrow_flaming"));
        EntityHelper.createEntity(ProjectileHammerHead.class, NamespaceID.getPermanent(MOD_ID, "hammer_head"), entityKey("hammer_head"));
        EntityHelper.createEntity(ProjectileWindball.class, NamespaceID.getPermanent(MOD_ID, "windball"), entityKey("windball"));
    }
}
