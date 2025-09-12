package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sunsetsatellite.catalyst.core.util.vector.Vec3f;

public class BlockLogicDungeonDoor extends BlockLogicRotatable {
    public BlockLogicDungeonDoor(Block<?> block) {
        super(block, Material.metal);
    }

    @Override
    public boolean getImmovable() {
        return true;
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xHit, double yHit) {
        Direction dir = getDirectionFromMeta(world.getBlockMetadata(x, y, z));
        if (dir.getSide() != side) return false;

        Direction dirOpposite = dir.getOpposite();

        int destX = x + dirOpposite.getOffsetX();
        int destY = y + dirOpposite.getOffsetY();
        int destZ = z + dirOpposite.getOffsetZ();


        while (destY > 0 && world.getBlockId(destX, destY-1, destZ) == 0) --destY;

        if (    world.isBlockNormalCube(destX, destY, destZ)
             || world.isBlockNormalCube(destX, destY + 1, destZ)) {
            return false;
        }

        player.moveTo(destX + .5, destY, destZ + .5, player.yRot, player.xRot);
        world.playSoundEffect(null, SoundCategory.ENTITY_SOUNDS, destX, destY, destZ, "random.door_open", 0.5f, 0.5f);
        return true;
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
