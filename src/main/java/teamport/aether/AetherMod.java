package teamport.aether;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.entity.EntityPainting;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.entity.animal.MobFireflyCluster;
import net.minecraft.core.enums.MobCategory;
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
import teamport.aether.entity.animal.aerbunny.MobAerbunny;
import teamport.aether.entity.animal.aerwhale.MobAerwhale;
import teamport.aether.entity.animal.moa.MobMoaBlack;
import teamport.aether.entity.animal.moa.MobMoaBlue;
import teamport.aether.entity.animal.moa.MobMoaWhite;
import teamport.aether.entity.animal.phow.MobPhow;
import teamport.aether.entity.animal.phyg.MobPhyg;
import teamport.aether.entity.animal.sheepuff.MobSheepuff;
import teamport.aether.entity.monster.aechorplant.MobAechorPlant;
import teamport.aether.entity.monster.cockatrice.MobCockatrice;
import teamport.aether.entity.monster.swet.MobSwet;
import teamport.aether.entity.monster.swet.MobSwetGold;
import teamport.aether.entity.monster.whirly.MobWhirly;
import teamport.aether.entity.monster.zephyr.MobZephyr;
import teamport.aether.items.AetherItems;
import teamport.aether.items.accessory.ItemTrinket;
import teamport.aether.net.NetEntryAetherProjectile;
import teamport.aether.net.NetEntryLightning;
import teamport.aether.net.NetEntrySlider;
import teamport.aether.net.message.AetherRideableNetworkMessage;
import teamport.aether.net.message.BossListNetworkMessage;
import teamport.aether.net.message.SunspiritDeathNetworkMessage;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.network.NetworkHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;

import static net.minecraft.core.entity.animal.MobFireflyCluster.FireflyColor.register;

public class AetherMod implements GameStartEntrypoint, ModInitializer {
    public static final String MOD_ID = "aether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static String versionString = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion().getFriendlyString();
    public static String state = "alpha";
    public static I18n TRANSLATOR = null;
    public static MobFireflyCluster.FireflyColor SILVER;

    // hide the mimic description
    public static final boolean BTWAILA = FabricLoader.getInstance().isModLoaded("btwaila");
    // for slots
    public static final byte ARMOR_START_INDEX = 41;

    public static final byte BRONZE_CHANCES = 4;
    public static final byte SILVER_CHANCES = 10;
    public static final byte GOLD_CHANCES = 11;

    @Override
    public void onInitialize() {
        LOGGER.info("Aether initialized, welcome to a hostile paradise. Version {} {}", state , versionString);
        NetworkHandler.registerNetworkMessage(SunspiritDeathNetworkMessage::new);
        NetworkHandler.registerNetworkMessage(AetherRideableNetworkMessage::new);
        NetworkHandler.registerNetworkMessage(BossListNetworkMessage::new);
    }

    @Override
    public void beforeGameStart() {
        AetherConfig.Setup();
        AetherEntities.init();
        AetherBlocks.init();
        AetherItems.init();
        AetherDimension.init();
        AetherEffects.init();

        SILVER = register(new MobFireflyCluster.FireflyColor(10, "fireflySilver", new Biome[]{AetherDimension.AETHER_PLAINS}, new float[]{0.5F, 1.0F, 0.88F}));

        NetEntityHandler.registerNetworkEntry(new NetEntryLightning(), 32);
        NetEntityHandler.registerNetworkEntry(new NetEntryAetherProjectile(), 35);
        NetEntityHandler.registerNetworkEntry(new NetEntrySlider());

        SoundTypes.loadSoundsJson(MOD_ID);
    }

    @Override
    public void afterGameStart() {
        TRANSLATOR = I18n.getInstance();

        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.monster).clear();
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.creature).clear();
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.waterCreature).clear();
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.ambientCreature).clear();

        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.creature).add(new SpawnListEntry(MobPhyg.class, 102));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.creature).add(new SpawnListEntry(MobPhow.class, 102));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.creature).add(new SpawnListEntry(MobSheepuff.class, 102));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.creature).add(new SpawnListEntry(MobAerbunny.class, 102));

        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.ambientCreature).add(new SpawnListEntry(MobAerwhale.class, 5));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.ambientCreature).add(new SpawnListEntry(MobFireflyCluster.class, 30));

        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.creature).add(new SpawnListEntry(MobMoaBlue.class, 51));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.creature).add(new SpawnListEntry(MobMoaWhite.class, 26));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.creature).add(new SpawnListEntry(MobMoaBlack.class, 13));

        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.monster).add(new SpawnListEntry(MobZephyr.class, 10));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.monster).add(new SpawnListEntry(MobSwet.class, 5));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.monster).add(new SpawnListEntry(MobSwetGold.class, 2));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.monster).add(new SpawnListEntry(MobAechorPlant.class, 5));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.monster).add(new SpawnListEntry(MobCockatrice.class, 10));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.monster).add(new SpawnListEntry(MobWhirly.class, 5));


        EntityPainting.addBorder(AetherItems.ZANITE.getDefaultStack(), NamespaceID.getPermanent("aether", "border_zanite"));
        EntityPainting.addBorder(AetherBlocks.BLOCK_GRAVITITE.getDefaultStack(), NamespaceID.getPermanent("aether", "border_gravitite"));
        registerNewFurnaceFuel();
        registerNewTagForItems();

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

        Blocks.PLANKS_OAK_PAINTED.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.PLANKS_OAK.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.STAIRS_PLANKS_OAK.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.STAIRS_PLANKS_PAINTED.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.SLAB_PLANKS_OAK.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.SLAB_PLANKS_PAINTED.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.FENCE_PLANKS_OAK.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.FENCE_PLANKS_OAK_PAINTED.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.FENCE_GATE_PLANKS_OAK.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.FENCE_GATE_PLANKS_OAK_PAINTED.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.BUTTON_PLANKS.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.BUTTON_PLANKS_PAINTED.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.TRAPDOOR_PLANKS_OAK.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.TRAPDOOR_PLANKS_PAINTED.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.PRESSURE_PLATE_PLANKS_OAK.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.PRESSURE_PLATE_PLANKS_OAK_PAINTED.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.CHEST_PLANKS_OAK.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.CHEST_PLANKS_OAK_PAINTED.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.DOOR_PLANKS_OAK_BOTTOM.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.DOOR_PLANKS_OAK_TOP.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.DOOR_PLANKS_PAINTED_BOTTOM.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.DOOR_PLANKS_PAINTED_TOP.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.SIGN_POST_PLANKS_OAK.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.SIGN_POST_PLANKS_OAK_PAINTED.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.SIGN_WALL_PLANKS_OAK.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);
        Blocks.SIGN_WALL_PLANKS_OAK_PAINTED.withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE);

    }

    public static void registerNewFurnaceFuel() {
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
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.SIGN_SKYROOT.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(Blocks.BUTTON_PLANKS.id(), 75);
        LookupFuelFurnace.instance.addFuelEntry(Blocks.BUTTON_PLANKS_PAINTED.id(), 75);
    }
}
