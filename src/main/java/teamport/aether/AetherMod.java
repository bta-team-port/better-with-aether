package teamport.aether;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.texture.stitcher.AtlasStitcher;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.EntityPainting;
import net.minecraft.core.entity.animal.MobFireflyCluster;
import net.minecraft.core.item.Items;
import net.minecraft.core.sound.SoundTypes;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.AetherEntities;
import teamport.aether.items.AetherItemTags;
import teamport.aether.items.AetherItems;
import turniplabs.halplibe.util.GameStartEntrypoint;

import java.io.IOException;
import java.net.URISyntaxException;

import static net.minecraft.core.entity.animal.MobFireflyCluster.FireflyColor.register;

public class AetherMod implements GameStartEntrypoint, ModInitializer {
    public static final String MOD_ID = "aether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static MobFireflyCluster.FireflyColor SILVER;

    // for slots
    public static final byte ARMOR_START_INDEX = 41;

    @Override
    public void onInitialize() {
        LOGGER.info("Aether initialized. Welcome to a hostile paradise.");
    }

    @Override
    public void beforeGameStart() {

        SILVER = register(new MobFireflyCluster.FireflyColor(10, "fireflySilver", new Biome[]{Biomes.PARADISE_PARADISE}, new float[]{0.5F, 1.0F, 0.88F}));
        //TODO Replace biome here with aether biome once added

        AetherConfig.Setup();
        AetherEntities.init();
        AetherBlocks.init();
        AetherItems.init();

        SoundTypes.loadSoundsJson(MOD_ID);
        AetherMod.registerTextures();
    }

    @Override
    public void afterGameStart() {
        EntityPainting.addBorder(AetherItems.ZANITE.getDefaultStack(), NamespaceID.getPermanent("aether", "border_zanite"));
        EntityPainting.addBorder(AetherBlocks.BLOCK_GRAVITITE.getDefaultStack(), NamespaceID.getPermanent("aether", "border_gravitite"));
        registerNewFurnaceFuel();
        registerNewTagForItems();
    }

    private static void registerNewTagForItems() {
        Items.TOOL_COMPASS.withTags(new Tag[]{AetherItemTags.ACCESSORY});
        Items.TOOL_CALENDAR.withTags(new Tag[]{AetherItemTags.ACCESSORY});
        Items.TOOL_CLOCK.withTags(new Tag[]{AetherItemTags.ACCESSORY});
        Items.MAP.withTags(new Tag[]{AetherItemTags.ACCESSORY});
    }

    private static void registerNewFurnaceFuel() {
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.STAIRS_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.SLAB_PLANKS_SKYROOT.id(), 150);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.FENCEGATE_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.CHEST_MIMIC.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.LOG_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.LOG_OAK_GOLDEN.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.SAPLING_SKYROOT.id(), 100);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.SAPLING_OAK_GOLDEN.id(), 100);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.STICK_SKYROOT.id, 100);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.TOOL_PICKAXE_SKYROOT.id, 500);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.TOOL_SWORD_SKYROOT.id, 500);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.TOOL_AXE_SKYROOT.id, 500);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.TOOL_SHOVEL_SKYROOT.id, 500);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.TOOL_SHOOTER.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.BUCKET_SKYROOT.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.DOOR_SKYROOT.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.BUTTON_PLANKS_SKYROOT.id(), 75);
        LookupFuelFurnace.instance.addFuelEntry(Blocks.BUTTON_PLANKS.id(), 75);
        LookupFuelFurnace.instance.addFuelEntry(Blocks.BUTTON_PLANKS_PAINTED.id(), 75);
    }

    public static void registerTextures() {
        for (final AtlasStitcher stitcher : TextureRegistry.stitcherMap.values()) {
            try {
                TextureRegistry.initializeAllFiles(MOD_ID, stitcher, true);
            } catch (URISyntaxException | IOException e) {
                AetherMod.LOGGER.error("Failed to initialize texture files!", e);
            }
        }
    }
}
