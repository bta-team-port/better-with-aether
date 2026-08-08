package teamport.aether.world.feature.util;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.*;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.util.helper.Axis;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.helper.Pair;
import teamport.aether.helper.unboxed.IntPair;

import static net.minecraft.core.util.helper.Direction.NORTH;
import static teamport.aether.world.feature.util.MetadataHelper.*;

public class WorldFeatureBlock extends WorldFeaturePoint {
    private int blockId;
    private int metadata;
    private boolean withNotify;


    WorldFeatureBlock(int x, int y, int z, int blockId, int metadata, boolean withNotify) {
        super(x, y, z);
        this.blockId = blockId;
        this.metadata = metadata;
        this.withNotify = withNotify;
    }

    WorldFeatureBlock(int x, int y, int z, @NonNull Pair<Integer, Integer> blockAndMeta, boolean withNotify) {
        super(x, y, z);
        this.blockId = blockAndMeta.first();
        this.metadata = blockAndMeta.second();
        this.withNotify = withNotify;
    }

    WorldFeatureBlock(int x, int y, int z, @NonNull IntPair blockAndMeta, boolean withNotify) {
        super(x, y, z);
        this.blockId = blockAndMeta.first();
        this.metadata = blockAndMeta.second();
        this.withNotify = withNotify;
    }

    public static WorldFeatureBlock wfb(@NonNull WorldFeaturePoint point, int blockID, int metadata, boolean withNotify) {
        return new WorldFeatureBlock(point.getX(), point.getY(), point.getZ(), blockID, metadata, withNotify);
    }

    public static @NonNull WorldFeatureBlock wfb(@NonNull WorldFeaturePoint point, int blockID, int metadata) {
        return new WorldFeatureBlock(point.getX(), point.getY(), point.getZ(), blockID, metadata, false);
    }

    public static @NonNull WorldFeatureBlock wfb(@NonNull WorldFeaturePoint point) {
        return new WorldFeatureBlock(point.getX(), point.getY(), point.getZ(), 0, 0, false);
    }

    public static @NonNull WorldFeatureBlock wfb(int x, int y, int z) {
        return new WorldFeatureBlock(x, y, z, 0, 0, false);
    }

    public static @NonNull WorldFeatureBlock wfb(int x, int y, int z, int blockID) {
        return new WorldFeatureBlock(x, y, z, blockID, 0, false);
    }

    public static @NonNull WorldFeatureBlock wfb(int x, int y, int z, int blockID, int metadata) {
        return new WorldFeatureBlock(x, y, z, blockID, metadata, false);
    }

    public static @NonNull WorldFeatureBlock wfb(int x, int y, int z, int blockID, boolean withNotify) {
        return new WorldFeatureBlock(x, y, z, blockID, 0, withNotify);
    }

    public static @NonNull WorldFeatureBlock wfb(int x, int y, int z, int blockID, int metadata, boolean withNotify) {
        return new WorldFeatureBlock(x, y, z, blockID, metadata, withNotify);
    }

    public static @NonNull WorldFeatureBlock wfb(int x, int y, int z, IntPair blockAndMeta, boolean withNotify) {
        return new WorldFeatureBlock(x, y, z, blockAndMeta, withNotify);
    }

    public static @NonNull WorldFeatureBlock wfb(int x, int y, int z, Pair<Integer, Integer> blockAndMeta, boolean withNotify) {
        return new WorldFeatureBlock(x, y, z, blockAndMeta, withNotify);
    }

    public void place(World world) {
        this.place(world, getX(), getY(), getZ());
    }

    private void place(World world, int ix, int iy, int iz) {
        if (this.withNotify) {
            world.setBlockAndMetadataWithNotify(ix, iy, iz, this.blockId, this.metadata);
        } else {
            world.setBlockAndMetadata(ix, iy, iz, this.blockId, this.metadata);
        }

        Block<?> block = Blocks.getBlock(this.blockId);
        if (block.isEntityTile && block.entitySupplier != null && world.getTileEntity(ix, iy, iz) == null) {
            TileEntity tileEntity = block.entitySupplier.get();
            world.setTileEntity(ix, iy, iz, tileEntity);
        }
    }

    @Override
    public WorldFeaturePoint rotateYAroundPivot(WorldFeaturePoint pivotPoint, @NonNull Direction direction) {
        super.rotateYAroundPivot(pivotPoint, direction);
        int rotateAmount = horizontalIndex(direction) - horizontalIndex(NORTH);

        Block<?> block = Blocks.getBlock(this.blockId);

        BlockLogic logic = block.getLogic();

        if (logic instanceof BlockLogicFenceGate) {
            int indexDirection = this.metadata & BlockLogicFenceGate.MASK_DIRECTION;
            Direction currentDirection = Direction.horizontal[indexDirection];
            this.metadata = replaceBits(this.metadata, BlockLogicFenceGate.MASK_DIRECTION,
                horizontalIndex(rotateY(currentDirection, rotateAmount)));
        } else if (logic instanceof BlockLogicStairs) {
            int indexDirection = this.metadata & BlockLogicStairs.MASK_ROTATION_HORIZONTAL;
            Direction currentDirection = getStairDirectionFromMetadata(indexDirection);
            this.metadata = replaceBits(this.metadata, BlockLogicStairs.MASK_ROTATION_HORIZONTAL,
                getStairMetadataFromDirection(rotateY(currentDirection, rotateAmount)));
        } else if (logic instanceof BlockLogicTorch) {
            int indexDirection = this.metadata & BlockLogicTorch.MASK_DIRECTION;
            Direction currentDirection = getTorchDirectionFromMetadata(indexDirection);
            if (currentDirection != Direction.NONE) {
                this.metadata = replaceBits(this.metadata, BlockLogicTorch.MASK_DIRECTION,
                    getTorchMetadataFromDirection(rotateY(currentDirection, rotateAmount)));
            }
        } else if (logic instanceof BlockLogicTrapDoor) {
            int indexDirection = this.metadata & BlockLogicTrapDoor.MASK_DIRECTION;
            Direction currentDirection = getTrapDoorDirectionForMeta(indexDirection);
            this.metadata = replaceBits(this.metadata, BlockLogicTrapDoor.MASK_DIRECTION,
                getTrapDoorMetaForDirection(rotateY(currentDirection, rotateAmount)));
        } else if (logic instanceof BlockLogicRotatable) {
            Direction currentDirection = BlockLogicRotatable.getDirectionFromMeta(this.metadata);
            if (currentDirection != Direction.NONE) {
                this.metadata = BlockLogicRotatable.setDirection(this.metadata, rotateY(currentDirection, rotateAmount));
            }
        } else if (logic instanceof BlockLogicVeryRotatable) {
            Direction currentDirection = BlockLogicVeryRotatable.metaToDirection(this.metadata);
            if (currentDirection != Direction.NONE) {
                this.metadata = BlockLogicVeryRotatable.setDirection(this.metadata, rotateY(currentDirection, rotateAmount));
            }
        } else if (logic instanceof BlockLogicFullyRotatable) {
            Direction currentDirection = BlockLogicFullyRotatable.metaToDirection(this.metadata);
            if (currentDirection != Direction.NONE) {
                this.metadata = replaceBits(this.metadata, BlockLogicFullyRotatable.MASK_DIRECTION,
                    BlockLogicFullyRotatable.directionToMeta(rotateY(currentDirection, rotateAmount)));
            }
        } else if (logic instanceof BlockLogicAxisAligned) {
            int axisMetadata = this.metadata & BlockLogicAxisAligned.MASK_DIRECTION;
            if (axisMetadata != BlockLogicAxisAligned.MASK_DIRECTION) {
                Axis currentAxis = BlockLogicAxisAligned.metaToAxis(axisMetadata);
                Direction axisDirection = currentAxis == Axis.X ? Direction.EAST
                    : currentAxis == Axis.Z ? Direction.SOUTH : Direction.UP;
                Axis newAxis = rotateY(axisDirection, rotateAmount).axis();
                this.metadata = replaceBits(this.metadata, BlockLogicAxisAligned.MASK_DIRECTION,
                    BlockLogicAxisAligned.axisToMeta(newAxis));
            }
        } else if (logic instanceof BlockLogicLadder ladder) {
            Side currentSide = ladder.getSideFromMeta(this.metadata);
            if (currentSide.isHorizontal()) {
                this.metadata = ladder.getMetaForSide(rotateY(currentSide.direction(), rotateAmount).side());
            }
        } else if (logic instanceof BlockLogicDoor) {
            int currentRotation = this.metadata & BlockLogicDoor.MASK_ROTATION;
            this.metadata = replaceBits(this.metadata, BlockLogicDoor.MASK_ROTATION,
                (currentRotation + rotateAmount) & BlockLogicDoor.MASK_ROTATION);
        } else if (logic instanceof BlockLogicButton) {
            int currentOrientation = this.metadata & BlockLogicButton.MASK_DIRECTION;
            Direction currentDirection = getButtonDirection(currentOrientation);
            if (currentDirection != Direction.NONE) {
                this.metadata = replaceBits(this.metadata, BlockLogicButton.MASK_DIRECTION,
                    getButtonMetadata(rotateY(currentDirection, rotateAmount)));
            }
        } else if (logic instanceof BlockLogicPressurePlate) {
            Side currentSide = BlockLogicPressurePlate.sideFromMeta(this.metadata);
            if (currentSide != Side.NONE) {
                this.metadata = BlockLogicPressurePlate.setSide(this.metadata,
                    rotateY(currentSide.direction(), rotateAmount).side());
            }
        } else if (logic instanceof BlockLogicSign sign) {
            int currentOrientation = this.metadata & BlockLogicSign.MASK_SIDE;
            if (sign.isFreeStanding) {
                this.metadata = replaceBits(this.metadata, BlockLogicSign.MASK_SIDE,
                    (currentOrientation + rotateAmount * 4) & BlockLogicSign.MASK_SIDE);
            } else {
                Side currentSide = Side.fromId(currentOrientation);
                if (currentSide.isHorizontal()) {
                    this.metadata = replaceBits(this.metadata, BlockLogicSign.MASK_SIDE,
                        rotateY(currentSide.direction(), rotateAmount).side().id);
                }
            }
        } else if (logic instanceof BlockLogicLever) {
            int currentRotation = BlockLogicLever.getRotation(this.metadata);
            this.metadata = replaceBits(this.metadata, BlockLogicLever.MASK_ROTATION,
                rotateLever(currentRotation, rotateAmount));
        } else if (logic instanceof BlockLogicRepeater) {
            Direction currentDirection = BlockLogicRepeater.getSideFromMeta(this.metadata).direction();
            this.metadata = replaceBits(this.metadata, BlockLogicRepeater.MASK_DIRECTION,
                getRepeaterMetadata(rotateY(currentDirection, rotateAmount)));
        } else if (logic instanceof BlockLogicBed) {
            int currentDirection = BlockLogicBed.DIRECTION.get(this.metadata);
            if (currentDirection >= 0 && currentDirection < BlockLogicBed.footToHeadMap.length) {
                Side currentSide = BlockLogicBed.footToHeadMap[currentDirection];
                Side newSide = rotateY(currentSide.direction(), rotateAmount).side();
                for (int i = 0; i < BlockLogicBed.footToHeadMap.length; i++) {
                    if (BlockLogicBed.footToHeadMap[i] == newSide) {
                        this.metadata = BlockLogicBed.DIRECTION.set(this.metadata, i);
                        break;
                    }
                }
            }
        }

        return this;
    }

    private static int replaceBits(int metadata, int mask, int value) {
        return (metadata & ~mask) | (value & mask);
    }

    private static Direction rotateY(@NonNull Direction direction, int rotateAmount) {
        return direction.rotate(Axis.Y, -rotateAmount);
    }

    private static Direction getButtonDirection(int metadata) {
        return switch (metadata) {
            case BlockLogicButton.SIDE_WEST -> Direction.WEST;
            case BlockLogicButton.SIDE_EAST -> Direction.EAST;
            case BlockLogicButton.SIDE_NORTH -> Direction.NORTH;
            case BlockLogicButton.SIDE_SOUTH -> Direction.SOUTH;
            case BlockLogicButton.SIDE_TOP -> Direction.UP;
            case BlockLogicButton.SIDE_BOTTOM -> Direction.DOWN;
            default -> Direction.NONE;
        };
    }

    private static int getButtonMetadata(@NonNull Direction direction) {
        return switch (direction) {
            case WEST -> BlockLogicButton.SIDE_WEST;
            case EAST -> BlockLogicButton.SIDE_EAST;
            case NORTH -> BlockLogicButton.SIDE_NORTH;
            case SOUTH -> BlockLogicButton.SIDE_SOUTH;
            case UP -> BlockLogicButton.SIDE_TOP;
            case DOWN -> BlockLogicButton.SIDE_BOTTOM;
            default -> 0;
        };
    }

    private static int rotateLever(int rotation, int rotateAmount) {
        Direction direction;
        switch (rotation) {
            case BlockLogicLever.ROTATION_EAST: direction = Direction.EAST; break;
            case BlockLogicLever.ROTATION_WEST: direction = Direction.WEST; break;
            case BlockLogicLever.ROTATION_SOUTH: direction = Direction.SOUTH; break;
            case BlockLogicLever.ROTATION_NORTH: direction = Direction.NORTH; break;
            case BlockLogicLever.ROTATION_TOP_NS:
            case BlockLogicLever.ROTATION_TOP_WE:
            case BlockLogicLever.ROTATION_BOTTOM_NS:
            case BlockLogicLever.ROTATION_BOTTOM_WE:
                if ((rotateAmount & 1) == 0) return rotation;
                if (rotation == BlockLogicLever.ROTATION_TOP_NS) return BlockLogicLever.ROTATION_TOP_WE;
                if (rotation == BlockLogicLever.ROTATION_TOP_WE) return BlockLogicLever.ROTATION_TOP_NS;
                if (rotation == BlockLogicLever.ROTATION_BOTTOM_NS) return BlockLogicLever.ROTATION_BOTTOM_WE;
                return BlockLogicLever.ROTATION_BOTTOM_NS;
            default: return rotation;
        }

        return switch (rotateY(direction, rotateAmount)) {
            case EAST -> BlockLogicLever.ROTATION_EAST;
            case WEST -> BlockLogicLever.ROTATION_WEST;
            case SOUTH -> BlockLogicLever.ROTATION_SOUTH;
            case NORTH -> BlockLogicLever.ROTATION_NORTH;
            default -> rotation;
        };
    }

    private static int getRepeaterMetadata(@NonNull Direction direction) {
        return switch (direction) {
            case SOUTH -> BlockLogicRepeater.DIRECTION_SOUTH;
            case WEST -> BlockLogicRepeater.DIRECTION_WEST;
            case NORTH -> BlockLogicRepeater.DIRECTION_NORTH;
            case EAST -> BlockLogicRepeater.DIRECTION_EAST;
            default -> 0;
        };
    }


    @Override
    public WorldFeatureBlock rotateYAroundPivot(int pivotX, int pivotY, int pivotZ, float angle) {
        super.rotateYAroundPivot(pivotX, pivotY, pivotZ, angle);

        return this;
    }

    @Override
    public CompoundTag toCompoundTag() {
        CompoundTag tag = super.toCompoundTag();
        tag.putInt("blockID", blockId);
        tag.putInt("blockMetadata", metadata);
        tag.putBoolean("withNotify", withNotify);
        return tag;
    }

    public static WorldFeatureBlock fromCompoundTag(CompoundTag tag) {
        return wfb(WorldFeaturePoint.fromCompoundTag(tag), tag.getInteger("blockID"), tag.getInteger("blockMetadata"), tag.getBoolean("withNotify"));
    }
    public int getBlockId() {
        return blockId;
    }
    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }
    public int getMetadata() {
        return metadata;
    }
    public void setMetadata(int metadata) {
        this.metadata = metadata;
    }
    public boolean isWithNotify() {
        return withNotify;
    }
    public void setWithNotify(boolean withNotify) {
        this.withNotify = withNotify;
    }

}
