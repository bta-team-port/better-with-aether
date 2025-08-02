package teamport.aether.entity;

import net.minecraft.core.util.collection.NamespaceID;
import teamport.aether.entity.aerbunny.MobAerbunny;
import teamport.aether.entity.aerwhale.MobAerwhale;
import teamport.aether.entity.cockatrice.MobCockatrice;
import teamport.aether.entity.mimic.MobMimic;
import teamport.aether.entity.minicloud.MobMinicloud;
import teamport.aether.entity.moa.MobMoa;
import teamport.aether.entity.moa.MobMoaBlack;
import teamport.aether.entity.moa.MobMoaWhite;
import teamport.aether.entity.parachute.EntityParachute;
import teamport.aether.entity.parachute.EntityParachuteGold;
import teamport.aether.entity.phow.MobPhow;
import teamport.aether.entity.phyg.MobPhyg;
import teamport.aether.entity.sentry.MobSentry;
import teamport.aether.entity.sheepuff.MobSheepuff;
import teamport.aether.entity.slider.MobBossSlider;
import teamport.aether.entity.sunspirit.MobBossSunspirit;
import teamport.aether.entity.swet.MobSwet;
import teamport.aether.entity.valkyrie.MobBossValkyrie;
import teamport.aether.entity.valkyrie.MobValkyrie;
import teamport.aether.entity.zephyr.MobZephyr;
import teamport.aether.tile.TileEntityEnchanter;
import teamport.aether.tile.TileEntityFreezer;
import teamport.aether.tile.TileEntityIncubator;
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
    }
}
