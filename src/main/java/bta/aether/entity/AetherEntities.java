package bta.aether.entity;

import bta.aether.entity.model.*;
import bta.aether.entity.renderer.*;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.core.enums.EnumDropCause;
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
    public static final BenchEntityModel modelSlider =  ModelHelper.getOrCreateEntityModel(MOD_ID, "slider", ModelSlider.class);


    public void initializeEntities() {
        EntityHelper.Core.createEntity(EntityBossDev.class, entityID++, "FatherSentry");
        EntityHelper.Core.createEntity(EntitySentry.class, entityID++, "Sentry");
        EntityHelper.Core.createEntity(EntitySwet.class, entityID++, "Swet");
        EntityHelper.Core.createEntity(EntityZephyr.class, entityID++, "Zephyr");
        EntityHelper.Core.createEntity(EntityMoa.class, entityID++, "Moa");
        EntityHelper.Core.createEntity(EntityBossSlider.class, entityID++, "Slider");
        EntityHelper.Core.createEntity(EntityMimic.class, entityID++, "Mimic");
        EntityHelper.Core.createEntity(EntityWhirlwind.class, entityID++, "Whirlwind");

    }
    public void initializeModels(){
        EntityHelper.Client.assignEntityRenderer(EntitySentry.class, new SentryRenderer(modelSentry, 0.5f));
        EntityHelper.Client.assignEntityRenderer(EntitySwet.class, new SwetRenderer(modelSwet, 1));
        EntityHelper.Client.assignEntityRenderer(EntityZephyr.class, new ZephyrRenderer(modelZephyr, 1));
        EntityHelper.Client.assignEntityRenderer(EntityMoa.class, new MoaRenderer(modelMoa, 1));
        EntityHelper.Client.assignEntityRenderer(EntityBossSlider.class, new LivingRenderer<EntityBossSlider>(modelSlider, 1));
        EntityHelper.Client.assignEntityRenderer(EntityWhirlwind.class, new WhirlwindRenderer());
    }
}
