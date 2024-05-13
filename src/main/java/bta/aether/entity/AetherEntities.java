package bta.aether.entity;

import bta.aether.entity.model.*;
import bta.aether.entity.renderer.*;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelSlime;
import turniplabs.halplibe.helper.EntityHelper;
import useless.dragonfly.helper.ModelHelper;
import useless.dragonfly.model.entity.BenchEntityModel;

import static bta.aether.Aether.MOD_ID;

public class AetherEntities {
    private static int entityID = 100;

    public static final BenchEntityModel modelSwet =  ModelHelper.getOrCreateEntityModel(MOD_ID, "swet", ModelSwet.class);
    public static final BenchEntityModel modelMoa =  ModelHelper.getOrCreateEntityModel(MOD_ID, "moa", ModelMoa.class);
    public static final BenchEntityModel modelMimic =  ModelHelper.getOrCreateEntityModel(MOD_ID, "mimic", ModelMimic.class);
    public static final BenchEntityModel modelValk =  ModelHelper.getOrCreateEntityModel(MOD_ID, "valk", ModelValk.class);
    public static final BenchEntityModel modelAerwhale = ModelHelper.getOrCreateEntityModel(MOD_ID, "aerwhale", ModelAerwhale.class);


    public void initializeEntities() {
        EntityHelper.Core.createEntity(EntityBossDev.class, entityID++, "FatherSentry");
        EntityHelper.Core.createEntity(EntitySentry.class, entityID++, "Sentry");
        EntityHelper.Core.createEntity(EntitySwet.class, entityID++, "Swet");
        EntityHelper.Core.createEntity(EntityZephyr.class, entityID++, "Zephyr");
        EntityHelper.Core.createEntity(EntityMoa.class, entityID++, "Moa");
        EntityHelper.Core.createEntity(EntityBossSlider.class, entityID++, "Slider");
        EntityHelper.Core.createEntity(EntityMimic.class, entityID++, "Mimic");
        EntityHelper.Core.createEntity(EntityWhirlwind.class, entityID++, "Whirlwind");
        EntityHelper.Core.createEntity(EntityAerbunny.class, entityID++, "AerBunny");
        EntityHelper.Core.createEntity(EntityValk.class, entityID++, "Valk");
        EntityHelper.Core.createEntity(EntityAerwhale.class, entityID++, "AerWhale");
        EntityHelper.Core.createEntity(EntityPhyg.class, entityID++, "Phyg");
        EntityHelper.Core.createEntity(EntityPhow.class, entityID++, "Phow");
        EntityHelper.Core.createEntity(EntitySheepuff.class, entityID++, "Sheepuff");
    }
    public void initializeModels(){
        EntityHelper.Client.assignEntityRenderer(EntitySentry.class, new SentryRenderer(new ModelSlime(0), 0.2F));
        EntityHelper.Client.assignEntityRenderer(EntitySwet.class, new SwetRenderer(modelSwet, 1F));
        EntityHelper.Client.assignEntityRenderer(EntityZephyr.class, new ZephyrRenderer());
        EntityHelper.Client.assignEntityRenderer(EntityMoa.class, new MoaRenderer(modelMoa, 1F));
        EntityHelper.Client.assignEntityRenderer(EntityBossSlider.class, new SliderRenderer(new ModelSlider(0.0F, 12.0F), 1.5F));
        EntityHelper.Client.assignEntityRenderer(EntityWhirlwind.class, new WhirlwindRenderer());
        EntityHelper.Client.assignEntityRenderer(EntityValk.class, new LivingRenderer<EntityAerbunny>(modelValk, 1F));
        EntityHelper.Client.assignEntityRenderer(EntityAerwhale.class, new LivingRenderer<EntityAerwhale>(modelAerwhale, 1F));
        EntityHelper.Client.assignEntityRenderer(EntityMimic.class, new MimicRenderer(modelMimic, 1F));
        EntityHelper.Client.assignEntityRenderer(EntityPhyg.class, new PhygRenderer(new ModelPhyg1(), new ModelPhyg2(), 0.7F));
        EntityHelper.Client.assignEntityRenderer(EntityPhow.class, new PhowRenderer(new ModelPhow1(), new ModelPhow2(), 0.7F));
        EntityHelper.Client.assignEntityRenderer(EntitySheepuff.class, new SheepuffRenderer(new ModelSheepuff1(), new ModelSheepuff2(), new ModelSheepuff3(), 0.7F));
        EntityHelper.Client.assignEntityRenderer(EntityAerbunny.class, new AerbunnyRenderer(new ModelAerbunny(), 0.7F));

    }
}
