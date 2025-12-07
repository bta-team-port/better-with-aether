package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import teamport.aether.entity.floating_block.EntityFloatingBlock;

public class BlockLogicBlockGravitite extends BlockLogic {

    public BlockLogicBlockGravitite(Block<?> block, Material material) {
        super(block, material);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        if (!world.isClientSide) {
            int meta = world.getBlockMetadata(x, y, z);
            boolean wasPowered = (meta & 1) != 0;
            boolean isPowered = world.hasNeighborSignal(x, y, z);
            if (isPowered != wasPowered) {
                if (isPowered) {
                    world.setBlockMetadataWithNotify(x, y, z, 1);
                    tryToFall(world, x, y, z);
                } else {
                    world.setBlockMetadataWithNotify(x, y, z, 0);
                }
            }

        }
    }

    public void tryToFall(World world, int x, int y, int z) {
        boolean isPowered = world.hasNeighborSignal(x, y, z);
        if (isPowered) {
            if (BlockLogicOreGravitite.canFallAbove(world, x, y + 1, z) && y < 256) {
                byte byte0 = 32;
                if (world.areBlocksLoaded(x - byte0, y - byte0, z - byte0, x + byte0, y + byte0, z + byte0)) {
                    EntityFloatingBlock entityFloatingBlock = new EntityFloatingBlock(world, x + 0.5, y + 0.5, z + 0.5, this.block.id(), 0, null);
                    world.entityJoinedWorld(entityFloatingBlock);
                    world.setBlockWithNotify(x, y, z, 0);
                } else {
                    world.setBlockWithNotify(x, y, z, 0);

                    while (BlockLogicOreGravitite.canFallAbove(world, x, y + 1, z) && y < 256) {
                        ++y;
                    }

                    if (y < 256) {
                        world.setBlockWithNotify(x, y, z, this.block.id());
                    }
                }
            }
        }
    }

    @Override
    public int tickDelay() {
        return 3;
    }

}
