package teamport.aether;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.EntityPainting;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.entity.animal.MobFireflyCluster;
import net.minecraft.core.enums.MobCategory;
import net.minecraft.core.item.Items;
import net.minecraft.core.net.entity.NetEntityHandler;
import net.minecraft.core.sound.SoundTypes;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.effect.AetherEffects;
import teamport.aether.entity.AetherEntities;
import teamport.aether.entity.aerbunny.MobAerbunny;
import teamport.aether.entity.aerwhale.MobAerwhale;
import teamport.aether.entity.cockatrice.MobCockatrice;
import teamport.aether.entity.moa.MobMoa;
import teamport.aether.entity.moa.MobMoaBlack;
import teamport.aether.entity.moa.MobMoaWhite;
import teamport.aether.entity.phow.MobPhow;
import teamport.aether.entity.phyg.MobPhyg;
import teamport.aether.entity.sheepuff.MobSheepuff;
import teamport.aether.entity.zephyr.MobZephyr;
import teamport.aether.items.AetherItemTags;
import teamport.aether.items.AetherItems;
import teamport.aether.net.NetEntryDart;
import teamport.aether.net.NetEntryLightning;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.util.GameStartEntrypoint;

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

        SILVER = register(new MobFireflyCluster.FireflyColor(10, "fireflySilver", new Biome[]{AetherDimension.AETHER_PLAINS}, new float[]{0.5F, 1.0F, 0.88F}));

        AetherConfig.Setup();
        AetherEntities.init();
        AetherBlocks.init();
        AetherItems.init();
        AetherDimension.init();
        AetherEffects.init();

        NetEntityHandler.registerNetworkEntry(new NetEntryDart(), 33);
        NetEntityHandler.registerNetworkEntry(new NetEntryLightning(), 32);

        SoundTypes.loadSoundsJson(MOD_ID);
//        AetherMod.registerTextures();
    }

    @Override
    public void afterGameStart() {
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.creature).add(new SpawnListEntry(MobPhyg.class, 102));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.creature).add(new SpawnListEntry(MobPhow.class, 102));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.creature).add(new SpawnListEntry(MobSheepuff.class, 102));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.creature).add(new SpawnListEntry(MobAerbunny.class, 102));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.ambientCreature).add(new SpawnListEntry(MobAerwhale.class, 10));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.ambientCreature).add(new SpawnListEntry(MobFireflyCluster.class, 10));

        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.creature).add(new SpawnListEntry(MobMoa.class, 102));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.creature).add(new SpawnListEntry(MobMoaWhite.class, 51));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.creature).add(new SpawnListEntry(MobMoaBlack.class, 26));

        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.monster).add(new SpawnListEntry(MobZephyr.class, 5));
//        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.monster).add(new SpawnListEntry(MobSwet.class, 5));
        AetherDimension.AETHER_PLAINS.getSpawnableList(MobCategory.monster).add(new SpawnListEntry(MobCockatrice.class, 5));

        EntityPainting.addBorder(AetherItems.ZANITE.getDefaultStack(), NamespaceID.getPermanent("aether", "border_zanite"));
        EntityPainting.addBorder(AetherBlocks.BLOCK_GRAVITITE.getDefaultStack(), NamespaceID.getPermanent("aether", "border_gravitite"));
        registerNewFurnaceFuel();
        registerNewTagForItems();

    }

    public static void registerNewTagForItems() {
        Items.TOOL_COMPASS.withTags(new Tag[]{AetherItemTags.TRINKET});
        Items.TOOL_CALENDAR.withTags(new Tag[]{AetherItemTags.TRINKET});
        Items.TOOL_CLOCK.withTags(new Tag[]{AetherItemTags.TRINKET});
        Items.MAP.withTags(new Tag[]{AetherItemTags.TRINKET});
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
        LookupFuelFurnace.instance.addFuelEntry(Blocks.BUTTON_PLANKS.id(), 75);
        LookupFuelFurnace.instance.addFuelEntry(Blocks.BUTTON_PLANKS_PAINTED.id(), 75);
    }
}
