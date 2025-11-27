package teamport.aether;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.block.BlockLogicNote;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.EntityPainting;
import net.minecraft.core.entity.animal.MobFireflyCluster;
import net.minecraft.core.item.Items;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.entity.NetEntityHandler;
import net.minecraft.core.sound.SoundTypes;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.aether.blocks.AetherBlockDetails;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.effect.AetherEffects;
import teamport.aether.entity.AetherEntities;
import teamport.aether.items.AetherItems;
import teamport.aether.items.accessory.ItemTrinket;
import teamport.aether.net.*;
import teamport.aether.net.message.*;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.biome.AetherBiomes;
import teamport.aether.world.feature.AetherWorldFeatures;
import turniplabs.halplibe.helper.network.NetworkHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.core.entity.animal.MobFireflyCluster.FireflyColor.register;

@SuppressWarnings({"java:S1104", "java:S1444", "java:S3008"})
public class AetherMod implements GameStartEntrypoint, ModInitializer {
    public static final String MOD_ID = "aether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static final String VERSION_STRING = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion().getFriendlyString();
    public static final String STATE = "beta";
    public static I18n TRANSLATOR = null;
    public static MobFireflyCluster.FireflyColor SILVER;

    public static final DamageType LIGHTNING = new DamageType("damagetype.lightning", true, true, "aether:gui/hud/protection_lightning");
    public static final DamageType HOLY = new DamageType("damagetype.holy", false, true, "aether:gui/hud/protection_holy");
    @SuppressWarnings("java:S2386")
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

    // for slots
    public static final byte ARMOR_START_INDEX = 41;

    // for zanite
    public static final float ZANITE_MULTIPLIER = 2.0F;

    public static final byte BRONZE_CHANCES = 4;
    public static final byte SILVER_CHANCES = 10;
    public static final byte GOLD_CHANCES = 11;

    @Override
    public void onInitialize() {
        LOGGER.info("Aether initialized, welcome to a hostile paradise. Version {} {}", STATE, VERSION_STRING);
        NetworkHandler.registerNetworkMessage(SunspiritDeathNetworkMessage::new);
        NetworkHandler.registerNetworkMessage(AetherRideableNetworkMessage::new);
        NetworkHandler.registerNetworkMessage(BossListNetworkMessage::new);
        NetworkHandler.registerNetworkMessage(AetherDungeonMapUpdateNetworkMessage::new);
        NetworkHandler.registerNetworkMessage(AetherSyncInvisibilityNetworkMessage::new);
        NetworkHandler.registerNetworkMessage(AetherSyncRepulsionNetworkMessage::new);
    }

    @Override
    public void beforeGameStart() {
        AetherGlobals.init();
        AetherConfig.init();
        AetherEntities.init();
        AetherBlocks.init();
        AetherDimension.init();
        AetherItems.init();
        AetherWorldFeatures.init();

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
        AetherEffects.init();

        TRANSLATOR = I18n.getInstance();

        EntityPainting.addBorder(AetherItems.AMBER.getDefaultStack(), NamespaceID.getPermanent(MOD_ID, "border_amber"));
        EntityPainting.addBorder(AetherItems.ZANITE.getDefaultStack(), NamespaceID.getPermanent(MOD_ID, "border_zanite"));
        EntityPainting.addBorder(AetherBlocks.BLOCK_GRAVITITE.getDefaultStack(), NamespaceID.getPermanent(MOD_ID, "border_gravitite"));
        AetherBlockDetails.initializeBlockDetails();
        registerNewTagForItems();
    }

    public static void registerNewTagForItems() {
        ItemTrinket.setIcon(Items.TOOL_COMPASS, "aether:item/trinket/armor_compass_outline");
        ItemTrinket.setIcon(Items.TOOL_CALENDAR, "aether:item/trinket/armor_calendar_outline");
        ItemTrinket.setIcon(Items.TOOL_CLOCK, "aether:item/trinket/armor_clock_outline");
        ItemTrinket.setIcon(Items.MAP, "aether:item/trinket/armor_map_outline");
        ItemTrinket.setIcon(AetherItems.TOOL_DUNGEON_COMPASS, "aether:item/trinket/armor_compass_outline");

        Blocks.WORKBENCH.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.FURNACE_STONE_ACTIVE.withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
        Blocks.FURNACE_STONE_IDLE.withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
        Blocks.FURNACE_BLAST_ACTIVE.withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
        Blocks.FURNACE_BLAST_IDLE.withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
        Blocks.TROMMEL_ACTIVE.withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
        Blocks.TROMMEL_IDLE.withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
        Blocks.LADDER_OAK.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.GLOWSTONE.withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        Blocks.ICE.withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
        Blocks.PERMAICE.withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);
    }
}
