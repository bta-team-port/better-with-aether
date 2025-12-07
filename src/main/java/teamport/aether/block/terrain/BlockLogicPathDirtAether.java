package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import teamport.aether.block.AetherBlocks;

public class BlockLogicPathDirtAether extends BlockLogic {
    public BlockLogicPathDirtAether(Block<?> block) {
        super(block, Material.dirt);
        block.setTicking(true);
        this.setBlockBounds(0.0, 0.0, 0.0, 1.0, 0.9375, 1.0);
        block.withLightBlock(255);
    }

    @Override
    public AABB getCollisionBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
        return AABB.getTemporaryBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }

    @Override
    public boolean isCubeShaped() {
        return false;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        super.onNeighborBlockChange(world, x, y, z, blockId);
        Material material = world.getBlockMaterial(x, y + 1, z);
        int id = world.getBlockId(x, y + 1, z);
        if (material.isSolid() && id != Blocks.FENCE_GATE_PLANKS_OAK.id() && id != Blocks.FENCE_GATE_PLANKS_OAK_PAINTED.id() && id != Blocks.SIGN_WALL_PLANKS_OAK.id() && id != AetherBlocks.FENCEGATE_PLANKS_SKYROOT.id()) {
            world.setBlockWithNotify(x, y, z, AetherBlocks.DIRT_AETHER.id());
        }

    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK:
                return new ItemStack[]{new ItemStack(this)};
            default:
                return new ItemStack[]{new ItemStack(AetherBlocks.DIRT_AETHER)};
        }
    }
}
