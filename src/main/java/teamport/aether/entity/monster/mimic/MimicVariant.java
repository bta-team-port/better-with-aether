package teamport.aether.entity.monster.mimic;

import net.minecraft.core.block.Blocks;
import teamport.aether.blocks.AetherBlocks;

public enum MimicVariant {
    SKYROOT(0, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 0),
    OAK(1, Blocks.CHEST_PLANKS_OAK.id(), 0),
    OAK_WHITE(2, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 0),
    OAK_ORANGE(3, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 16),
    OAK_MAGENTA(4, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 32),
    OAK_LIGHTBLUE(5, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 48),
    OAK_YELLOW(6, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 64),
    OAK_LIME(7, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 80),
    OAK_PINK(8, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 96),
    OAK_GRAY(9, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 112),
    OAK_SILVER(10, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 128),
    OAK_CYAN(11, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 144),
    OAK_PURPLE(12,Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 160),
    OAK_BLUE(13,Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 176),
    OAK_BROWN(14, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 192),
    OAK_GREEN(15,Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 208),
    OAK_RED(16, Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 224),
    OAK_BLACK(17,Blocks.CHEST_PLANKS_OAK_PAINTED.id(), 240),
    DUNGEON_BRONZE(18,AetherBlocks.CHEST_DUNGEON_BRONZE.id(), 0),
    DUNGEON_SILVER(19,AetherBlocks.CHEST_DUNGEON_BRONZE.id(), 0),
    DUNGEON_GOLD(20,AetherBlocks.CHEST_DUNGEON_BRONZE.id(), 0);

    private final int id;
    private final int blockID;
    private final int metadata;

    MimicVariant(int id, int blockID, int metadata) {
        this.id = id;
        this.blockID = blockID;
        this.metadata = metadata;
    }

    public int getId() {
        return id;
    }

    public int getBlockID() {
        return blockID;
    }

    public int getMetadata() {
        return metadata;
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
