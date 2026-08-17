package teamport.aether.world.feature.terrain;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.MethodParametersAnnotation;
import net.minecraft.core.world.generate.feature.WorldFeature;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;

import java.util.Random;

public class WorldFeatureAetherLiquid extends WorldFeature {
    private int liquidBlockId;

    @MethodParametersAnnotation(
        names = {"liquidId"}
    )
    public WorldFeatureAetherLiquid(int liquidId) {
        this.liquidBlockId = liquidId;
    }

    public boolean place(@NonNull World world, Random random, int x, int y, int z) {
        if (world.getBlock(x, y + 1, z).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) {
            if (world.getBlock(x, y - 1, z).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) {
                if ((world.getBlock(x, y, z) == Blocks.AIR || world.getBlock(x, y, z).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK))) {
                    int l = 0;
                    if (world.getBlock(x - 1, y, z).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) {
                        ++l;
                    }

                    if (world.getBlock(x + 1, y, z).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) {
                        ++l;
                    }

                    if (world.getBlock(x, y, z - 1).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) {
                        ++l;
                    }

                    if (world.getBlock(x, y, z + 1).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) {
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
                        Blocks.blocksList[this.liquidBlockId].updateTick(world, x, y, z, random, false);
                        world.scheduledUpdatesAreImmediate = false;
                    }

                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
