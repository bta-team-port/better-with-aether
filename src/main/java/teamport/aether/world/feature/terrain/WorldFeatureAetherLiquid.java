package teamport.aether.world.feature.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.MethodParametersAnnotation;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.block.AetherBlocks;

import java.util.Random;

public class WorldFeatureAetherLiquid extends WorldFeature {
    private final int liquidBlockId;

    @MethodParametersAnnotation(names = {"liquidId"})
    public WorldFeatureAetherLiquid(int liquidId) {
        this.liquidBlockId = liquidId;
    }

    @Override
    public boolean place(World world, Random random, int x, int y, int z) {
        if (world.getBlockId(x, y + 1, z) != AetherBlocks.COBBLE_HOLYSTONE.id()) {
            return false;
        } else if (world.getBlockId(x, y - 1, z) != AetherBlocks.COBBLE_HOLYSTONE.id()) {
            return false;
        } else if (world.getBlockId(x, y, z) != 0 && world.getBlockId(x, y, z) != AetherBlocks.COBBLE_HOLYSTONE.id()) {
            return false;
        } else {
            int l = 0;
            if (world.getBlockId(x - 1, y, z) == AetherBlocks.COBBLE_HOLYSTONE.id()) {
                ++l;
            }

            if (world.getBlockId(x + 1, y, z) == AetherBlocks.COBBLE_HOLYSTONE.id()) {
                ++l;
            }

            if (world.getBlockId(x, y, z - 1) == AetherBlocks.COBBLE_HOLYSTONE.id()) {
                ++l;
            }

            if (world.getBlockId(x, y, z + 1) == AetherBlocks.COBBLE_HOLYSTONE.id()) {
                ++l;
            }

            int i1 = 0;
            if (world.isAirBlock(x - 1, y, z)) {
                ++i1;
            }

            if (world.isAirBlock(x + 1, y, z)) {
                ++i1;
            }

            if (world.isAirBlock(x, y, z - 1)) {
                ++i1;
            }

            if (world.isAirBlock(x, y, z + 1)) {
                ++i1;
            }

            if (l == 3 && i1 == 1) {
                world.setBlockWithNotify(x, y, z, this.liquidBlockId);
                world.scheduledUpdatesAreImmediate = true;
                Block<?> block = Blocks.blocksList[this.liquidBlockId];
                if (block != null) block.updateTick(world, x, y, z, random, false);
                world.scheduledUpdatesAreImmediate = false;
            }

            return true;
        }
    }
}
