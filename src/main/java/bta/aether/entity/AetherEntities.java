package bta.aether.entity;

import bta.aether.entity.model.ModelSentry;
import bta.aether.entity.renderer.SentryRenderer;
import turniplabs.halplibe.helper.EntityHelper;
import useless.dragonfly.helper.ModelHelper;
import useless.dragonfly.model.entity.BenchEntityModel;

import static bta.aether.Aether.MOD_ID;

public class AetherEntities {
    private static int entityID = 100;

    public static final BenchEntityModel modelSentry =  ModelHelper.getOrCreateEntityModel(MOD_ID, "sentry", ModelSentry.class);

    public void initializeEntities() {
        EntityHelper.Client.assignEntityRenderer(EntitySentry.class, new SentryRenderer(modelSentry, 0.5f));
        EntityHelper.Core.createEntity(EntitySentry.class, entityID++, "Sentry");
    }
}
