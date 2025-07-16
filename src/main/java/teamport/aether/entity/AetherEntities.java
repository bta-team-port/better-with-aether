package teamport.aether.entity;

import net.minecraft.core.util.collection.NamespaceID;
import teamport.aether.entity.mimic.MobMimic;
import teamport.aether.entity.sentry.MobSentry;
import teamport.aether.entity.sheepuff.MobSheepuff;
import teamport.aether.entity.zephyr.MobZephyr;
import teamport.aether.tile.TileEntityEnchanter;
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


        // TODO register the other 2, incubator and freezer
        EntityHelper.createTileEntity(TileEntityEnchanter.class, NamespaceID.getPermanent(MOD_ID, "enchanter"));
    }
}
