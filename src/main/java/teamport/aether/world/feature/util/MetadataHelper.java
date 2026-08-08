package teamport.aether.world.feature.util;


import net.minecraft.core.block.BlockLogicTorch;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.DyeColor;
import org.jspecify.annotations.NonNull;

import static net.minecraft.core.block.BlockLogicTrapDoor.*;
import static net.minecraft.core.util.helper.Direction.*;

/**
 * @implNote If you think this class is unnecessary, then you have not worked with metadata
 * long enough.
 */
public class MetadataHelper {
    public static final byte MASK_DIRECTION = 3;

    public static int horizontalIndex(Direction direction) {
        Direction[] arr = Direction.horizontal;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == direction) return i;
        }
        return 0;
    }

    public static int getMetadataFromDyeAndDirection(@NonNull DyeColor dyeColor, Direction direction) {
        return dyeColor.blockMeta << 4 | horizontalIndex(direction);
    }

    public static int getMetadataFromDyeAndLower(@NonNull DyeColor dyeColor, int lowerBits) {
        return dyeColor.blockMeta << 4 | lowerBits;
    }


    public static int maskDirectionHorizontal(int metadata, Direction direction) {
        return (metadata & ~MASK_DIRECTION) | horizontalIndex(direction);
    }


    public static int maskDirectionHorizontal(int metadata, int direction) {
        return (metadata & ~MASK_DIRECTION) | direction;
    }

    /**
     * @param dyeColor  sets the color of the trapdoor
     * @param isUpper   determines whether the trapdoor is placed on the lower or upper part of the block
     * @param isOpen    determines whether the trapdoor is placed as opened or not
     * @param direction determines the direction the trapdoor is opening too
     * @return returns the metadata for the trapdoor opening in the direction set
     * @implNote Sets the metadata for trapdoor using color, the block placement,
     * whether it opens and its opening direction
     */
    public static int setMetadataTrapdoor(@NonNull DyeColor dyeColor, boolean isUpper, boolean isOpen, @NonNull Direction direction) {
        int upper = isUpper ? 1 : 0;
        int open = isOpen ? 1 : 0;
        int metadata = dyeColor.blockMeta << 4;
        metadata |= upper << 3;
        metadata |= open << 2;
        return metadata | getTrapDoorMetaForDirection(direction.opposite());
    }

    /**
     * @param dyeColor  sets the color of trapdoor
     * @param isUpper   determines whether the stair is placed facing upwards or downwards
     * @param direction determines the direction the stair are ascending
     * @return returns the metadata for the stairs in the direction ascending
     * @implNote Sets the metadata for trapdoor using the block placement,
     * whether it opens and its opening direction
     */
    public static int getMetadataStairs(@NonNull DyeColor dyeColor, boolean isUpper, Direction direction) {
        int upper = isUpper ? 1 : 0;
        int metadata = dyeColor.blockMeta << 4;
        metadata |= upper << 3;
        return metadata | getStairMetadataFromDirection(direction);
    }

    /**
     * @implNote The direction is the ascending direction of the trapdoors.
     */
    public static int getTrapDoorMetaForDirection(@NonNull Direction direction) {
        return switch (direction) {
            case EAST -> DIRECTION_EAST;
            case WEST -> DIRECTION_WEST;
            case SOUTH -> DIRECTION_SOUTH;
            default -> DIRECTION_NORTH;
        };
    }

    /**
     * @implNote The direction is the ascending direction of the trapdoors.
     */
    public static Direction getTrapDoorDirectionForMeta(int metadata) {
        return switch (metadata) {
            case DIRECTION_SOUTH -> SOUTH;
            case DIRECTION_NORTH -> NORTH;
            case DIRECTION_EAST -> EAST;
            case DIRECTION_WEST -> WEST;
            default -> NONE;
        };
    }

    /**
     * @implNote The direction is the ascending direction of the torches.
     */
    public static int getTorchMetadataFromDirection(@NonNull Direction direction) {
        return switch (direction) {
            case NORTH -> BlockLogicTorch.SIDE_NORTH;
            case EAST -> BlockLogicTorch.SIDE_EAST;
            case SOUTH -> BlockLogicTorch.SIDE_SOUTH;
            case WEST -> BlockLogicTorch.SIDE_WEST;
            case DOWN -> BlockLogicTorch.SIDE_BOTTOM;
            case UP -> BlockLogicTorch.SIDE_TOP;
            default -> BlockLogicTorch.SIDE_NONE;
        };
    }

    /**
     * @implNote The direction is the ascending direction of the torches.
     */
    public static Direction getTorchDirectionFromMetadata(int metadata) {
        return switch (metadata) {
            case BlockLogicTorch.SIDE_NORTH -> NORTH;
            case BlockLogicTorch.SIDE_EAST -> EAST;
            case BlockLogicTorch.SIDE_SOUTH -> SOUTH;
            case BlockLogicTorch.SIDE_WEST -> WEST;
            case BlockLogicTorch.SIDE_BOTTOM -> DOWN;
            case BlockLogicTorch.SIDE_TOP -> UP;
            default -> NONE;
        };
    }

    /**
     * @implNote The direction is the ascending direction of the stairs.
     * Importantly this differs from how BlockLogicStairs implements direction, this due to BTA placement setting and many layers of abstraction.
     */
    public static int getStairMetadataFromDirection(@NonNull Direction direction) {
        return switch (direction) {
            case EAST -> 0;
            case WEST -> 1;
            case SOUTH -> 2;
            default -> 3;
        };
    }

    /**
     * @implNote The metadata of the stairs ascending direction.
     * Importantly this differs from how BlockLogicStairs implements direction, this due to BTA placement setting and many layers of abstraction.
     */
    public static Direction getStairDirectionFromMetadata(int metadata) {
        return switch (metadata) {
            case 0 -> EAST;
            case 1 -> WEST;
            case 2 -> SOUTH;
            default -> NORTH;
        };
    }
}
