package teamport.aether.helper;


import net.minecraft.core.block.BlockLogicTorch;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.DyeColor;

import static net.minecraft.core.block.BlockLogicTrapDoor.*;
import static net.minecraft.core.util.helper.Direction.*;

/**
 * @implNote If you think this class is unnecessary, then you have not worked with metadata
 * long enough.
 */
public class MetadataHelper {
    public static final byte MASK_DIRECTION = 3;

    public static int getMetadataFromDyeAndDirection(DyeColor dyeColor, Direction direction) {
        return dyeColor.blockMeta << 4 | direction.getHorizontalIndex();
    }

    public static int getMetadataFromDyeAndLower(DyeColor dyeColor, int lowerBits) {
        return dyeColor.blockMeta << 4 | lowerBits;
    }


    public static int maskDirectionHorizontal(int metadata, Direction direction) {
        return (metadata & ~MASK_DIRECTION) | direction.getHorizontalIndex();
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
    public static int setMetadataTrapdoor(DyeColor dyeColor, boolean isUpper, boolean isOpen, Direction direction) {
        int upper = isUpper ? 1 : 0;
        int open = isOpen ? 1 : 0;
        int metadata = dyeColor.blockMeta << 4;
        metadata |= upper << 3;
        metadata |= open << 2;
        return metadata | getTrapDoorMetaForDirection(direction.getOpposite());
    }

    /**
     * @param dyeColor  sets the color of trapdoor
     * @param isUpper   determines whether the stair is placed facing upwards or downwards
     * @param direction determines the direction the stair are ascending
     * @return returns the metadata for the stairs in the direction ascending
     * @implNote Sets the metadata for trapdoor using the block placement,
     * whether it opens and its opening direction
     */
    public static int getMetadataStairs(DyeColor dyeColor, boolean isUpper, Direction direction) {
        int upper = isUpper ? 1 : 0;
        int metadata = dyeColor.blockMeta << 4;
        metadata |= upper << 3;
        return metadata | getStairMetadataFromDirection(direction);
    }

    /**
     * @implNote The direction is the ascending direction of the trapdoors.
     */
    public static int getTrapDoorMetaForDirection(Direction direction) {
        switch (direction) {
            case EAST:
                return DIRECTION_EAST;
            case WEST:
                return DIRECTION_WEST;
            case SOUTH:
                return DIRECTION_SOUTH;
            case NORTH:
            default:
                return DIRECTION_NORTH;
        }
    }

    /**
     * @implNote The direction is the ascending direction of the trapdoors.
     */
    public static Direction getTrapDoorDirectionForMeta(int metadata) {
        switch (metadata) {
            case DIRECTION_SOUTH:
                return SOUTH;
            case DIRECTION_NORTH:
                return NORTH;
            case DIRECTION_EAST:
                return EAST;
            case DIRECTION_WEST:
                return WEST;
            default:
                return NONE;
        }
    }

    /**
     * @implNote The direction is the ascending direction of the torches.
     */
    public static int getTorchMetadataFromDirection(Direction direction) {
        switch (direction) {
            case NORTH:
                return BlockLogicTorch.SIDE_NORTH;
            case EAST:
                return BlockLogicTorch.SIDE_EAST;
            case SOUTH:
                return BlockLogicTorch.SIDE_SOUTH;
            case WEST:
                return BlockLogicTorch.SIDE_WEST;
            case DOWN:
                return BlockLogicTorch.SIDE_BOTTOM;
            case UP:
                return BlockLogicTorch.SIDE_TOP;
            case NONE:
            default:
                return BlockLogicTorch.SIDE_NONE;
        }
    }

    /**
     * @implNote The direction is the ascending direction of the torches.
     */
    public static Direction getTorchDirectionFromMetadata(int metadata) {
        switch (metadata) {
            case BlockLogicTorch.SIDE_NORTH:
                return NORTH;
            case BlockLogicTorch.SIDE_EAST:
                return EAST;
            case BlockLogicTorch.SIDE_SOUTH:
                return SOUTH;
            case BlockLogicTorch.SIDE_WEST:
                return WEST;
            case BlockLogicTorch.SIDE_BOTTOM:
                return DOWN;
            case BlockLogicTorch.SIDE_TOP:
                return UP;
            case BlockLogicTorch.SIDE_NONE:
            default:
                return NONE;
        }
    }

    /**
     * @implNote The direction is the ascending direction of the stairs.
     * Importantly this differs from how BlockLogicStairs implements direction, this due to BTA placement setting and many layers of abstraction.
     */
    public static int getStairMetadataFromDirection(Direction direction) {
        switch (direction) {
            case EAST:
                return 0;
            case WEST:
                return 1;
            case SOUTH:
                return 2;
            case NORTH:
            default:
                return 3;
        }
    }

    /**
     * @implNote The metadata of the stairs ascending direction.
     * Importantly this differs from how BlockLogicStairs implements direction, this due to BTA placement setting and many layers of abstraction.
     */
    public static Direction getStairDirectionFromMetadata(int metadata) {
        switch (metadata) {
            case 0:
                return EAST;
            case 1:
                return WEST;
            case 2:
                return SOUTH;
            case 3:
            default:
                return NORTH;
        }
    }
}
