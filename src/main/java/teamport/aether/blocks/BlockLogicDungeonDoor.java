package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockLogicDungeonDoor extends BlockLogicRotatable {
    public BlockLogicDungeonDoor(Block<?> block) {
        super(block, Material.metal);
    }

    public enum DoorDungeonSide {
        LEFT,
        MIDDLE,
        RIGHT;
    }

    public enum DoorDungeonHeight {
        TOP,
        MIDDLE,
        BOTTOM;
    }

    // metadata:
    // D - direction; Height - H(top middle bottom) ; side - S (Left middle right)
    // HH SS DDD
    // 0HHS SDDD
    //

    public static DoorDungeonSide getSideByMeta(int meta) {
        return DoorDungeonSide.values()[(meta >> 3) & 3];
    }

    public static DoorDungeonHeight getHeightByMeta(int meta) {
        return DoorDungeonHeight.values()[(meta >> 5) & 3];

    }

    public static int setHeightByMeta(int meta, DoorDungeonHeight height) {
        return (meta & (~0b1100000)) + ((height.ordinal() & 0b11) << 5);
    }

    public static int setSideByMeta(int meta, DoorDungeonSide side) {
        return (meta & (~0b0011000)) + ((side.ordinal() & 0b11) << 3);
    }

    public static int getDoorMetadata(World world, int x, int y, int z, int meta) {
        boolean top = world.getBlockId(x, y + 1, z) == AetherBlocks.DOOR_DUNGEON.id();
        boolean bottom = world.getBlockId(x, y - 1, z) == AetherBlocks.DOOR_DUNGEON.id();

        DoorDungeonHeight height;
        if (top && bottom) {
            height = DoorDungeonHeight.MIDDLE;
        }
        else if (!top) {
            height = DoorDungeonHeight.TOP;
        }
        else {
            height = DoorDungeonHeight.BOTTOM;
        }

        DoorDungeonSide side;
        Direction dir = getDirectionFromMeta(meta);

        boolean left = false;
        boolean right = false;

        switch (dir) {
            case NORTH:
            case SOUTH:
                left = world.getBlockId(x-1, y, z) == AetherBlocks.DOOR_DUNGEON.id();
                right = world.getBlockId(x+1, y, z) == AetherBlocks.DOOR_DUNGEON.id();
                break;

            case WEST:
            case EAST:
                left = world.getBlockId(x, y, z -1) == AetherBlocks.DOOR_DUNGEON.id();
                right = world.getBlockId(x, y, z +1) == AetherBlocks.DOOR_DUNGEON.id();
                break;
        }

        if (left && right) { side = DoorDungeonSide.MIDDLE; }
        else if (!left) { side = DoorDungeonSide.LEFT; }
        else { side = DoorDungeonSide.RIGHT; }


        return setHeightByMeta(setSideByMeta(meta, side), height);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        int meta = world.getBlockMetadata(x, y, z);
        int newMeta = getDoorMetadata(world, x, y, z, meta);

        if (newMeta != meta) world.setBlockMetadataWithNotify(x, y, z, newMeta);
    }

    @Override
    public void onBlockPlacedByWorld(World world, int x, int y, int z) {
        onNeighborBlockChange(world, x, y, z, 0);
    }

    @Override
    public void onBlockPlacedByMob(World world, int x, int y, int z, @NotNull Side side, Mob mob, double xPlaced, double yPlaced) {
        super.onBlockPlacedByMob(world, x, y, z, side, mob, xPlaced, yPlaced);
        onNeighborBlockChange(world, x, y, z, 0);
    }

    @Override
    public void onBlockPlacedOnSide(World world, int x, int y, int z, @NotNull Side side, double xPlaced, double yPlaced) {
        super.onBlockPlacedOnSide(world, x, y, z, side, xPlaced, yPlaced);
        onNeighborBlockChange(world, x, y, z, 0);
    }

    @Override
    public int getPlacedBlockMetadata(@Nullable Player player, ItemStack stack, World world, int x, int y, int z, Side side, double xPlaced, double yPlaced) {
        int initialMeta = super.getPlacedBlockMetadata(player, stack, world, x, y, z, side, xPlaced, yPlaced);
        return getDoorMetadata(world, x, y, z, initialMeta);
    }

    @Override
    public AABB getBlockBoundsFromState(WorldSource world, int x, int y, int z) {
        return this.getBoundsForRotation(BlockLogicRotatable.getDirectionFromMeta(world.getBlockMetadata(x, y, z)));
    }

    public AABB getBoundsForRotation(Direction rotation) {
        float top = 1.0F;
        float bottom = 0.0F;

        float thickness = .25F;
        switch (rotation) {
            case EAST:
            case WEST:
                return AABB.getTemporaryBB(thickness, bottom, 0.0F, 1 - thickness, top, 1.0F);

            case SOUTH:
            case NORTH:
            default:
                return AABB.getTemporaryBB(0.0F, bottom, thickness, 1.0F, top, (1.0F - thickness));
        }
    }

    @Override
    public boolean isSolidRender() { return false; }

    @Override
    public boolean isCubeShaped() { return false; }
}
