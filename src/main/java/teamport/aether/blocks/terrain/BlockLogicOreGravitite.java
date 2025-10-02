package teamport.aether.blocks.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureOre;
import teamport.aether.entity.EntityFloatingBlock;

import java.util.Random;

public class BlockLogicOreGravitite extends BlockLogic {
    public static WorldFeatureOre.OreMap variantMap = new WorldFeatureOre.OreMap();
    public static boolean fallInstantly = false;

    public BlockLogicOreGravitite(Block<?> block, Block<?> parentBlock, Material material) {
        super(block, material);
        variantMap.put(parentBlock.id(), block.id());
    }

    public void onBlockPlacedByWorld(World world, int x, int y, int z) {
        world.scheduleBlockUpdate(x, y, z, this.block.id(), this.tickDelay());
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        world.scheduleBlockUpdate(x, y, z, this.block.id(), this.tickDelay());
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        this.tryToFall(world, x, y, z);
    }

    public void tryToFall(World world, int x, int y, int z) {
        if (canFallAbove(world, x, y + 1, z) && y < 256) {
            byte byte0 = 32;
            if (!fallInstantly && world.areBlocksLoaded(x - byte0, y - byte0, z - byte0, x + byte0, y + byte0, z + byte0)) {
                EntityFloatingBlock entityFloatingBlock = new EntityFloatingBlock(world, (double) x + 0.5, (double) y + 0.5, (double) z + 0.5, this.block.id(), 0, null);
                world.entityJoinedWorld(entityFloatingBlock);
                world.setBlockWithNotify(x, y, z, 0);
            } else {
                world.setBlockWithNotify(x, y, z, 0);

                while (canFallAbove(world, x, y + 1, z) && y < 256) {
                    ++y;
                }

                if (y < 256) {
                    world.setBlockWithNotify(x, y, z, this.block.id());
                }
            }
        }
    }

    public int tickDelay() {
        return 3;
    }

    public static boolean canFallAbove(World world, int x, int y, int z) {
        Block<?> block = world.getBlock(x, y, z);
        return block == null || block.hasTag(BlockTags.PLACE_OVERWRITES);
    }

    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK:
            case EXPLOSION:
            case PROPER_TOOL:
            case PISTON_CRUSH:
                return new ItemStack[]{new ItemStack(this)};
            default:
                return null;
        }
    }
}
