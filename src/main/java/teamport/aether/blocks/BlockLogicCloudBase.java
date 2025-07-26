package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicTransparent;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;

public class BlockLogicCloudBase extends BlockLogicTransparent {
    public BlockLogicCloudBase(Block<?> block) {
        super(block, Material.cloth);
    }


    public int getPistonPushReaction(World world, int x, int y, int z) {
        return 1;
    }

    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        entity.fallDistance = 0.0F;
    }

    public boolean isCubeShaped() {
        return false;
    }

    public boolean getIsBlockSolid(WorldSource blockAccess, int x, int y, int z, Side side) {
        return false;
    }

    public void handleEntityInside(World world, int x, int y, int z, Entity entity, Vec3 entityVelocity) {
        entity.fallDistance = 0.0F;
    }


    public boolean isSolidRender() {
        return false;
    }

    public AABB getCollisionBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
        return AABB.getPermanentBB(x, y, z, x + 1, y, z + 1);
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (!entity.isSneaking()) {
            if (entity.yd < 0.0) {
                entity.yd *= 0.005;
            }
        }
        entity.fallDistance = 0.0F;
    }

}
