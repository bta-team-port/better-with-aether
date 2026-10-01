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
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;

import java.util.Random;

public class WorldFeatureAetherOre implements WorldFeatureInterface {
    private final Block<?> minableBlock;
    private final int numberOfBlocks;
    private final Int2IntArrayMap variantMap;

    @MethodParametersAnnotation(
        names = {"blockId", "numberOfBlocks"}
    )
    public WorldFeatureAetherOre(Block<?> block, int numberOfBlocks) {
        this.minableBlock = block;
        this.numberOfBlocks = numberOfBlocks;
        this.variantMap = null;
    }

    @MethodParametersAnnotation(
        names = {"blockId", "numberOfBlocks", "variantMap"}
    )
    public WorldFeatureAetherOre(@NonNull Int2IntArrayMap variantMap, int numberOfBlocks) {
        this.minableBlock = null;
        this.numberOfBlocks = numberOfBlocks;
        this.variantMap = variantMap;
    }

    @Override
    public boolean place(@NonNull World world, @NonNull Random random, @NonNull TilePosc tilePos) {
        float f = random.nextFloat() * (float) Math.PI;
        int xStart = tilePos.x();
        int yStart = tilePos.y();
        int zStart = tilePos.z();
        double xMax = (xStart + 8) + MathHelper.sin(f) * this.numberOfBlocks / 8.0F;
        double xMin = (xStart + 8) - MathHelper.sin(f) * this.numberOfBlocks / 8.0F;
        double zMax = (zStart + 8) + MathHelper.cos(f) * this.numberOfBlocks / 8.0F;
        double zMin = (zStart + 8) - MathHelper.cos(f) * this.numberOfBlocks / 8.0F;
        double yMax = yStart + random.nextInt(3) + 2.0;
        double yMin = yStart - random.nextInt(3) + 2.0;
        TilePos queryPos = new TilePos();

        for (int l = 0; l <= this.numberOfBlocks; ++l) {
            double veinX = xMax + (xMin - xMax) * l / this.numberOfBlocks;
            double veinY = yMax + (yMin - yMax) * l / this.numberOfBlocks;
            double veinZ = zMax + (zMin - zMax) * l / this.numberOfBlocks;
            double randomSize = random.nextDouble() * this.numberOfBlocks / 16.0F;
            double veinWidth = (MathHelper.sin(l * (float) Math.PI / this.numberOfBlocks) + 1.0F) * randomSize + 1.0F;
            double veinHeight = (MathHelper.sin(l * (float) Math.PI / this.numberOfBlocks) + 1.0F) * randomSize + 1.0F;
            int xVeinStart = MathHelper.floor(veinX - veinWidth / 2.0F);
            int yVeinStart = MathHelper.floor(veinY - veinHeight / 2.0F);
            int zVeinStart = MathHelper.floor(veinZ - veinWidth / 2.0F);
            int xVeinEnd = MathHelper.floor(veinX + veinWidth / 2.0F);
            int yVeinEnd = MathHelper.floor(veinY + veinHeight / 2.0F);
            int zVeinEnd = MathHelper.floor(veinZ + veinWidth / 2.0F);

            for (int x = xVeinStart; x <= xVeinEnd; ++x) {
                double d12 = ((double) x + (double) 0.5F - veinX) / (veinWidth / 2.0F);
                if (d12 * d12 >= 1.0F) {
                    continue;
                }
                for (int y = yVeinStart; y <= yVeinEnd; ++y) {
                    double d13 = ((double) y + (double) 0.5F - veinY) / (veinHeight / 2.0F);
                    if (d12 * d12 + d13 * d13 >= 1.0F) {
                        continue;
                    }
                    for (int z = zVeinStart; z <= zVeinEnd; ++z) {
                        double d14 = ((double) z + (double) 0.5F - veinZ) / (veinWidth / 2.0F);
                        if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0F) {
                            this.placeOreBlock(world, queryPos, x, y, z);
                        }
                    }
                }
            }
        }

        return true;
    }

    private void placeOreBlock(@NotNull World world, TilePos queryPos, int x, int y, int z) {
        queryPos.set(x, y, z);
        Block<?> currentBlock = world.getBlockType(queryPos);
        if (currentBlock == Blocks.AIR) {
            return;
        }
        int id = currentBlock.id();
        if (this.variantMap != null) {
            if (!this.variantMap.containsKey(id)) {
                return;
            }
            Block<?> block = Blocks.blocksList[this.variantMap.get(id)];
            assert block != null;
            world.setBlockType(queryPos, block);
            return;
        }
        if (!currentBlock.hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) {
            return;
        }
        Block<?> block = this.minableBlock;
        assert block != null;
        world.setBlockType(queryPos, block);
    }
}
