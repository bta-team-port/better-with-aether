package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;
import teamport.aether.block.AetherBlocks;

public class BlockLogicPathDirtAether extends BlockLogic {
    public BlockLogicPathDirtAether(Block<?> block) {
        super(block, Materials.DIRT);
        block.setTicking(true);
        this.setBlockBounds(0.0, 0.0, 0.0, 1.0, 0.9375, 1.0);
        block.withLightBlock(255);
    }

    @Override
    public AABBdc getCollisionAABB(WorldSource world, TilePosc pos) {
        int x = pos.x();
        int y = pos.y();
        int z = pos.z();
        return new AABBd(x, y, z, x + 1.0, y + 1.0, z + 1.0);
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
        if (material.isSolid()
            && id != Blocks.FENCE_GATE_PLANKS_OAK.id()
            && id != Blocks.FENCE_GATE_PLANKS_OAK_PAINTED.id()
            && id != Blocks.SIGN_WALL_PLANKS_OAK.id()
            && id != AetherBlocks.SIGN_WALL_PLANKS_SKYROOT.id()
            && id != Blocks.SIGN_WALL_PLANKS_OAK_PAINTED.id()
            && id != AetherBlocks.SIGN_WALL_PLANKS_SKYROOT_PAINTED.id()
            && id != AetherBlocks.FENCEGATE_PLANKS_SKYROOT.id()
            && id != AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED.id()) {
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
