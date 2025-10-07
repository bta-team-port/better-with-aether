package teamport.aether.entity.monster.mimic;

import net.minecraft.core.block.Blocks;
import teamport.aether.blocks.AetherBlocks;

public enum MimicVariant {
    SKYROOT(0, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 0, "wood"),
    OAK(1, Blocks.CHEST_PLANKS_OAK.id(), 0, "wood"),
    OAK_WHITE(2, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 0, "wood"),
    OAK_ORANGE(3, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 16, "wood"),
    OAK_MAGENTA(4, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 32, "wood"),
    OAK_LIGHTBLUE(5, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 48, "wood"),
    OAK_YELLOW(6, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 64, "wood"),
    OAK_LIME(7, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 80, "wood"),
    OAK_PINK(8, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 96, "wood"),
    OAK_GRAY(9, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 112, "wood"),
    OAK_SILVER(10, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 128, "wood"),
    OAK_CYAN(11, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 144, "wood"),
    OAK_PURPLE(12, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 160, "wood"),
    OAK_BLUE(13, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 176, "wood"),
    OAK_BROWN(14, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 192, "wood"),
    OAK_GREEN(15, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 208, "wood"),
    OAK_RED(16, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 224, "wood"),
    OAK_BLACK(17, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 240, "wood"),
    DUNGEON_BRONZE(18, AetherBlocks.CHEST_DUNGEON_BRONZE.id(), 0, "stone"),
    DUNGEON_SILVER(19, AetherBlocks.CHEST_DUNGEON_SILVER.id(), 0, "stone"),
    DUNGEON_GOLD(20, AetherBlocks.CHEST_DUNGEON_GOLD.id(), 0, "stone");

    private final int id;
    private final int itemID;
    private final int itemMetadata;
    private final String material;

    MimicVariant(int id, int itemID, int metadata, String material) {
        this.id = id;
        this.itemID = itemID;
        this.itemMetadata = metadata;
        this.material = material;
    }

    public int getId() {
        return id;
    }

    public int getItemID() {
        return itemID;
    }

    public int getItemMetadata() {
        return itemMetadata;
    }

    public String getMaterial() {
        return material;
    }

    public static MimicVariant fromId(int id) {
        for (MimicVariant variant : values()) {
            if (variant.id == id) {
                return variant;
            }
        }
        return SKYROOT;
    }
}