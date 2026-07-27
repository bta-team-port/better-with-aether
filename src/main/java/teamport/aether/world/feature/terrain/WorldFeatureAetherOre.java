package teamport.aether.world.feature.terrain;

import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.MethodParametersAnnotation;
import net.minecraft.core.world.generate.feature.WorldFeatureOre;
import net.minecraft.core.world.pos.TilePosc;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;

import java.util.Random;

public class WorldFeatureAetherOre extends WorldFeatureOre {
    private final int numberOfBlocks;
    private final Int2IntArrayMap variantMap;
    private int minableBlockId;

    @MethodParametersAnnotation(names = {"blockId", "numberOfBlocks"})
    public WorldFeatureAetherOre(int blockId, int numberOfBlocks) {
        super(blockId, numberOfBlocks);
        this.minableBlockId = blockId;
        this.numberOfBlocks = numberOfBlocks;
        this.variantMap = null;
    }

    @MethodParametersAnnotation(names = {"blockId", "numberOfBlocks", "variantMap"})
    public WorldFeatureAetherOre(@NonNull Int2IntArrayMap variantMap, int numberOfBlocks) {
        super(variantMap, numberOfBlocks);
        this.numberOfBlocks = numberOfBlocks;
        this.variantMap = variantMap;
    }

    @Override
    public boolean place(World world, Random random, TilePosc pos) {
        int xStart = pos.x();
        int yStart = pos.y();
        int zStart = pos.z();
        float f = random.nextFloat() * 3.1415927F;
        double xMax = (xStart + 8) + MathHelper.sin(f) * this.numberOfBlocks / 8.0F;
        double xMin = (xStart + 8) - MathHelper.sin(f) * this.numberOfBlocks / 8.0F;
        double zMax = (zStart + 8) + MathHelper.cos(f) * this.numberOfBlocks / 8.0F;
        double zMin = (zStart + 8) - MathHelper.cos(f) * this.numberOfBlocks / 8.0F;
        double yMax = yStart + random.nextInt(3) + 2.0;
        double yMin = yStart - random.nextInt(3) + 2.0;

        for (int l = 0; l <= this.numberOfBlocks; ++l) {
            double d6 = xMax + (xMin - xMax) * l / this.numberOfBlocks;
            double d7 = yMax + (yMin - yMax) * l / this.numberOfBlocks;
            double d8 = zMax + (zMin - zMax) * l / this.numberOfBlocks;
            double d9 = random.nextDouble() * this.numberOfBlocks / 16.0;
            double d10 = (MathHelper.sin(l * 3.1415927F / this.numberOfBlocks) + 1.0F) * d9 + 1.0;
            double d11 = (MathHelper.sin(l * 3.1415927F / this.numberOfBlocks) + 1.0F) * d9 + 1.0;
            int xVeinStart = MathHelper.floor(d6 - d10 / 2.0);
            int yVeinStart = MathHelper.floor(d7 - d11 / 2.0);
            int zVeinStart = MathHelper.floor(d8 - d10 / 2.0);
            int xVeinEnd = MathHelper.floor(d6 + d10 / 2.0);
            int yVeinEnd = MathHelper.floor(d7 + d11 / 2.0);
            int zVeinEnd = MathHelper.floor(d8 + d10 / 2.0);

            for (int x = xVeinStart; x <= xVeinEnd; ++x) {
                double d12 = (x + 0.5 - d6) / (d10 / 2.0);
                if (d12 * d12 < 1.0) {
                    for (int y = yVeinStart; y <= yVeinEnd; ++y) {
                        double d13 = (y + 0.5 - d7) / (d11 / 2.0);
                        if (d12 * d12 + d13 * d13 < 1.0) {
                            for (int z = zVeinStart; z <= zVeinEnd; ++z) {
                                double d14 = (z + 0.5 - d8) / (d10 / 2.0);
                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0) {
                                    int blockId;
                                    if (this.variantMap != null) {
                                        blockId = world.getBlockId(x, y, z);
                                        if (blockId > 0 && this.variantMap.containsKey(blockId)) {
                                            world.setBlock(x, y, z, this.variantMap.get(blockId));
                                        }
                                    } else {
                                        blockId = world.getBlockId(x, y, z);
                                        if (blockId == AetherBlocks.COBBLE_HOLYSTONE.id()) {
                                            world.setBlock(x, y, z, this.minableBlockId);
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
