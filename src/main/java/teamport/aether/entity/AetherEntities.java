package teamport.aether.entity;

import net.minecraft.core.util.collection.NamespaceID;
import teamport.aether.entity.mimic.MobMimic;
import teamport.aether.entity.phow.MobPhow;
import teamport.aether.entity.phyg.MobPhyg;
import teamport.aether.entity.sentry.MobSentry;
import teamport.aether.entity.sheepuff.MobSheepuff;
import teamport.aether.entity.swet.MobSwet;
import teamport.aether.entity.zephyr.MobZephyr;
import teamport.aether.tile.TileEntityChestLocked;
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
        EntityHelper.createEntity(MobSheepuff.class, NamespaceID.getPermanent(MOD_ID, "sheepuff"), entityKey("sheepuff"));
        EntityHelper.createEntity(MobPhow.class, NamespaceID.getPermanent(MOD_ID, "phow"), entityKey("phow"));
        EntityHelper.createEntity(MobPhyg.class, NamespaceID.getPermanent(MOD_ID, "phyg"), entityKey("phyg"));
        EntityHelper.createEntity(MobSwet.class, NamespaceID.getPermanent(MOD_ID, "swet"), entityKey("swet"));

        EntityHelper.createTileEntity(TileEntityEnchanter.class, NamespaceID.getPermanent(MOD_ID, "enchanter"));
        EntityHelper.createTileEntity(TileEntityFreezer.class, NamespaceID.getPermanent(MOD_ID, "freezer"));
        EntityHelper.createTileEntity(TileEntityIncubator.class, NamespaceID.getPermanent(MOD_ID, "incubator"));
        EntityHelper.createTileEntity(TileEntityChestLocked.class, NamespaceID.getPermanent(MOD_ID, "chest_locked"));
    }
}
