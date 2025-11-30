package teamport.aether.entity.monster.mimic;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.DyeColor;
import teamport.aether.blocks.AetherBlocks;

import java.util.ArrayList;
import java.util.List;

import static teamport.aether.entity.monster.mimic.MimicEntry.mimicEntry;

public class MimicRegistry {
    public static final MimicRegistry instance = new MimicRegistry();
    @SuppressWarnings("java:S116")
    public final List<MimicEntry> MIMIC_ENTRY_LIST = new ArrayList<>();
    public static final MimicEntry DEFAULT = mimicEntry(0, AetherBlocks.CHEST_MIMIC_SKYROOT.id(), 0, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 0);

    public static void init(){/* just to load this class*/}

    protected MimicRegistry() {
        this.register();
    }

    private void register() {
        int variantSkinID = 0;
        addEntry(variantSkinID++, AetherBlocks.CHEST_MIMIC_SKYROOT, AetherBlocks.CHEST_PLANKS_SKYROOT);
        addEntry(variantSkinID++, AetherBlocks.CHEST_MIMIC_OAK, Blocks.CHEST_PLANKS_OAK);
        for (DyeColor dye : DyeColor.blockOrderedColors()) {
            int meta = dye.blockMeta << 4;
            addEntry(variantSkinID++, AetherBlocks.CHEST_MIMIC_OAK_PAINTED.id(), meta, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), meta);
        }
        addEntry(variantSkinID++, AetherBlocks.CHEST_MIMIC_BRONZE.id(), 0, AetherBlocks.CHEST_DUNGEON_BRONZE.id(), 0);
        addEntry(variantSkinID++, AetherBlocks.CHEST_MIMIC_SILVER.id(), 0, AetherBlocks.CHEST_DUNGEON_SILVER.id(), 0);
        addEntry(variantSkinID++, AetherBlocks.CHEST_MIMIC_GOLD.id(), 0, AetherBlocks.CHEST_DUNGEON_GOLD.id(), 0);
        for (DyeColor dye : DyeColor.blockOrderedColors()) {
            int meta = dye.blockMeta << 4;
            addEntry(variantSkinID++, AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.id(), meta, AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED.id(), meta);
        }
    }

    public void addEntry(int mimicVariant, int mimicChestId, int mimicChestMetadata, int chestID, int chestMetadata) {
        this.MIMIC_ENTRY_LIST.add(mimicEntry(mimicVariant, mimicChestId, mimicChestMetadata, chestID, chestMetadata));
    }

    public void addEntry(int mimicVariant, Block<?> mimicChest, Block<?> chest) {
        this.MIMIC_ENTRY_LIST.add(mimicEntry(mimicVariant, mimicChest.id(), 0, chest.id(), 0));
    }

    public static MimicEntry getMimicVariantByID(int mimicVariant) {
        mimicVariant = mimicVariant % instance.MIMIC_ENTRY_LIST.size();

        for (MimicEntry variant : MimicRegistry.instance.MIMIC_ENTRY_LIST) {
            if (variant.mimicVariant == mimicVariant) {
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


}
