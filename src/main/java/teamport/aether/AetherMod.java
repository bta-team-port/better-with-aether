package teamport.aether;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.block.BlockLogicNote;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.entity.EntityPainting;
import net.minecraft.core.entity.animal.MobFireflyCluster;
import net.minecraft.core.item.Items;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.entity.NetEntityHandler;
import net.minecraft.core.sound.SoundTypes;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.effect.AetherEffects;
import teamport.aether.entity.AetherEntities;
import teamport.aether.items.AetherItems;
import teamport.aether.items.accessory.ItemTrinket;
import teamport.aether.net.*;
import teamport.aether.net.message.AetherDungeonMapUpdateNetworkMessage;
import teamport.aether.net.message.AetherRideableNetworkMessage;
import teamport.aether.net.message.BossListNetworkMessage;
import teamport.aether.net.message.SunspiritDeathNetworkMessage;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.biome.AetherBiomes;
import turniplabs.halplibe.helper.network.NetworkHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.core.block.BlockLogicNote.Instrument.CELESTA;
import static net.minecraft.core.entity.animal.MobFireflyCluster.FireflyColor.register;

public class AetherMod implements GameStartEntrypoint, ModInitializer {
    public static final String MOD_ID = "aether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static String versionString = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion().getFriendlyString();
    public static String state = "alpha";
    public static I18n TRANSLATOR = null;
    public static MobFireflyCluster.FireflyColor SILVER;

    public static final Map<Integer, BlockLogicNote.Instrument> BLOCK_INSTRUMENTS = new HashMap<>();

    public static final BlockLogicNote.Instrument FLUTE = new BlockLogicNote.Instrument(11, "flute");
    public static final BlockLogicNote.Instrument CLICK = new BlockLogicNote.Instrument(12, "click");
    public static final BlockLogicNote.Instrument XYLOPHONE = new BlockLogicNote.Instrument(13, "xylophone");
    public static final BlockLogicNote.Instrument BELL = new BlockLogicNote.Instrument(14, "bell");
    public static final BlockLogicNote.Instrument TRUMPET = new BlockLogicNote.Instrument(15, "trumpet");
    public static final BlockLogicNote.Instrument ORGAN = new BlockLogicNote.Instrument(16, "organ");
    public static final BlockLogicNote.Instrument SITAR = new BlockLogicNote.Instrument(17, "sitar");
    public static final BlockLogicNote.Instrument TRANCE = new BlockLogicNote.Instrument(18, "trance");
    public static final BlockLogicNote.Instrument SAXOPHONE = new BlockLogicNote.Instrument(19, "saxophone");
    public static final BlockLogicNote.Instrument MUSICBOX = new BlockLogicNote.Instrument(20, "musicbox");

    // hide the mimic description
    public static final boolean BTWAILA = FabricLoader.getInstance().isModLoaded("btwaila");
    // for slots
    public static final byte ARMOR_START_INDEX = 41;

    // for zanite
    public static final float ZANITE_MULTIPLIER = 2.0F;

    public static final byte BRONZE_CHANCES = 4;
    public static final byte SILVER_CHANCES = 10;
    public static final byte GOLD_CHANCES = 11;

    @Override
    public void onInitialize() {
        LOGGER.info("Aether initialized, welcome to a hostile paradise. Version {} {}", state, versionString);
        NetworkHandler.registerNetworkMessage(SunspiritDeathNetworkMessage::new);
        NetworkHandler.registerNetworkMessage(AetherRideableNetworkMessage::new);
        NetworkHandler.registerNetworkMessage(BossListNetworkMessage::new);
        NetworkHandler.registerNetworkMessage(AetherDungeonMapUpdateNetworkMessage::new);
    }

    @Override
    public void beforeGameStart() {
        AetherConfig.Setup();
        AetherEntities.init();
        AetherBlocks.init();
        AetherItems.init();
        AetherDimension.init();
        AetherEffects.init();

        SILVER = register(new MobFireflyCluster.FireflyColor(10, "fireflySilver", new Biome[]{AetherBiomes.AETHER_PLAINS}, new float[]{0.5F, 1.0F, 0.88F}));

        NetEntityHandler.registerNetworkEntry(new NetEntryLightning(), 32);
        NetEntityHandler.registerNetworkEntry(new NetEntryAetherProjectile(), 35);
        NetEntityHandler.registerNetworkEntry(new NetEntryParachute(), 36);
        NetEntityHandler.registerNetworkEntry(new NetEntryFloatingBlock(), 37);
        NetEntityHandler.registerNetworkEntry(new NetEntrySlider());

        SoundTypes.loadSoundsJson(MOD_ID);
    }

    @Override
    public void afterGameStart() {
        TRANSLATOR = I18n.getInstance();

        EntityPainting.addBorder(AetherItems.ZANITE.getDefaultStack(), NamespaceID.getPermanent("aether", "border_zanite"));
        EntityPainting.addBorder(AetherBlocks.BLOCK_GRAVITITE.getDefaultStack(), NamespaceID.getPermanent("aether", "border_gravitite"));
        registerNewFurnaceFuel();
        registerNewTagForItems();
        registerBlockInstruments();
    }

    public static void registerNewTagForItems() {
        ItemTrinket.setIcon(Items.TOOL_COMPASS, "aether:item/trinket/armor_compass_outline_alt2");
        ItemTrinket.setIcon(Items.TOOL_CALENDAR, "aether:item/trinket/armor_calendar_outline");
        ItemTrinket.setIcon(Items.TOOL_CLOCK, "aether:item/trinket/armor_clock_outline_alt1");
        ItemTrinket.setIcon(Items.MAP, "aether:item/trinket/armor_outline_map_filled");
        ItemTrinket.setIcon(AetherItems.TOOL_DUNGEON_COMPASS, "aether:item/trinket/armor_compass_outline");

        Blocks.WORKBENCH.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.FURNACE_STONE_ACTIVE.withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
        Blocks.FURNACE_STONE_IDLE.withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
        Blocks.LADDER_OAK.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.GLOWSTONE.withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        Blocks.ICE.withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
        Blocks.PERMAICE.withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
    }

    public static void registerNewFurnaceFuel() {
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.STICK_SKYROOT.id, 100);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.PLANKS_SKYROOT_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.STAIRS_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.SLAB_PLANKS_SKYROOT.id(), 150);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED.id(), 150);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.FENCEGATE_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.SIGN_SKYROOT.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.SIGN_SKYROOT_PAINTED.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.DOOR_SKYROOT.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.DOOR_SKYROOT_PAINTED.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.BUTTON_PLANKS_SKYROOT.id(), 75);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.BUTTON_PLANKS_SKYROOT_PAINTED.id(), 75);

        LookupFuelFurnace.instance.addFuelEntry(Blocks.BUTTON_PLANKS.id(), 75);
        LookupFuelFurnace.instance.addFuelEntry(Blocks.BUTTON_PLANKS_PAINTED.id(), 75);

        LookupFuelFurnace.instance.addFuelEntry(AetherItems.BUCKET_SKYROOT.id, 300);

        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.LOG_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.LOG_OAK_GOLDEN.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.SAPLING_SKYROOT.id(), 100);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.SAPLING_OAK_GOLDEN.id(), 100);

        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.CHEST_MIMIC_OAK.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.CHEST_MIMIC_OAK_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.CHEST_MIMIC_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.id(), 300);

        LookupFuelFurnace.instance.addFuelEntry(AetherItems.TOOL_PICKAXE_SKYROOT.id, 500);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.TOOL_SWORD_SKYROOT.id, 500);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.TOOL_AXE_SKYROOT.id, 500);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.TOOL_SHOVEL_SKYROOT.id, 500);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.TOOL_SHOOTER.id, 300);
    }

    public static void registerBlockInstruments() {
        BLOCK_INSTRUMENTS.put(AetherBlocks.AERCLOUD_WHITE.id(), FLUTE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.AERCLOUD_BLUE.id(), FLUTE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.AERCLOUD_GOLD.id(), FLUTE);

        // we do have a lot, a lot, of chests.
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_PLANKS_SKYROOT.id(), CLICK);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_MIMIC_OAK.id(), CLICK);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_MIMIC_OAK_PAINTED.id(), CLICK);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_MIMIC_SKYROOT.id(), CLICK);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.id(), CLICK);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_DUNGEON_BRONZE.id(), CLICK);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_DUNGEON_BRONZE_LOCKED.id(), CLICK);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_DUNGEON_GOLD.id(), CLICK);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_DUNGEON_GOLD_LOCKED.id(), CLICK);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_DUNGEON_SILVER.id(), CLICK);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_DUNGEON_SILVER_LOCKED.id(), CLICK);

        BLOCK_INSTRUMENTS.put(AetherBlocks.BLOCK_ZANITE.id(), MUSICBOX);
        BLOCK_INSTRUMENTS.put(AetherBlocks.BRICK_ZANITE.id(), MUSICBOX);
        BLOCK_INSTRUMENTS.put(AetherBlocks.SLAB_BRICK_ZANITE.id(), MUSICBOX);
        BLOCK_INSTRUMENTS.put(AetherBlocks.STAIRS_BRICK_ZANITE.id(), MUSICBOX);

        BLOCK_INSTRUMENTS.put(AetherBlocks.BLOCK_AMBER.id(), SAXOPHONE);

        BLOCK_INSTRUMENTS.put(AetherBlocks.QUICKSOIL.id(), SITAR);
        BLOCK_INSTRUMENTS.put(AetherBlocks.GLASS_QUICKSOIL.id(), SITAR);

        BLOCK_INSTRUMENTS.put(AetherBlocks.BLOCK_GRAVITITE.id(), XYLOPHONE);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_HELLFIRE.id(), ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_HELLFIRE_LIGHT.id(), ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.SLAB_CARVED_HELLFIRE.id(), ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.STAIRS_CARVED_HELLFIRE.id(), ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_HELLFIRE_LOCKED.id(), ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED.id(), ORGAN);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC_LIGHT.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.SLAB_CARVED_ANGELIC.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.STAIRS_CARVED_ANGELIC.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC_LOCKED.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC_TRAPPED.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC_TRAPPED_LOCKED.id(), BELL);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE.id(), TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE_LIGHT.id(), TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.SLAB_CARVED_STONE.id(), TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.STAIRS_CARVED_STONE.id(), TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE_LOCKED.id(), TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE_LIGHT_LOCKED.id(), TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE_TRAPPED.id(), TRANCE);

        BLOCK_INSTRUMENTS.put(AetherBlocks.ICESTONE.id(), CELESTA);
        BLOCK_INSTRUMENTS.put(AetherBlocks.BLOCK_AMBROSIUM.id(), TRUMPET);
    }
}
