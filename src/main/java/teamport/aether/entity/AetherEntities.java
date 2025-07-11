package teamport.aether.entity;

import net.minecraft.core.util.collection.NamespaceID;
import teamport.aether.entity.sentry.MobSentry;
import teamport.aether.entity.zephyr.MobZephyr;
import turniplabs.halplibe.helper.EntityHelper;

import static teamport.aether.AetherMod.MOD_ID;

public final class AetherEntities {
    private static boolean hasInit = false;

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

    }

}
