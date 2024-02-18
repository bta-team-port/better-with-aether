package bta.aether;

import bta.aether.block.AetherBlocks;
import bta.aether.catalyst.effects.AetherEffects;
import bta.aether.entity.AetherEntities;
import bta.aether.entity.EntityFallingGravitite;
import bta.aether.entity.EntityPortalAetherFX;
import bta.aether.entity.projectiles.*;
import bta.aether.entity.renderer.aetherArrowRenderer;
import bta.aether.item.AetherItems;
import bta.aether.packet.PuffAerBunnyPacket;
import bta.aether.tile.TileEntityChestLocked;
import bta.aether.tile.TileEntityEnchanter;
import bta.aether.tile.TileEntityFreezer;
import bta.aether.tile.TileEntityIncubator;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.entity.fx.EntityFireflyFX;
import net.minecraft.client.render.entity.FallingSandRenderer;
import net.minecraft.client.render.entity.SnowballRenderer;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.*;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.achievements.AchievementPage;


public class Aether implements GameStartEntrypoint, ClientStartEntrypoint, ModInitializer {
    public static final String MOD_ID = "aether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        NetworkHelper.register(PuffAerBunnyPacket.class, true, false);
    }

    @Override
    public void beforeGameStart() {
        new AetherBlocks().initializeBlocks();
        new AetherItems().initializeItems();
        new AetherEntities().initializeEntities();
        new AetherEffects().initializeEffects();


        //Tiles
        EntityHelper.Core.createTileEntity(TileEntityEnchanter.class,"Enchanter");
        EntityHelper.Core.createTileEntity(TileEntityFreezer.class,"Freezer");
        EntityHelper.Core.createTileEntity(TileEntityIncubator.class,"Incubator");
        EntityHelper.Core.createTileEntity(TileEntityChestLocked.class, "chest.locked");

        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.logSkyroot.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.logOakGolden.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.planksSkyroot.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.slabPlanksSkyroot.id, 150);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.stairsPlanksSkyroot.id, 300);
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
        EntityHelper.Client.assignEntityRenderer(EntityLightningKnife.class, new SnowballRenderer(AetherItems.toolKnifeLightning.getIconIndex(new ItemStack(AetherItems.toolKnifeLightning))));
        EntityHelper.Client.assignEntityRenderer(EntityArrowFlaming.class, new aetherArrowRenderer("/Jar/aether/other/FlamingArrows.png"));
        EntityHelper.Client.assignEntityRenderer(EntityGoldenDart.class, new aetherArrowRenderer("/Jar/aether/mobs/entitygoldendart.png"));
        EntityHelper.Client.assignEntityRenderer(EntityPoisonDart.class, new aetherArrowRenderer("/Jar/aether/mobs/entitypoisondart.png"));
        EntityHelper.Client.assignEntityRenderer(EntityEnchantedDart.class, new aetherArrowRenderer("/Jar/aether/mobs/entityenchanteddart.png"));
        EntityHelper.Client.assignEntityRenderer(EntityHammerHead.class, new SnowballRenderer(TextureHelper.getOrCreateItemTextureIndex(Aether.MOD_ID, "../other/NotchWave.png")));
        EntityHelper.Client.assignEntityRenderer(EntityZephyrSnowball.class, new SnowballRenderer(Item.ammoSnowball.getIconIndex(new ItemStack(Item.ammoSnowball))));

        new AetherEntities().initializeModels();
    }

    @Override
    public void afterClientStart() {
    }
}
