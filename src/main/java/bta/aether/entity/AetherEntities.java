package bta.aether.entity;

import bta.aether.entity.model.ModelMoa;
import bta.aether.entity.model.ModelSentry;
import bta.aether.entity.model.ModelSwet;
import bta.aether.entity.model.ModelZephyr;
import bta.aether.entity.renderer.MoaRenderer;
import bta.aether.entity.renderer.SentryRenderer;
import bta.aether.entity.renderer.SwetRenderer;
import bta.aether.entity.renderer.ZephyrRenderer;
import bta.aether.entity.tileEntity.TileEntityChestLocked;
import turniplabs.halplibe.helper.EntityHelper;
import useless.dragonfly.helper.ModelHelper;
import useless.dragonfly.model.entity.BenchEntityModel;

import static bta.aether.Aether.MOD_ID;

public class AetherEntities {
    private static int entityID = 100;

    public static final BenchEntityModel modelSentry =  ModelHelper.getOrCreateEntityModel(MOD_ID, "sentry", ModelSentry.class);
    public static final BenchEntityModel modelSwet =  ModelHelper.getOrCreateEntityModel(MOD_ID, "swet", ModelSwet.class);
    public static final BenchEntityModel modelZephyr =  ModelHelper.getOrCreateEntityModel(MOD_ID, "zephyr", ModelZephyr.class);
    public static final BenchEntityModel modelMoa =  ModelHelper.getOrCreateEntityModel(MOD_ID, "moa", ModelMoa.class);

    public void initializeEntities() {
        EntityHelper.Core.createEntity(EntityDevBoss.class, entityID++, "FatherSentry");
        EntityHelper.Core.createEntity(EntitySentry.class, entityID++, "Sentry");
        EntityHelper.Core.createEntity(EntitySwet.class, entityID++, "Swet");
        EntityHelper.Core.createEntity(EntityZephyr.class, entityID++, "Zephyr");
        EntityHelper.Core.createEntity(EntityMoa.class, entityID++, "Moa");

        EntityHelper.Core.createTileEntity(TileEntityChestLocked.class, "chest.locked");
    }
    public void initializeModels(){
        EntityHelper.Client.assignEntityRenderer(EntitySentry.class, new SentryRenderer(modelSentry, 0.5f));
        EntityHelper.Client.assignEntityRenderer(EntitySwet.class, new SwetRenderer(modelSwet, 1));
        EntityHelper.Client.assignEntityRenderer(EntityZephyr.class, new ZephyrRenderer(modelZephyr, 1));
        EntityHelper.Client.assignEntityRenderer(EntityMoa.class, new MoaRenderer(modelMoa, 1));
    }
}
