package teamport.aether.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

import java.util.Random;

public class ItemDoorDungeon extends Item {
    private final Block<?> doorBlock;
    private final DoorType doorType;

    public enum DoorType {
        BRONZE(4, 4),
        SILVER(2, 3),
        GOLD(3, 3);

        private final int width;
        private final int height;

        DoorType(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    public ItemDoorDungeon(String name, String namespaceId, int id, Block<?> doorBlock, DoorType doorType) {
        super(name, namespaceId, id);
        this.doorBlock = doorBlock;
        this.doorType = doorType;
    }

    @Override
    public boolean onUseItemOnBlock(ItemStack itemstack, Player entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        if (!world.canPlaceInsideBlock(blockX, blockY, blockZ)) {
            blockX += side.getOffsetX();
            blockY += side.getOffsetY();
            blockZ += side.getOffsetZ();
        }

        int width = doorType.width;
        int height = doorType.height;

        Direction dir = entityplayer.getHorizontalPlacementDirection(side).rotate(2);
        int meta = dir.getHorizontalIndex();

        int xOffset;
        int zOffset;
        switch (dir) {
            case NORTH:
                xOffset = -1;
                zOffset = 0;
                break;
            case SOUTH:
                xOffset = 1;
                zOffset = 0;
                break;
            case EAST:
                xOffset = 0;
                zOffset = -1;
                break;
            case WEST:
                xOffset = 0;
                zOffset = 1;
                break;
            default:
                return false;
        }

        for (int y = 0; y < height; y++) {
            for (int w = 0; w < width; w++) {
                int placeX = blockX + w * xOffset;
                int placeZ = blockZ + w * zOffset;
                if (!this.doorBlock.canPlaceBlockAt(world, placeX, blockY + y, placeZ)) {
                    return false;
                }
            }
        }

        world.noNeighborUpdate = true;
        for (int y = 0; y < height; y++) {
            for (int w = 0; w < width; w++) {
                int placeX = blockX + w * xOffset;
                int placeZ = blockZ + w * zOffset;
                world.setBlockAndMetadataWithNotify(placeX, blockY + y, placeZ, this.doorBlock.id(), meta);
                world.notifyBlocksOfNeighborChange(placeX, blockY + y, placeZ, this.doorBlock.id());
                this.doorBlock.onBlockPlacedByMob(world, placeX, blockY + y, placeZ, side, entityplayer, xPlaced, yPlaced);
            }
        }
        world.noNeighborUpdate = false;
        world.playBlockSoundEffect(entityplayer, blockX + 0.5F, blockY + 0.5F, blockZ + 0.5F, this.doorBlock, EnumBlockSoundEffectType.PLACE);

        itemstack.consumeItem(entityplayer);
        return true;
    }

    @Override
    public void onUseByActivator(ItemStack itemStack, TileEntityActivator activatorBlock, World world, Random random, int blockX, int blockY, int blockZ, double offX, double offY, double offZ, Direction direction) {
        if (!world.canPlaceInsideBlock(blockX, blockY, blockZ)) {
            blockX += direction.getOffsetX();
            blockY += direction.getOffsetY();
            blockZ += direction.getOffsetZ();
        }

        int width = doorType.width;
        int height = doorType.height;

        if (!direction.isHorizontal()) {
            direction = Direction.NORTH;
        }

        Direction dir = direction.rotate(2);
        int meta = dir.getHorizontalIndex();

        int xOffset;
        int zOffset;
        switch (dir) {
            case NORTH:
                xOffset = -1;
                zOffset = 0;
                break;
            case SOUTH:
                xOffset = 1;
                zOffset = 0;
                break;
            case EAST:
                xOffset = 0;
                zOffset = -1;
                break;
            case WEST:
                xOffset = 0;
                zOffset = 1;
                break;
            default:
                return;
        }

        for (int y = 0; y < height; y++) {
            for (int w = 0; w < width; w++) {
                int placeX = blockX + w * xOffset;
                int placeZ = blockZ + w * zOffset;
                if (!this.doorBlock.canPlaceBlockAt(world, placeX, blockY + y, placeZ)) {
                    return;
                }
            }
        }

        world.noNeighborUpdate = true;
        for (int y = 0; y < height; y++) {
            for (int w = 0; w < width; w++) {
                int placeX = blockX + w * xOffset;
                int placeZ = blockZ + w * zOffset;
                world.setBlockAndMetadataWithNotify(placeX, blockY + y, placeZ, this.doorBlock.id(), meta);
                world.notifyBlocksOfNeighborChange(placeX, blockY + y, placeZ, this.doorBlock.id());
                this.doorBlock.onBlockPlacedOnSide(world, placeX, blockY + y, placeZ, direction.getSide(), 0.5, 0.5);
            }
        }
        world.noNeighborUpdate = false;
        world.playBlockSoundEffect(null, blockX + 0.5F, blockY + 0.5F, blockZ + 0.5F, this.doorBlock, EnumBlockSoundEffectType.PLACE);

        itemStack.consumeItem(null);
    }
}
