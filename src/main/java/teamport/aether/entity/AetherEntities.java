package teamport.aether.entity;

import net.minecraft.core.util.collection.NamespaceID;
import teamport.aether.entity.sentry.MobSentry;
import turniplabs.halplibe.helper.EntityHelper;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherEntities {

    public String entityKey(String string) {
        return MOD_ID + ".entity." + string;
    }

    public void initializeEntities() {
        EntityHelper.createEntity(MobSentry.class, NamespaceID.getPermanent(MOD_ID, "sentry"), entityKey("sentry"));
    }

}
