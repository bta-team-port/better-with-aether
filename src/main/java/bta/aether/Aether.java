package bta.aether;

import bta.aether.block.AetherBlocks;
import bta.aether.catalyst.effects.AetherEffects;
import bta.aether.entity.AetherEntities;
import bta.aether.entity.EntityPortalAetherFX;
import bta.aether.entity.EntitySentry;
import bta.aether.entity.EntityFallingGravitite;
import bta.aether.entity.ArrowFlamingRenderer;
import bta.aether.entity.EntityArrowFlaming;
import bta.aether.entity.*;
import bta.aether.item.AetherItems;
import bta.aether.tile.TileEntityChestLocked;
import bta.aether.tile.TileEntityEnchanter;
import bta.aether.tile.TileEntityFreezer;
import bta.aether.tile.TileEntityIncubator;
import net.minecraft.client.entity.fx.EntityFireflyFX;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.render.entity.FallingSandRenderer;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.AchievementHelper;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.ParticleHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.achievements.AchievementPage;


public class Aether implements GameStartEntrypoint, ClientStartEntrypoint {
    public static final String MOD_ID = "aether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void beforeGameStart() {
        new AetherBlocks().initializeBlocks();
        new AetherItems().initializeItems();
        new AetherEntities().initializeEntities();
        new AetherEffects().initializeEffects();

        MobInfoRegistry.register(EntitySentry.class, "aether.sentry.name", "aether.sentry.desc",
                20, 0, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(new ItemStack(AetherBlocks.stoneCarved),
                        0.66f * 0.8f, 1 ,2), new MobInfoRegistry.MobDrop(new ItemStack(AetherBlocks.stoneCarvedLight),
                        0.66f * 0.2f, 1, 2)});


        //Tiles
        EntityHelper.Core.createTileEntity(TileEntityEnchanter.class,"Enchanter");
        EntityHelper.Core.createTileEntity(TileEntityFreezer.class,"Freezer");
        EntityHelper.Core.createTileEntity(TileEntityIncubator.class,"Incubator");
        EntityHelper.Core.createTileEntity(TileEntityChestLocked.class, "chest.locked");

        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.logSkyroot.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.logOakGolden.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.planksSkyroot.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.stickSkyroot.id, 150);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.toolPickaxeSkyroot.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.toolShovelSkyroot.id, 600);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.toolAxeSkyroot.id, 600);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.toolSwordSkyroot.id, 600);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.bucketSkyroot.id, 600);

        ParticleHelper.createParticle(EntityPortalAetherFX.class, "aether");
        ParticleHelper.createParticle(EntityFireflyFX.class, "fireflySilver");

        AchievementPage AETHERACHIEVEMENTS;
        AETHERACHIEVEMENTS = new AetherAchievements();
        AchievementHelper.addPage(AETHERACHIEVEMENTS);

        LOGGER.info("Aether initialized. Welcome to a hostile paradise.");
    }

    @Override
    public void afterGameStart() {

    }

    @Override
    public void beforeClientStart() {
        EntityHelper.Client.assignEntityRenderer(EntityFallingGravitite.class, new FallingSandRenderer());
        EntityHelper.Client.assignEntityRenderer(EntityArrowFlaming.class, new ArrowFlamingRenderer());
        EntityHelper.Client.assignEntityRenderer(EntityGoldenDart.class, new GoldenDartRenderer());
        new AetherEntities().initializeModels();
    }

    @Override
    public void afterClientStart() {
    }
}
