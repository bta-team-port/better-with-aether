package teamport.aether.blocks.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSaplingBase;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.world.feature.terrain.WorldFeatureAetherTreeGoldenOak;

import java.util.Random;

public class BlockLogicSaplingOakGolden extends BlockLogicSaplingBase {
    public BlockLogicSaplingOakGolden(Block<?> block) {
        super(block);
    }

    @Override
    public boolean mayPlaceOn(int blockId) {
        Block<?> block = Blocks.blocksList[blockId];
        return block != null
                && (block.hasTag(BlockTags.GROWS_FLOWERS)
                || block.hasTag(BlockTags.GROWS_TREES)
                || block.hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS)
                || block.hasTag(AetherBlockTags.GROWS_AETHER_TREES));
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isClientSide) {
            if (!this.canGrowOnSand && world.getBlockId(x, y - 1, z) == Blocks.SAND.id()) {
                world.setBlockWithNotify(x, y, z, Blocks.DEADBUSH.id());
            }

            super.updateTick(world, x, y, z, rand);
            int growthRate = 30;

            if (world.getBlockLightValue(x, y + 1, z) >= 9 && rand.nextInt(growthRate) == 0) {
                int l = world.getBlockMetadata(x, y, z);
                if ((l & 8) == 0) {
                    world.setBlockMetadataWithNotify(x, y, z, l | 8);
                } else {
                    this.growTree(world, x, y, z, rand);
                }
            }

        }
    }

    @Override
    public void growTree(World world, int x, int y, int z, Random random) {
        world.setBlockWithNotify(x, y, z, 0);
        WorldFeature tree = new WorldFeatureAetherTreeGoldenOak();
        if (!tree.place(world, random, x, y, z)) {
            world.setBlockWithNotify(x, y, z, this.id());
        }
    }

}
