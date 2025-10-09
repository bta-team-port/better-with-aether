package teamport.aether.helper;


import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.DyeColor;

import static net.minecraft.core.block.BlockLogicTrapDoor.*;

/**
 * @implNote If you think this class is unnecessary, then you have not worked with metadata
 * long enough.
 * */
public class MetadataHelper {
    public static final byte MASK_DIRECTION = 3;

    public static int getMetadataFromDyeAndDirection(DyeColor dyeColor, Direction dir) {
        return dyeColor.blockMeta << 4 | dir.getHorizontalIndex();
    }

    public static int getMetadataFromTrapdoors(DyeColor dyeColor, boolean isUpper, boolean isOpen, Direction direction){
        int open = isOpen ? 1 : 0;
        int upper = isUpper ? 1 : 0;
        int metadata = dyeColor.blockMeta << 4;
        metadata |= upper << 3;
        metadata |= open << 2;
        return metadata | getTrapDoorMetaForDirection(direction);
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


}
