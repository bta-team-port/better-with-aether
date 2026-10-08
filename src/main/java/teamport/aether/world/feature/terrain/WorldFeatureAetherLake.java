package teamport.aether.world.feature.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.MethodParametersAnnotation;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class WorldFeatureAetherLake implements WorldFeatureInterface {
    private static final int SIZE_XZ = 16;
    private static final int SIZE_Y = 8;
    private static final int LIQUID_HEIGHT = 4;
    private final Block<?> fluidBlock;
    private final Block<?> shoreBase;
    private final Block<?> shoreCover;
    private final boolean isWorldFiller;

    public WorldFeatureAetherLake(
        @NotNull Block<?> fluidBlock,
        @NotNull Block<?> shoreBase,
        @NotNull Block<?> shoreCover,
        boolean isWorldFiller
    ) {
        this.fluidBlock = fluidBlock;
        this.shoreBase = shoreBase;
        this.shoreCover = shoreCover;
        this.isWorldFiller = isWorldFiller;
    }

    public static final class LakeConfig {
        private final TilePosc origin;
        private final boolean[][][] mask;

        private LakeConfig(World world, Random random, TilePosc origin) {
            this.origin = LakeConfig.computeOrigin(world, origin);
            this.mask = computeLakeMask(random);
        }

        public TilePosc origin() {
            return origin;
        }

        public boolean[][][] mask() {
            return mask;
        }

        private static boolean[][][] computeLakeMask(@NotNull Random random) {
            boolean[][][] lakeMasks = new boolean[SIZE_XZ][SIZE_Y][SIZE_XZ];
            int blobCount = random.nextInt(4) + 4;
            for (int blobIndex = 0; blobIndex < blobCount; ++blobIndex) {
                double blobSizeX = random.nextDouble() * 6.0F + 3.0F;
                double blobSizeY = random.nextDouble() * 4.0F + 2.0F;
                double blobSizeZ = random.nextDouble() * 6.0F + 3.0F;
                double blobCenterX = random.nextDouble() * (16.0F - blobSizeX - 2.0F) + 1.0F + blobSizeX / 2.0F;
                double blobCenterY = random.nextDouble() * (8.0F - blobSizeY - 4.0F) + 2.0F + blobSizeY / 2.0F;
                double blobCenterZ = random.nextDouble() * (16.0F - blobSizeZ - 2.0F) + 1.0F + blobSizeZ / 2.0F;

                for (int localX = 1; localX < SIZE_XZ - 1; ++localX) {
                    for (int localZ = 1; localZ < SIZE_XZ - 1; ++localZ) {
                        for (int localY = 1; localY < SIZE_Y - 1; ++localY) {
                            double nx = (localX - blobCenterX) / (blobSizeX / 2.0F);
                            double ny = (localY - blobCenterY) / (blobSizeY / 2.0F);
                            double nz = (localZ - blobCenterZ) / (blobSizeZ / 2.0F);
                            double distanceSquared = nx * nx + ny * ny + nz * nz;
                            if (distanceSquared < 1.0F) {
                                lakeMasks[localX][localY][localZ] = true;
                            }
                        }
                    }
                }
            }
            return lakeMasks;
        }

        private static TilePosc computeOrigin(@NotNull World world, @NotNull TilePosc tilePosc) {
            int x = tilePosc.x() - 8;
            int y = tilePosc.y();
            int z = tilePosc.z() - 8;
            TilePos scan = new TilePos();
            while (y > 0 && world.isAirBlock(scan.set(x, y, z))) --y;
            return new TilePos(x, y - 4, z);
        }

        public static LakeConfig prepare(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc) {
            return new LakeConfig(world, random, tilePosc);
        }
    }


    @Override
    public boolean place(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc) {
        LakeConfig lakeConfig = LakeConfig.prepare(world, random, tilePosc);
        if (!this.canPlace(world, lakeConfig)) {
            return false;
        }
        return this.placeFeature(world, random, lakeConfig);
    }

    public boolean canPlace(@NotNull World world, LakeConfig config) {
        int x = config.origin().x();
        int y = config.origin().y();
        int z = config.origin().z();
        TilePos tmp = new TilePos();
        for (int dx = 0; dx < SIZE_XZ; ++dx) {
            for (int dz = 0; dz < SIZE_XZ; ++dz) {
                for (int dy = 0; dy < SIZE_Y; ++dy) {
                    if (!isIsRimCell(config.mask(), dx, dy, dz)) {
                        continue;
                    }
                    tmp.set(x + dx, y + dy, z + dz);
                    Material neighborMaterial = world.getBlockMaterial(tmp);
                    if (dy >= LIQUID_HEIGHT && neighborMaterial.isLiquid()) {
                        return false;
                    }
                    if (dy < LIQUID_HEIGHT && !neighborMaterial.isSolid() && world.getBlockType(tmp) != this.fluidBlock) {
                        return false;
                    }
                    if (world.getBlockType(tmp) == Blocks.ICE) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean placeFeature(@NotNull World world, @NotNull Random random, LakeConfig config) {
        this.placeLiquid(world, config);
        this.placeShoreCover(world, config);
        if (isWorldFiller) {
            this.placeWorldFiller(world, random, config);
        }
        return true;
    }

    private void placeLiquid(@NotNull World world, @NotNull LakeConfig config) {
        int x = config.origin().x();
        int y = config.origin().y();
        int z = config.origin().z();
        TilePos tmp = new TilePos();
        for (int dx = 0; dx < SIZE_XZ; ++dx) {
            for (int dz = 0; dz < SIZE_XZ; ++dz) {
                for (int dy = 0; dy < 8; ++dy) {
                    tmp.set(x + dx, y + dy, z + dz);
                    if (config.mask()[dx][dy][dz] && world.getBlockType(tmp).getHardness() >= 0) {
                        world.setBlockTypeNotify(tmp, dy < LIQUID_HEIGHT ? this.fluidBlock : Blocks.AIR);
                    }
                }
            }
        }
    }

    private void placeShoreCover(@NotNull World world, @NotNull LakeConfig config) {
        int x = config.origin().x();
        int y = config.origin().y();
        int z = config.origin().z();
        TilePos tmp = new TilePos();
        for (int dx = 0; dx < SIZE_XZ; ++dx) {
            for (int dz = 0; dz < SIZE_XZ; ++dz) {
                for (int dy = LIQUID_HEIGHT; dy < SIZE_Y; ++dy) {
                    tmp.set(x + dx, y + dy - 1, z + dz);
                    if (config.mask()[dx][dy][dz]
                        && world.getBlockType(tmp).getHardness() >= 0
                        && world.getBlockType(tmp) == this.shoreBase
                        && world.getSavedLightValue(LightLayer.Sky, new TilePos(x + dx, y + dy, z + dz)) > 0
                    ) {
                        world.setBlockType(tmp, this.shoreCover);
                    }
                }
            }
        }
    }

    private void placeWorldFiller(@NotNull World world, @NotNull Random random, @NotNull LakeConfig config) {
        int x = config.origin().x();
        int y = config.origin().y();
        int z = config.origin().z();
        TilePos tmp = new TilePos();
        for (int dx = 0; dx < SIZE_XZ; ++dx) {
            for (int dz = 0; dz < SIZE_XZ; ++dz) {
                for (int dy = 0; dy < SIZE_Y; ++dy) {
                    tmp.set(x + dx, y + dy, z + dz);
                    boolean isRimCell = isIsRimCell(config.mask(), dx, dy, dz);
                    if (isRimCell && (dy < LIQUID_HEIGHT || random.nextInt(2) != 0)
                        && world.getBlockType(tmp).getHardness() >= 0
                        && world.getBlockMaterial(tmp).isSolid()
                    ) {
                        int fillerBlockId = world.getWorldType().getFillerBlockId();
                        world.setBlockType(tmp, Blocks.getBlock(fillerBlockId));
                    }
                }
            }
        }
    }

    private boolean isIsRimCell(boolean[][][] lakeMask, int dx, int dy, int dz) {
        int sizeXZ1 = SIZE_XZ - 1;
        int sizeY1 = SIZE_Y - 1;
        return !lakeMask[dx][dy][dz] &&
            (
                dx < sizeXZ1 && lakeMask[dx + 1][dy][dz]
                    || dx > 0 && lakeMask[dx - 1][dy][dz]
                    || dz < sizeXZ1 && lakeMask[dx][dy][dz + 1]
                    || dz > 0 && lakeMask[dx][dy][dz - 1]
                    || dy < sizeY1 && lakeMask[dx][dy + 1][dz]
                    || dy > 0 && lakeMask[dx][dy - 1][dz]
            );
    }
}
