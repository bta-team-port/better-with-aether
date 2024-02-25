package bta.aether;

import bta.aether.block.AetherBlocks;
import bta.aether.catalyst.effects.AetherEffects;
import bta.aether.entity.AetherEntities;
import bta.aether.entity.EntityFallingGravitite;
import bta.aether.entity.EntityFlameAmbrosiumFX;
import bta.aether.entity.EntityPortalAetherFX;
import bta.aether.entity.projectiles.*;
import bta.aether.entity.renderer.aetherArrowRenderer;
import bta.aether.item.AetherItems;
import bta.aether.packet.PuffAerBunnyPacket;
import bta.aether.tile.TileEntityChestLocked;
import bta.aether.tile.TileEntityEnchanter;
import bta.aether.tile.TileEntityFreezer;
import bta.aether.tile.TileEntityIncubator;
import bta.aether.world.AetherDimension;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.entity.fx.EntityFireflyFX;
import net.minecraft.client.render.entity.FallingSandRenderer;
import net.minecraft.client.render.entity.SnowballRenderer;
import net.minecraft.core.block.BlockLanternFirefly;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.*;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.FireflyColor;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.achievements.AchievementPage;

import static net.minecraft.client.entity.fx.EntityFlameFX.Type.ORANGE;


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

        TextureHelper.getOrCreateParticleTexture(MOD_ID, "flameambrosium.png");
        ParticleHelper.createParticle("flameambrosium", (world, x, y, z, motionX, motionY, motionZ) -> {
            return new EntityFlameAmbrosiumFX(world, x, y, z, motionX, motionY, motionZ, ORANGE);
        });

        ((BlockLanternFirefly) AetherBlocks.lanternAetherBlock).setItem(AetherItems.lanternAether);
        ParticleHelper.createParticle("fireflySilver", (world, x, y, z, motionX, motionY, motionZ) -> {
            EntityFireflyFX particle = new EntityFireflyFX(world, x, y, z, motionX, motionY, motionZ, 2.5f, 0);
            ParticleHelper.setFireflyColorMin(particle, 0.25f, 0.50f, 0.35f);
            ParticleHelper.setFireflyColorMid(particle, 0.50f, 0.75f, 0.60f);
            ParticleHelper.setFireflyColorMax(particle, 0.75f, 1.00f, 0.85f);
            return particle;
        });

        FireflyColor fireflySilver = new FireflyColor(6, "fireflySilver", new ItemStack(AetherItems.lanternAether, 1), new Biome[]{AetherDimension.biomeAether});

        FireflyHelper.createColor(fireflySilver);
        FireflyHelper.setColor((BlockLanternFirefly) AetherBlocks.lanternAetherBlock, fireflySilver);

        AchievementPage AETHERACHIEVEMENTS;
        AETHERACHIEVEMENTS = new AetherAchievements();
        AchievementHelper.addPage(AETHERACHIEVEMENTS);

        LOGGER.info("Aether initialized. Welcome to a hostile paradise.");
    }

    @Override
    public void afterGameStart() {
        new AetherRecipes().initilizeRecipes();
    }

    @Override
    public void beforeClientStart() {
        EntityHelper.Client.assignEntityRenderer(EntityFallingGravitite.class, new FallingSandRenderer());
        EntityHelper.Client.assignEntityRenderer(EntityLightningKnife.class, new SnowballRenderer(AetherItems.toolKnifeLightning.getIconIndex(new ItemStack(AetherItems.toolKnifeLightning))));
        EntityHelper.Client.assignEntityRenderer(EntityArrowFlaming.class, new aetherArrowRenderer("/assets/aether/other/FlamingArrows.png"));
        EntityHelper.Client.assignEntityRenderer(EntityGoldenDart.class, new aetherArrowRenderer("/assets/aether/mobs/entitygoldendart.png"));
        EntityHelper.Client.assignEntityRenderer(EntityPoisonDart.class, new aetherArrowRenderer("/assets/aether/mobs/entitypoisondart.png"));
        EntityHelper.Client.assignEntityRenderer(EntityEnchantedDart.class, new aetherArrowRenderer("/assets/aether/mobs/entityenchanteddart.png"));
        EntityHelper.Client.assignEntityRenderer(EntityHammerHead.class, new SnowballRenderer(TextureHelper.getOrCreateItemTextureIndex(Aether.MOD_ID, "../other/NotchWave.png")));
        EntityHelper.Client.assignEntityRenderer(EntityZephyrSnowball.class, new SnowballRenderer(Item.ammoSnowball.getIconIndex(new ItemStack(Item.ammoSnowball))));

        new AetherEntities().initializeModels();
    }

    @Override
    public void afterClientStart() {
    }
}
