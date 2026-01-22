package teamport.aether.entity.monster.mimic;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.DyeColor;
import teamport.aether.block.AetherBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static teamport.aether.entity.monster.mimic.MimicEntry.mimicEntry;

public class MimicRegistry {
    ///  Static values are initialed in the order of declaration, default has to be the first value!
    public static final MimicEntry DEFAULT = mimicEntry(0, "skyroot", AetherBlocks.CHEST_MIMIC_SKYROOT.id(), 0, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 0);
    public static final MimicRegistry instance = new MimicRegistry();
    @SuppressWarnings("java:S116")
    protected final List<MimicEntry> MIMIC_ENTRY_LIST = new ArrayList<>();

    public static void init() {/* just to load this class*/}

    protected MimicRegistry() {
        this.register();
    }

    private void register() {
        int variantSkinID = 1;
        this.addEntry(DEFAULT);
        addEntry(variantSkinID++, "oak", AetherBlocks.CHEST_MIMIC_OAK, Blocks.CHEST_PLANKS_OAK);
        for (DyeColor dye : DyeColor.blockOrderedColors()) {
            int meta = dye.blockMeta << 4;
            addEntry(variantSkinID++, "oak_" + dye.colorID, AetherBlocks.CHEST_MIMIC_OAK_PAINTED.id(), meta, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), meta);
        }
        addEntry(variantSkinID++, "dungeon_bronze", AetherBlocks.CHEST_MIMIC_BRONZE.id(), 0, AetherBlocks.CHEST_DUNGEON_BRONZE.id(), 0);
        addEntry(variantSkinID++, "dungeon_silver", AetherBlocks.CHEST_MIMIC_SILVER.id(), 0, AetherBlocks.CHEST_DUNGEON_SILVER.id(), 0);
        addEntry(variantSkinID++, "dungeon_gold", AetherBlocks.CHEST_MIMIC_GOLD.id(), 0, AetherBlocks.CHEST_DUNGEON_GOLD.id(), 0);
        for (DyeColor dye : DyeColor.blockOrderedColors()) {
            int meta = dye.blockMeta << 4;
            addEntry(variantSkinID++, "skyroot_" + dye.colorID, AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.id(), meta, AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED.id(), meta);
        }
    }

    protected static void addEntry(String pathName, int mimicChestId, int mimicChestMetadata, int chestID, int chestMetadata) {
        instance.MIMIC_ENTRY_LIST.add(mimicEntry(instance.MIMIC_ENTRY_LIST.size(), pathName, mimicChestId, mimicChestMetadata, chestID, chestMetadata));
    }

    protected void addEntry(MimicEntry entry) {
        this.MIMIC_ENTRY_LIST.add(entry);
    }

    protected void addEntry(int mimicVariant, String pathName, int mimicChestId, int mimicChestMetadata, int chestID, int chestMetadata) {
        this.MIMIC_ENTRY_LIST.add(mimicEntry(mimicVariant, pathName, mimicChestId, mimicChestMetadata, chestID, chestMetadata));
    }

    protected void addEntry(int mimicVariant, String pathName, Block<?> mimicChest, Block<?> chest) {
        this.MIMIC_ENTRY_LIST.add(mimicEntry(mimicVariant, pathName, mimicChest.id(), 0, chest.id(), 0));
    }

    protected static MimicEntry getMimicVariantByID(int mimicVariant) {
        mimicVariant = mimicVariant % instance.MIMIC_ENTRY_LIST.size();

        for (MimicEntry variant : MimicRegistry.instance.MIMIC_ENTRY_LIST) {
            if (variant.mimicVariant == mimicVariant) {
                return variant;
            }
        }
        return DEFAULT;
    }

    public static MimicEntry getMimicVariantByName(String name) {
        for (MimicEntry variant : MimicRegistry.instance.MIMIC_ENTRY_LIST) {
            if (variant.getPathName().equalsIgnoreCase(name)) {
                return variant;
            }
        }
        return DEFAULT;
    }

    public static MimicEntry getMimicVariantByMimicChest(int mimicChestID, int mimicMetadata) {
        for (MimicEntry variant : MimicRegistry.instance.MIMIC_ENTRY_LIST) {
            if (variant.mimicChestID == mimicChestID && variant.mimicChestMetadata == mimicMetadata) {
                return variant;
            }
        }
        return DEFAULT;
    }

    public static MimicEntry getMimicVariantByChest(int chestID, int metadata) {
        for (MimicEntry variant : MimicRegistry.instance.MIMIC_ENTRY_LIST) {
            if (variant.chestID == chestID && variant.chestMetadata == metadata) {
                return variant;
            }
        }
        return DEFAULT;
    }

    public static MimicEntry getRandomEntry(Random random) {
        return instance.MIMIC_ENTRY_LIST.get(random.nextInt(instance.MIMIC_ENTRY_LIST.size()));
    }

    public static int getLength() {
        return instance.MIMIC_ENTRY_LIST.size();
    }

    public static int getPrevValue(int index) {
        return MimicRegistry.getValue(index - 1);
    }

    public static int getNextValue(int index) {
        return MimicRegistry.getValue(index + 1);
    }

    private static int getValue(int index) {
        return instance.MIMIC_ENTRY_LIST.get(Math.floorMod(index, instance.MIMIC_ENTRY_LIST.size())).getMimicVariant();
    }

}
