package teamport.aether.mixin.dimension;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicFire;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.world.AetherDimension;

@Mixin(value = BlockLogicFire.class, remap = false)
public abstract class BlockLogicFireMixin extends BlockLogic {

    @Shadow protected abstract boolean canNeighborCatchFire(World world, int x, int y, int z);

    @Shadow protected abstract int getBurnResultId(World world, int x, int y, int z);

    public BlockLogicFireMixin(Block<?> block, Material material) {
        super(block, material);
    }

    @Override
    public void onBlockPlacedByWorld(World world, int x, int y, int z) {
        if (world.dimension == AetherDimension.AETHER) {
            world.setBlock(x, y, z, 0);
            return;
        }

        if (world.getBlockId(x, y - 1, z) != Blocks.OBSIDIAN.id() || !Blocks.PORTAL_NETHER.getLogic().tryToCreatePortal(world, x, y, z, null)) {
            if (!world.isBlockNormalCube(x, y - 1, z) && !this.canNeighborCatchFire(world, x, y, z)) {
                world.setBlockWithNotify(x, y, z, this.getBurnResultId(world, x, y, z));
            } else {
                world.scheduleBlockUpdate(x, y, z, this.block.id(), this.tickDelay());
            }

        }

        super.onBlockPlacedByWorld(world, x, y, z);
    }
}
