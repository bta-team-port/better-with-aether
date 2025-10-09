package teamport.aether.helper;


import net.minecraft.core.block.BlockLogicTorch;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.DyeColor;

import static net.minecraft.core.block.BlockLogicTrapDoor.*;

/**
 * @implNote If you think this class is unnecessary, then you have not worked with metadata
 * long enough.
 * */
public class MetadataHelper {
    public static final byte MASK_DIRECTION = 3;

    public static int getMetadataFromDyeAndDirection(DyeColor dyeColor, Direction direction) {
        return dyeColor.blockMeta << 4 | direction.getHorizontalIndex();
    }

    public static int getMetadataFromDyeAndLower(DyeColor dyeColor, int lowerBits) {
        return dyeColor.blockMeta << 4 | lowerBits;
    }

    public static int getMetadataTrapdoor(DyeColor dyeColor, boolean isUpper, boolean isOpen, Direction direction){
        int upper = isUpper ? 1 : 0;
        int open = isOpen ? 1 : 0;
        int metadata = dyeColor.blockMeta << 4;
        metadata |= upper << 3;
        metadata |= open << 2;
        return metadata | getTrapDoorMetaForDirection(direction);
    }

    public static int getMetaDataDefault(DyeColor dyeColor, boolean isUpper, boolean isOpen, Direction direction){
        int upper = isUpper ? 1 : 0;
        int open = isOpen ? 1 : 0;
        int metadata = dyeColor.blockMeta << 4;
        metadata |= upper << 3;
        metadata |= open << 2;
        return metadata | direction.getHorizontalIndex();
    }

    public static int maskDirectionHorizontal(int metadata, Direction direction) {
       return (metadata & ~MASK_DIRECTION) | direction.getHorizontalIndex();
    }


    public static int maskDirectionHorizontal(int metadata, int direction) {
        return (metadata & ~MASK_DIRECTION) | direction;
    }

    public static int getTrapDoorMetaForDirection(Direction dir) {
        switch (dir) {
            case NORTH:
                return DIRECTION_NORTH;
            case EAST:
                return DIRECTION_EAST;
            case WEST:
                return DIRECTION_WEST;
            case SOUTH:
            default:
                return DIRECTION_SOUTH;
        }
    }

    public static Direction getTrapDoorDirectionForMeta(int meta) {
        switch (meta & 3) {
            case DIRECTION_SOUTH:
                return Direction.SOUTH;
            case DIRECTION_NORTH:
                return Direction.NORTH;
            case DIRECTION_EAST:
                return Direction.EAST;
            case DIRECTION_WEST:
                return Direction.WEST;
            default:
                return Direction.NONE;
        }
    }

    public static int getMetadataTorchPlacement(Direction direction){
        switch (direction){
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


}
