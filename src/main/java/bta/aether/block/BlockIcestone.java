package bta.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFlower;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.world.World;

import java.util.Random;

public class BlockIcestone extends Block {
    public BlockIcestone(String key, int id, Material material) {
        super(key, id, material);
        this.setTicking(true);
    }

    @Override
    public int tickRate() {
        return 50;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        for(int l = 0; l < 32; ++l) {
            int x1 = x + world.rand.nextInt(8) - world.rand.nextInt(8);
            int y1 = y + world.rand.nextInt(4) - world.rand.nextInt(4);
            int z1 = z + world.rand.nextInt(8) - world.rand.nextInt(8);

            freezeBlock(world, x1, y1, z1);
        }
        super.updateTick(world, x, y, z, rand);
    }

    public void freezeBlock(World world,int x, int y, int z) {
        int block = world.getBlockId(x, y, z);

        if (block == Block.fluidWaterStill.id) world.setBlockWithNotify(x, y, z, Block.ice.id);
        if (block == Block.fluidLavaStill.id) world.setBlockWithNotify(x, y, z, Block.obsidian.id);
    }

}