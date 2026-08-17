package teamport.aether.world.feature.terrain;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.MethodParametersAnnotation;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;

import java.util.Random;

public class WorldFeatureAetherOre implements WorldFeatureInterface {
    private final int minableBlockId;
    private final int numberOfBlocks;
    private final Int2IntArrayMap variantMap;

    @MethodParametersAnnotation(
        names = {"blockId", "numberOfBlocks"}
    )
    public WorldFeatureAetherOre(int blockId, int numberOfBlocks) {
        this.minableBlockId = blockId;
        this.numberOfBlocks = numberOfBlocks;
        this.variantMap = null;
    }

    @MethodParametersAnnotation(
        names = {"blockId", "numberOfBlocks", "variantMap"}
    )
    public WorldFeatureAetherOre(@NonNull Int2IntArrayMap variantMap, int numberOfBlocks) {
        this.minableBlockId = 0;
        this.numberOfBlocks = numberOfBlocks;
        this.variantMap = variantMap;
    }

    public boolean place(@NonNull World world, @NonNull Random random, @NonNull TilePosc tilePos) {
        float f = random.nextFloat() * (float) Math.PI;
        int xStart = tilePos.x();
        int yStart = tilePos.y();
        int zStart = tilePos.z();
        double xMax = (float) (xStart + 8) + MathHelper.sin(f) * (float) this.numberOfBlocks / 8.0F;
        double xMin = (float) (xStart + 8) - MathHelper.sin(f) * (float) this.numberOfBlocks / 8.0F;
        double zMax = (float) (zStart + 8) + MathHelper.cos(f) * (float) this.numberOfBlocks / 8.0F;
        double zMin = (float) (zStart + 8) - MathHelper.cos(f) * (float) this.numberOfBlocks / 8.0F;
        double yMax = yStart + random.nextInt(3) + 2.0;
        double yMin = yStart - random.nextInt(3) + 2.0;
        TilePos queryPos = new TilePos();

        for (int l = 0; l <= this.numberOfBlocks; ++l) {
            double d6 = xMax + (xMin - xMax) * (double) l / (double) this.numberOfBlocks;
            double d7 = yMax + (yMin - yMax) * (double) l / (double) this.numberOfBlocks;
            double d8 = zMax + (zMin - zMax) * (double) l / (double) this.numberOfBlocks;
            double d9 = random.nextDouble() * (double) this.numberOfBlocks / (double) 16.0F;
            double d10 = (double) (MathHelper.sin((float) l * (float) Math.PI / (float) this.numberOfBlocks) + 1.0F) * d9 + (double) 1.0F;
            double d11 = (double) (MathHelper.sin((float) l * (float) Math.PI / (float) this.numberOfBlocks) + 1.0F) * d9 + (double) 1.0F;
            int xVeinStart = MathHelper.floor(d6 - d10 / (double) 2.0F);
            int yVeinStart = MathHelper.floor(d7 - d11 / (double) 2.0F);
            int zVeinStart = MathHelper.floor(d8 - d10 / (double) 2.0F);
            int xVeinEnd = MathHelper.floor(d6 + d10 / (double) 2.0F);
            int yVeinEnd = MathHelper.floor(d7 + d11 / (double) 2.0F);
            int zVeinEnd = MathHelper.floor(d8 + d10 / (double) 2.0F);

            for (int x = xVeinStart; x <= xVeinEnd; ++x) {
                double d12 = ((double) x + (double) 0.5F - d6) / (d10 / (double) 2.0F);
                if (!(d12 * d12 >= (double) 1.0F)) {
                    for (int y = yVeinStart; y <= yVeinEnd; ++y) {
                        double d13 = ((double) y + (double) 0.5F - d7) / (d11 / (double) 2.0F);
                        if (!(d12 * d12 + d13 * d13 >= (double) 1.0F)) {
                            for (int z = zVeinStart; z <= zVeinEnd; ++z) {
                                double d14 = ((double) z + (double) 0.5F - d8) / (d10 / (double) 2.0F);
                                if (d12 * d12 + d13 * d13 + d14 * d14 < (double) 1.0F) {
                                    queryPos.set(x, y, z);
                                    Block<?> currentBlock = world.getBlockType(queryPos);
                                    if (currentBlock != Blocks.AIR) {
                                        int id = currentBlock.id();
                                        if (this.variantMap != null) {
                                            if (this.variantMap.containsKey(id)) {
                                                world.setBlockType(queryPos, Blocks.blocksList[this.variantMap.get(id)]);
                                            }
                                        } else if (currentBlock.hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) {
                                            world.setBlockType(queryPos, Blocks.blocksList[this.minableBlockId]);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
