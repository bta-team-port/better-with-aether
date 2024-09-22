package bta.aether.entity;

import bta.aether.AetherMod;
import bta.aether.entity.model.*;
import bta.aether.entity.renderer.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.ModelSlime;
import turniplabs.halplibe.helper.EntityHelper;
import org.useless.dragonfly.helper.ModelHelper;
import org.useless.dragonfly.model.entity.BenchEntityModel;

import java.util.function.Supplier;

import static bta.aether.AetherMod.MOD_ID;

public class AetherEntities {
    private static int entityID = 100;

    public static final BenchEntityModel modelMoa =  ModelHelper.getOrCreateEntityModel(MOD_ID, "moa", ModelMoa.class);
    public static final BenchEntityModel modelMimic =  ModelHelper.getOrCreateEntityModel(MOD_ID, "mimic", ModelMimic.class);
    public static final BenchEntityModel modelValk =  ModelHelper.getOrCreateEntityModel(MOD_ID, "valk", ModelValk.class);


    public void initializeEntities() {
        Supplier<EntityRenderer<?>> supplier = () -> new SentryRenderer(new ModelSlime(0), 0.2F);
        EntityHelper.createEntity(EntityBossDev.class, entityID++, "FatherSentry", supplier);

        supplier = () -> new SentryRenderer(new ModelSlime(0), 0.2F);
        EntityHelper.createEntity(EntitySentry.class, entityID++, "Sentry", supplier);

        supplier = () -> new SwetRenderer(new ModelSlime(16), new ModelSlime(0), 0.3F);
        EntityHelper.createEntity(EntitySwet.class, entityID++, "Swet", supplier);

        supplier = ZephyrRenderer::new;
        EntityHelper.createEntity(EntityZephyr.class, entityID++, "Zephyr", supplier);


        supplier = () -> new MoaRenderer(modelMoa, 1F);
        EntityHelper.createEntity(EntityMoa.class, entityID++, "Moa", supplier);


        supplier = () -> new SliderRenderer(new ModelSlider(0.0F, 12.0F), 1.5F);
        EntityHelper.createEntity(EntityBossSlider.class, entityID++, "Slider", supplier);

        supplier = () -> new WhirlwindRenderer();
        EntityHelper.createEntity(EntityWhirlwind.class, entityID++, "Whirlwind", supplier);

        supplier = () -> new ValkRenderer(modelValk, 1F);
        EntityHelper.createEntity(EntityValk.class, entityID++, "Valk", supplier);

        supplier = () -> new AerwhaleRenderer(new ModelAerwhale(), 1F);
        EntityHelper.createEntity(EntityAerwhale.class, entityID++, "AerWhale", supplier);

        supplier = () -> new PhygRenderer(new ModelPhyg1(), new ModelPhyg2(), 0.7F);
        EntityHelper.createEntity(EntityPhyg.class, entityID++, "Phyg", supplier);

        supplier = () -> new PhowRenderer(new ModelPhow1(), new ModelPhow2(), 0.7F);
        EntityHelper.createEntity(EntityPhow.class, entityID++, "Phow", supplier);

        supplier = () -> new SheepuffRenderer(new ModelSheepuff1(), new ModelSheepuff2(), new ModelSheepuff3(), 0.7F);
        EntityHelper.createEntity(EntitySheepuff.class, entityID++, "Sheepuff", supplier);

        supplier = () -> new AerbunnyRenderer(new ModelAerbunny(), 0.4F);
        EntityHelper.createEntity(EntityAerbunny.class, entityID++, "AerBunny", supplier);

        supplier = () -> new MimicRenderer(modelMimic, 1F);
        EntityHelper.createEntity(EntityMimic.class, entityID++, "Mimic", supplier);

    }

    @Deprecated
    public void initializeModels(){
        AetherMod.LOGGER.warn("This is not used anymore. Please remove me.");
    }
}
