package teamport.aether.block.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;
import org.jspecify.annotations.Nullable;
import teamport.aether.helper.ParticleMaker;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.function.Supplier;

public class BlockLogicDungeonDoor extends BlockLogicRotatable {
    public final @Nullable Supplier<Item> droppedItem;

    public BlockLogicDungeonDoor(Block<?> block, @Nullable Supplier<Item> droppedItem) {
        super(block, Materials.STONE);
        this.droppedItem = droppedItem;
    }

    @Override
    public int getPistonPushReaction(World world, TilePosc pos) {
        return Material.PISTON_CANT_PUSH;
    }

    @Override
    public boolean onInteracted(World world, TilePosc pos, Player player, Side side, double xHit, double yHit) {
        return onBlockRightClicked(world, pos.x(), pos.y(), pos.z(), player, side, xHit, yHit);
    }

    @Override
    public void onRemoved(World world, TilePosc pos, int data) {
        removeDoorGrid(world, pos.x(), pos.y(), pos.z(), data);
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        if (this.droppedItem == null) {
            return null;
        } else {
            return new ItemStack[]{new ItemStack(this.droppedItem.get())};
        }
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
        Direction dir = getDirectionFromMeta(world.getBlockMetadata(x, y, z));
        if (dir.side() != side) return false;

        Direction dirOpposite = dir.opposite();

        int destX = x + dirOpposite.offsetX();
        int destY = y + dirOpposite.offsetY();
        int destZ = z + dirOpposite.offsetZ();


        while (destY > 0 && world.getBlockId(destX, destY - 1, destZ) == 0) --destY;

        if (world.isBlockNormalCube(destX, destY, destZ)
            || world.isBlockNormalCube(destX, destY + 1, destZ)) {
            return false;
        }

        player.moveTo(destX + .5, destY, destZ + .5, player.yRot, player.xRot);

        if (!EnvironmentHelper.isServerEnvironment()) {
            world.playSoundEffect(null, SoundCategory.ENTITY_SOUNDS, destX, destY, destZ, "random.door_open", 0.5f, 0.5f);
        }
        return true;
    }

    @Override
    public AABBdc getBoundsFromState(WorldSource world, TilePosc pos) {
        return this.getBoundsForRotation(BlockLogicRotatable.getDirectionFromMeta(world.getBlockMetadata(pos.x(), pos.y(), pos.z())));
    }

    public AABBdc getBoundsForRotation(Direction rotation) {
        float top = 1.0F;
        float bottom = 0.0F;

        float thickness = .25F;
        switch (rotation) {
            case EAST:
            case WEST:
                return new AABBd(thickness, bottom, 0.0F, 1 - thickness, top, 1.0F);

            case SOUTH:
            case NORTH:
            default:
                return new AABBd(0.0F, bottom, thickness, 1.0F, top, (1.0F - thickness));
        }
    }

    @Override
    public void onBlockRemoved(World world, int x, int y, int z, int data) {
        removeDoorGrid(world, x, y, z, data);
    }

    private void removeDoorGrid(World world, int x, int y, int z, int meta) {
        world.noNeighborUpdate = true;
        for (int dx = -3; dx <= 3; dx++) {
            for (int dy = -3; dy <= 3; dy++) {
                for (int dz = -3; dz <= 3; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) continue;
                    int checkX = x + dx;
                    int checkY = y + dy;
                    int checkZ = z + dz;
                    if (world.getBlockId(checkX, checkY, checkZ) == this.block.id() && world.getBlockMetadata(checkX, checkY, checkZ) == meta) {
                        world.setBlockWithNotify(checkX, checkY, checkZ, 0);
                        ParticleMaker.spawnBlockBreakParticles(world, checkX, checkY, checkZ, this.id());
                    }
                }
            }
        }
        world.noNeighborUpdate = false;
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }

    @Override
    public boolean isCubeShaped() {
        return false;
    }
}
