package bta.aether.block;

import bta.aether.entity.EntityFallingGravitite;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.world.World;

import java.util.Random;

public class BlockOreGravitite extends Block {
    public static boolean fallInstantly = false;

    public BlockOreGravitite(String key, int id) {
        super(key, id, Material.stone);
    }

    @Override
    public void onBlockAdded(World world, int i, int j, int k) {
        world.scheduleBlockUpdate(i, j, k, this.id, this.tickRate());
    }
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        world.scheduleBlockUpdate(x, y, z, this.id, this.tickRate());
    }
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        this.tryToFall(world, x, y, z);
    }

    private void tryToFall(World world, int i, int j, int k) {
        if (canFallTo(world, i, j + 1, k) && j < 256) {
            byte byte0 = 32;
            if (!fallInstantly && world.areBlocksLoaded(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0)) {
                EntityFallingGravitite entityfallinggravitite = new EntityFallingGravitite(world, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, this.id);
                world.entityJoinedWorld(entityfallinggravitite);
            } else {
                world.setBlockWithNotify(i, j, k, 0);

                while(canFallTo(world, i, j + 1, k) && j < 256) {
                    ++j;
                }

                if (j > 0) {
                    world.setBlockWithNotify(i, j, k, this.id);
                }
            }
        }

    }
    @Override
    public int tickRate() {
        return 3;
    }

    public static boolean canFallTo(World world, int i, int j, int k) { // Could probably just be replaced with BlockSand.canFallBelow -Useless
        int blockId = world.getBlockId(i, j, k);
        if (blockId == 0) {
            return true;
        } else if (blockId == Block.fire.id) {
            return true;
        } else {
            return Block.hasTag(blockId, BlockTags.IS_WATER) || Block.hasTag(blockId, BlockTags.IS_LAVA);
        }
    }
}