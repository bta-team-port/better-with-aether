package teamport.aether.world.chunk;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFallingBlock;
import net.minecraft.core.block.BlockLogicLeavesBase;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
import net.minecraft.core.world.generate.feature.WorldFeatureLake;
import net.minecraft.core.world.noise.*;
import net.minecraft.core.world.pos.*;
import net.minecraft.core.world.type.tag.WorldTypeTags;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import teamport.aether.AetherGlobals;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.BlockLogicFloatingBlock;
import teamport.aether.block.terrain.BlockLogicOreAmbrosium;
import teamport.aether.block.terrain.BlockLogicOreGravitite;
import teamport.aether.block.terrain.BlockLogicOreZanite;
import teamport.aether.noise.Worley;
import teamport.aether.world.feature.dungeon.bronze.WorldFeatureAetherBronzeDungeon;
import teamport.aether.world.feature.dungeon.gold.WorldFeatureAetherGoldDungeon;
import teamport.aether.world.feature.dungeon.silver.WorldFeatureAetherSilverDungeon;
import teamport.aether.world.feature.terrain.*;
import teamport.aether.world.type.AetherWorldTypes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

@SuppressWarnings({"java:S116", "java:S3776"})
public class ChunkDecoratorAether implements ChunkDecorator {
    private final World world;
    private final Noise3D flowerVeinNoise;
    private final Noise3D flowerDensityNoise;
    private final Noise2D cloudNoise;
    private final Noise3D cloudNoise2;
    private final Noise2D grassNoise;
    private final double grassRange;

    public ChunkDecoratorAether(@NonNull World world) {
        this.world = world;
        this.flowerVeinNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed(), 4, 44));
        this.flowerDensityNoise = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed() * 31 ^ 13, 4, 44));
        this.cloudNoise = new FractalNoise2D<>(SimplexNoise.genOctaves(world.getRandomSeed(), 4));
        this.cloudNoise2 = new FractalNoise3D<>(ImprovedPerlinNoise.genOctaves(world.getRandomSeed() * 31 ^ 7, 4, 44));
        this.grassNoise = new FractalNoise2D<>(SimplexNoise.genOctaves(world.getRandomSeed() ^ 0x6A09E667L, 2)).setLacunarity(2.0).setPersistence(2.0);
        this.grassRange = grassNoise.getRange2D().y();
    }

    private static @NonNull Random deriveRandomFromWorld(@NonNull Chunk chunk, long seed) {
        Random rand = new Random(seed);
        long l1 = rand.nextLong() / 2L * 2L + 1L;
        long l2 = rand.nextLong() / 2L * 2L + 1L;
        rand.setSeed(chunk.pos.x * l1 + chunk.pos.z * l2 ^ seed);
        return rand;
    }

    @Override
    public void decorate(@NonNull Chunk chunk) {
        this.world.scheduledUpdatesAreImmediate = true;
        BlockLogicFallingBlock.fallInstantly = true;
        BlockLogicFloatingBlock.floatInstantly = true;
        ChunkDecorateData data = new ChunkDecorateData(this.world, chunk);
        this.decorateWithClouds(data);
        this.decorateWithFlatClouds(data);
        this.decorateWithDungeons(data);
        this.decorateWithFlowers(data);
        this.decorateDirtPatches(data);
        this.decorateWithQuickSoil(data);
        this.decorateLakes(data);
        this.decorateTrees(data);
        this.decorateUnderTrees(data);
        this.decorateWaterFalls(data);
        this.decorateWithOres(data);
        BlockLogicFloatingBlock.floatInstantly = false;
        BlockLogicFallingBlock.fallInstantly = false;
        this.world.scheduledUpdatesAreImmediate = false;
    }

    public static final WorldFeatureAetherClouds AERCLOUD_WHITE = new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_WHITE, 16, false);
    public static final WorldFeatureAetherClouds AERCLOUD_BLUE = new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_BLUE, 8, false);
    public static final WorldFeatureAetherClouds AERCLOUD_GOLD = new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_GOLD, 4, false);
    public static final WorldFeatureAetherClouds AERCLOUD_FLAT = new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_WHITE, 48, true);

    public void decorateWithClouds(@NotNull ChunkDecorateData data) {
        float heightModifier = (data.maxY + 1 - data.minY) / 128.0F;
        TilePos tilePos = new TilePos(data.worldX + 8, data.minY, data.worldZ + 8);

        if (data.rand.nextInt(12) == 0) {
            int base = data.rand.nextInt(16) + 112;
            tilePos.y = data.minY + Math.round(base * heightModifier);
            AERCLOUD_GOLD.place(this.world, data.rand, tilePos);
        }

        if (data.rand.nextInt(12) == 0) {
            int base = data.rand.nextInt(32) + 64;
            tilePos.y = data.minY + Math.round(base * heightModifier);
            AERCLOUD_BLUE.place(this.world, data.rand, tilePos);
        }

        if (data.rand.nextInt(6) == 0) {
            int base = data.rand.nextInt(96) + 16;
            tilePos.y = data.minY + Math.round(base * heightModifier);
            AERCLOUD_WHITE.place(this.world, data.rand, tilePos);
        }

        if ((world.getWorldType() == AetherWorldTypes.AETHER_DEFAULT || world.getWorldType() == AetherWorldTypes.AETHER_RETRO) && data.rand.nextInt(24) == 0) {
            tilePos.y = data.rand.nextInt(28) + 4;
            AERCLOUD_FLAT.place(this.world, data.rand, tilePos);
        }
    }

    private final double[] CLOUD_NOISE_BUFFER = new double[16 * 16];
    private final double[] CLOUD_NOISE_2_BUFFER = new double[16 * 16];
    private final double[] CLOUD_NOISE_TOP_BUFFER = new double[16 * 16];
    private final double[] CLOUD_NOISE_TOP_2_BUFFER = new double[16 * 16];

    public void decorateWithFlatClouds(@NonNull ChunkDecorateData data) {
        if (world.getWorldType() != AetherWorldTypes.AETHER_EXTENDED && world.getWorldType() != AetherWorldTypes.AETHER_AMPLIFIED) {
            return;
        }
        double scale = 0.38;
        Chunk chunk = data.chunk;
        double chunkX = data.worldX;
        double chunkZ = data.worldZ;

        this.cloudNoise.getRegion(
            CLOUD_NOISE_BUFFER,
            chunkX, chunkZ,
            16, 16,
            scale * 0.627,
            scale * 2
        );

        this.cloudNoise2.getRegion(
            CLOUD_NOISE_2_BUFFER,
            chunkX, chunkZ - 32.0,
            0.0,
            16, 16, 1,
            scale * 0.627,
            scale * 0.627,
            scale * 2
        );

        this.cloudNoise.getRegion(
            CLOUD_NOISE_TOP_BUFFER,
            chunkX + 32.0, chunkZ + 32.0,
            16, 16,
            scale * 0.627,
            scale * 2
        );

        this.cloudNoise2.getRegion(
            CLOUD_NOISE_TOP_2_BUFFER,
            chunkX + 48.0, chunkZ + 32.0,
            0.0,
            16, 16, 1,
            scale * 0.627,
            scale * 0.627,
            scale * 2
        );

        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                int index = z + x * 16;

                int cloudDensity = (int) Math.min(
                    Math.abs(CLOUD_NOISE_2_BUFFER[index] + CLOUD_NOISE_BUFFER[index]) * 6 - 32, 2
                );

                for (int y = 0; y < cloudDensity; y++) {
                    ChunkTilePos chunkTilePos = new ChunkTilePos(x, 24 + y, z);
                    if (chunk.getBlockId(chunkTilePos) == Blocks.AIR.id()) {
                        chunk.setBlockId(chunkTilePos, AetherBlocks.AERCLOUD_WHITE.id());
                    }
                }

                int cloudDensity2 = (int) Math.min(
                    Math.abs(CLOUD_NOISE_TOP_2_BUFFER[index] + CLOUD_NOISE_TOP_BUFFER[index]) * 6 - 40, 1
                );

                for (int y = 0; y < cloudDensity2; y++) {
                    ChunkTilePos chunkTilePos = new ChunkTilePos(x, 14 + y, z);
                    if (chunk.getBlockId(chunkTilePos) == Blocks.AIR.id()) {
                        chunk.setBlockId(chunkTilePos, AetherBlocks.AERCLOUD_WHITE.id());
                    }
                }
            }
        }
    }

    public void decorateWithDungeons(@NonNull ChunkDecorateData data) {
        int chunkX = data.chunk.pos.x;
        int chunkZ = data.chunk.pos.z;
        int rangeY = data.maxY + 1 - data.minY;
        int x = chunkX * 16;
        int z = chunkZ * 16;
        long worldSeed = this.world.getRandomSeed();
        int transformedSeed = Worley.mix((int) (worldSeed >>> 32), (int) (worldSeed & 0xFFFFFFFFL), 0);
        int goldSeed = Worley.isSeed(chunkX, chunkZ, AetherGlobals.GOLD_CHANCES, transformedSeed, 1, 2); // 22 - 2
        int silverSeed = Worley.isSeed(chunkX, chunkZ, AetherGlobals.SILVER_CHANCES, transformedSeed, 1, 2); // 16 - 2
        int bronzeSeed = Worley.isSeed(chunkX, chunkZ, AetherGlobals.BRONZE_CHANCES, transformedSeed, 1, 2); // 10 - 2
        if (goldSeed > -1) {
            int dungeonX = x + 8;
            int dungeonY = (rangeY / 2) + data.rand.nextInt(rangeY / 8);
            int dungeonZ = z + 8;
            WorldFeatureAetherGoldDungeon goldDungeon = new WorldFeatureAetherGoldDungeon(data.rand);
            if (goldDungeon.canPlace(world, dungeonX, dungeonY, dungeonZ)) {
                goldDungeon.register(world, worldSeed ^ data.rand.nextLong(), dungeonX, dungeonY, dungeonZ);
            }
        } else if (silverSeed > -1) {
            int dungeonX = x - 15;
            int dungeonY = (int) ((rangeY - (rangeY / 4.5)) + data.rand.nextInt(rangeY / 8));
            int dungeonZ = z + 28;
            WorldFeatureAetherSilverDungeon silverDungeon = new WorldFeatureAetherSilverDungeon(data.rand);
            if (silverDungeon.canPlace(world, dungeonX, dungeonY, dungeonZ)) {
                silverDungeon.register(this.world, worldSeed ^ data.rand.nextLong(), dungeonX, dungeonY, dungeonZ);
            }
        } else if (bronzeSeed > -1) {
            int dungeonX = x + data.rand.nextInt(16);
            int dungeonZ = z + data.rand.nextInt(16);
            // find the deepest section in the current chunk.
            int maxDepth = 0;
            int maxDepthStart = 0;
            int currentDepth = 0;
            int currentStart = 0;
            for (int i = this.world.getWorldType().getMinY(this.world); i < this.world.getWorldType().getMaxY(this.world); i++) {
                if (world.getBlockType(new TilePos(dungeonX, i, dungeonZ)) != Blocks.AIR) {
                    currentDepth++;
                } else {
                    currentDepth = 0;
                    currentStart = i;
                }
                if (currentDepth > maxDepth) {
                    maxDepth = currentDepth;
                    maxDepthStart = currentStart;
                }
            }
            int dungeonY = Math.max(0, (maxDepthStart + maxDepth / 2) - (int) (5.0F / 256.0F * rangeY));
            new WorldFeatureAetherBronzeDungeon().place(this.world, data.rand, dungeonX, dungeonY, dungeonZ);
        }
    }

    private static final int[] FLOWERS = new int[]{AetherBlocks.FLOWER_WHITE.id(), AetherBlocks.FLOWER_PURPLE.id()};
    private static final WeightedRandomBag<Integer> size = new WeightedRandomBag<>();

    static {
        size.addEntry(0, 4);
        size.addEntry(32, 4);
        size.addEntry(64, 3);
        size.addEntry(96, 2);
    }

    private final double[] FLOWER_DENSITY_NOISE_BUFFER = new double[16 * 16];
    private final double[] FLOWER_VEIN_NOISE_BUFFER = new double[8 * 8];

    public void decorateWithFlowers(@NonNull ChunkDecorateData data) {
        double beachScale = 0.03125;

        this.flowerDensityNoise.getRegion(
            FLOWER_DENSITY_NOISE_BUFFER, data.worldX, data.worldZ, 0.0,
            16, 16, 1, beachScale * 1.5, beachScale * 1.5, beachScale * 1.5
        );
        this.flowerVeinNoise.getRegion(
            FLOWER_VEIN_NOISE_BUFFER, data.worldX, data.worldZ, 0.0,
            8, 8, 1, beachScale * 0.627, beachScale * 0.627, beachScale * 0.627
        );
        boolean isRetro = world.getWorldType().hasTag(WorldTypeTags.RETRO);
        int minY = world.getWorldType().getMinY(world);
        int maxY = world.getWorldType().getMaxY(world);
        double clumpRadius = 64.0;
        int mix = Worley.mix((int) (world.getRandomSeed() >>> 32), (int) (world.getRandomSeed() & 0xFFFFFFFFL), 0);
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                double noise = MathHelper.clamp(Math.abs(FLOWER_DENSITY_NOISE_BUFFER[z + x * 8]) / 16.0, 0.0, 1.0);
                float sample = Worley.sampleAt(data.worldX + x, data.worldZ + z, 16, mix);
                double influence = MathHelper.clamp(sample, -clumpRadius, clumpRadius) / clumpRadius;
                double flowerDensityFloat = ((noise * -1) / influence) * -1;
                int flowerDensity = (int) (MathHelper.clamp(flowerDensityFloat, 0, 1) * 16) - 8;

                int blockY = data.chunk.getHeightValue(x, z);
                if (blockY <= minY + 1 || blockY >= maxY) {
                    continue;
                }
                Block<?> blk = data.chunk.getBlock(new ChunkTilePos(x, blockY - 1, z));
                ChunkTilePos chunkTilePos = new ChunkTilePos(x, blockY, z);
                if (!blk.hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS)) {
                    continue;
                }
                if (flowerDensity >= 0) {
                    this.placeFlowerBasedOnDensity(data, chunkTilePos, flowerDensity, x, z, isRetro);
                } else {
                    if (data.rand.nextInt(128) == 0) {
                        data.chunk.setBlockIdDataRaw(chunkTilePos, FLOWERS[data.rand.nextInt(FLOWERS.length)], size.getRandom(data.rand));
                    }
                    if (!isRetro && data.rand.nextInt(16) == 0) {
                        data.chunk.setBlockId(chunkTilePos, AetherBlocks.TALLGRASS_AETHER.id());
                    }

                }
            }
        }
    }

    private void placeFlowerBasedOnDensity(@NotNull ChunkDecorateData data, ChunkTilePos chunkTilePos, int flowerDensity, int x, int z, boolean isRetro) {
        if (data.rand.nextInt(3 * (9 - flowerDensity)) != 0) {
            return;
        }
        int flowerID = !isRetro ? AetherBlocks.TALLGRASS_AETHER.id() : 0;
        int flowerMeta = 0;
        if (data.rand.nextInt(2) == 0) {
            flowerID = FLOWERS[(int) Math.abs(FLOWER_VEIN_NOISE_BUFFER[z / 2 + (x / 2) * 8] * 8) % FLOWERS.length];
            flowerMeta = size.getRandom(data.rand);
        }
        if (flowerID != 0) {
            data.chunk.setBlockIdDataRaw(chunkTilePos, flowerID, flowerMeta);
        }
    }

    private static final double GRASS_SCALE      = 0.2;
    private static final double GRASS_THRESHOLD  = -0.2;
    private static final double GRASS_LEVEL_SHIFT = 100.0;

    private void decorateDirtPatches(@NotNull ChunkDecorateData data) {
        TilePos temp = new TilePos(0, 0, 0);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int i = 0; i < 10; i++) {
                    int y = data.getHeightValue(data.worldX + x, data.worldZ + z, i);
                    if (y > 0) {
                        double n = this.grassValue(data.worldX + x, data.worldZ + z, i);
                        temp.set(data.worldX + x, y - 1, data.worldZ + z);
                        if (n >= GRASS_THRESHOLD && this.world.getBlockType(temp).hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS)){
                            this.world.setBlockType(temp.up(), AetherBlocks.TALLGRASS_AETHER);
                        }
                    }
                }
            }
        }
    }

    private void dumpGrassLevels( int originX, int originZ, int size, int... levels) throws IOException {
        Path dir = FabricLoader.getInstance().getGameDir().resolve("noise_debug");
        Files.createDirectories(dir);

        for (int level : levels) {
            BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < size; x++) {
                for (int z = 0; z < size; z++) {
                    double n = grassValue(originX + x, originZ + z, level);
                    int rgb = n >= -0.2 ? 0x22BB22 : 0xFFFFFF;
                    img.setRGB(x, z, rgb);
                }
            }

            File out = dir.resolve("grass_level_" + level + ".png").toFile();
            ImageIO.write(img, "png", out);
        }
    }

    private double grassValue(int worldX, int worldZ, int level) {
        double shift = level * GRASS_LEVEL_SHIFT;
        return this.grassNoise.getValue(worldX * GRASS_SCALE + shift, worldZ * GRASS_SCALE + shift) / this.grassRange;
    }

    public static final WorldFeatureAetherQuicksoil QUICKSOIL = new WorldFeatureAetherQuicksoil(AetherBlocks.QUICKSOIL);

    @SuppressWarnings("java:S1119")
    public void decorateWithQuickSoil(@NonNull ChunkDecorateData data) {
        if (data.rand.nextInt(3) != 0) {
            return;
        }
        int rangeY = data.maxY + 1 - data.minY;
        int startY = data.minY + (rangeY / 8);
        int endY = data.minY + (int) (192.0F / 256.0F * rangeY);

        outer:
        for (int y = startY; y < endY; y++) {
            for (int x = data.worldX; x < data.worldX + 16; x++) {
                for (int z = data.worldZ; z < data.worldZ + 16; z++) {
                    TilePos tilePos = new TilePos(x, y, z);
                    if (this.world.getBlockType(tilePos) == Blocks.AIR
                        && this.world.getBlockType(new TilePos(x, y + 1, z)) == AetherBlocks.GRASS_AETHER) {

                        QUICKSOIL.place(this.world, data.rand, tilePos);
                        break outer;
                    }
                }
            }
        }
    }

    public static final WorldFeatureLake LAKE_WATER = new WorldFeatureLake(Blocks.FLUID_WATER_STILL.id());

    private void decorateLakes(@NotNull ChunkDecorateData data) {
        int rangeY = data.maxY + 1 - data.minY;
        TilePos tilePos = new TilePos(0, 0, 0);
        if (data.rand.nextInt(8) == 0) {
            int x = data.worldX + data.rand.nextInt(Chunk.CHUNK_SIZE_X);
            int z = data.worldZ + data.rand.nextInt(Chunk.CHUNK_SIZE_Z);
            int y = data.rand.nextInt(rangeY);
            LAKE_WATER.place(this.world, data.rand, tilePos.set(x, y, z));
        }
        for(int i = 0; i < 10; i++){
            int x = data.worldX + data.rand.nextInt(Chunk.CHUNK_SIZE_X);
            int z = data.worldZ + data.rand.nextInt(Chunk.CHUNK_SIZE_Z);
            int y = data.getHeightValue(x, z, i);
            if(y <= 0 ){
                return;
            }
            if(data.rand.nextInt(8) == 0){
                LAKE_WATER.place(this.world, data.rand, tilePos.set(x, y, z));
            }
        }
    }

    public static final WorldFeatureAetherTreeGoldenOak TREE_GOLDEN = new WorldFeatureAetherTreeGoldenOak(AetherBlocks.LEAVES_OAK_GOLDEN, AetherBlocks.LOG_OAK_GOLDEN, 6);
    public static final WorldFeatureSkyrootTree TREE_SKYROOT = new WorldFeatureSkyrootTree(AetherBlocks.LEAVES_SKYROOT, AetherBlocks.LOG_SKYROOT, 4);
    public static final WorldFeatureSkyrootTree SHORT_TREE_SKYROOT = new WorldFeatureSkyrootTree(AetherBlocks.LEAVES_SKYROOT, AetherBlocks.LOG_SKYROOT, 1);

    private void decorateTrees(@NotNull ChunkDecorateData data) {
        BlockLogicLeavesBase.enableDecay = false;
        try {
            for (int generateChance = 0; generateChance < 3; ++generateChance) {
                int x = data.worldX + data.rand.nextInt(Chunk.CHUNK_SIZE_X) + 8;
                int z = data.worldZ + data.rand.nextInt(Chunk.CHUNK_SIZE_Z) + 8;
                int y = world.getHeightValue(x, z);
                (data.rand.nextInt(18) == 0 ? TREE_GOLDEN : TREE_SKYROOT).place(this.world, data.rand, new TilePos(x, y, z));

            }
        } finally {
            BlockLogicLeavesBase.enableDecay = true;
        }
    }

    private void decorateUnderTrees(@NotNull ChunkDecorateData data) {
        try {
            BlockLogicLeavesBase.enableDecay = false;
            for (int i = 0; i < 10; i++) {
                for (int generateChance = 0; generateChance < 2; ++generateChance) {
                    int x = data.worldX + data.rand.nextInt(Chunk.CHUNK_SIZE_X);
                    int z = data.worldZ + data.rand.nextInt(Chunk.CHUNK_SIZE_Z);
                    int y = data.getHeightValue(x, z, i);
                    if (y == 0) {
                        break;
                    }
                    (data.rand.nextInt(5) == 0 ? TREE_SKYROOT : SHORT_TREE_SKYROOT).place(this.world, data.rand, new TilePos(x, y, z));
                }
            }
        } finally {
            BlockLogicLeavesBase.enableDecay = true;
        }
    }

    public static final WorldFeatureAetherLiquid WATERFALL = new WorldFeatureAetherLiquid(Blocks.FLUID_WATER_FLOWING);

    private void decorateWaterFalls(@NotNull ChunkDecorateData data) {
        int rangeY = data.maxY + 1 - data.minY;
        TilePos tilePos = new TilePos(0, 0, 0);
        for (int generateChance = 0; generateChance < 25; ++generateChance) {
            int x = data.worldX + data.rand.nextInt(Chunk.CHUNK_SIZE_X);
            int z = data.worldZ + data.rand.nextInt(Chunk.CHUNK_SIZE_Z);
            int y = data.rand.nextInt(rangeY - 1);
            WATERFALL.place(this.world, data.rand, tilePos.set(x, y, z));
        }
    }

    public static final WorldFeatureAetherOre ORE_DIRT = new WorldFeatureAetherOre(AetherBlocks.DIRT_AETHER, 32);
    public static final WorldFeatureAetherOre ORE_ICESTONE = new WorldFeatureAetherOre(AetherBlocks.ICESTONE, 32);
    public static final WorldFeatureAetherOre ORE_AMBROSIUM = new WorldFeatureAetherOre(BlockLogicOreAmbrosium.variantMap, 16);
    public static final WorldFeatureAetherOre ORE_ZANITE = new WorldFeatureAetherOre(BlockLogicOreZanite.variantMap, 8);
    public static final WorldFeatureAetherOre ORE_GRAVITITE = new WorldFeatureAetherOre(BlockLogicOreGravitite.variantMap, 7);


    public void decorateWithOres(@NotNull ChunkDecorateData data) {
        int rangeY = data.maxY + 1 - data.minY;
        float oreHeightModifier = rangeY / 128.0F;
        int generateChance = 0;
        TilePos tilePos = new TilePos(0, 0, 0);
        for (generateChance = 0; generateChance < 10 * oreHeightModifier; ++generateChance) {
            tilePos.set(data.worldX + data.rand.nextInt(16), data.rand.nextInt(rangeY), data.worldZ + data.rand.nextInt(16));
            ORE_DIRT.place(this.world, data.rand, tilePos);
        }

        for (generateChance = 0; generateChance < 10 * oreHeightModifier; ++generateChance) {
            tilePos.set(data.worldX + data.rand.nextInt(16), data.rand.nextInt(rangeY), data.worldZ + data.rand.nextInt(16));
            ORE_ICESTONE.place(this.world, data.rand, tilePos);
        }

        //Ambrosium 0-256
        for (generateChance = 0; generateChance < 20.0F * oreHeightModifier; ++generateChance) {
            tilePos.set(data.worldX + data.rand.nextInt(16), data.rand.nextInt(rangeY), data.worldZ + data.rand.nextInt(16));
            ORE_AMBROSIUM.place(this.world, data.rand, tilePos);
        }

        //Zanite 0-192
        for (generateChance = 0; generateChance < 15.0F * oreHeightModifier; ++generateChance) {
            tilePos.set(data.worldX + data.rand.nextInt(16), data.rand.nextInt(rangeY / 2), data.worldZ + data.rand.nextInt(16));
            ORE_ZANITE.place(this.world, data.rand, tilePos);
        }

        //Gravitite 0-128
        for (generateChance = 0; generateChance < 8.0f * oreHeightModifier; ++generateChance) {
            tilePos.set(data.worldX + data.rand.nextInt(16), data.rand.nextInt(rangeY / 3), data.worldZ + data.rand.nextInt(16));
            ORE_GRAVITITE.place(this.world, data.rand, tilePos);
        }
    }

    public static class ChunkDecorateData {
        public final Chunk chunk;
        public final Random rand;
        public final OverhandMap map;
        public final int minY;
        public final int maxY;
        public final int worldX;
        public final int worldZ;

        public ChunkDecorateData(World world, Chunk chunk) {
            this.chunk = chunk;
            this.minY = world.getWorldType().getMinY(world);
            this.maxY = world.getWorldType().getMaxY(world);
            this.worldX = chunk.pos.x * 16;
            this.worldZ = chunk.pos.z * 16;
            this.rand = ChunkDecoratorAether.deriveRandomFromWorld(chunk, world.getRandomSeed());
            this.map = new OverhandMap(chunk);
        }

        public int getHeightValue(int x, int y, int i) {
            return this.map.getHeight(x, y, i);
        }

        public int getGap(int x, int y, int i) {
            return this.map.getGap(x, y, i);
        }

        public int getLayers(int x, int z) {
            return this.map.getLayers(x, z);
        }

        public static class OverhandMap {
            private record HeightValues(int height, int gap) {
            }

            private static final int MIN_GAP = 7;
            HeightValues[][][] heightMap = new HeightValues[16][16][];
            Chunk chunk;

            public OverhandMap(Chunk chunk) {
                this.chunk = chunk;
                init();
            }

            public int getLayers(int x, int z) {
                int cx = (x - chunk.pos.x * 16) & 15;
                int cz = (z - chunk.pos.z * 16) & 15;
                return this.heightMap[cx][cz].length;
            }

            public int getHeight(int x, int z, int index) {
                int cx = (x - chunk.pos.x * 16) & 15;
                int cz = (z - chunk.pos.z * 16) & 15;
                HeightValues[] tmp = this.heightMap[cx][cz];
                if (index + 1 > tmp.length) {
                    return 0;
                }
                return tmp[index].height();
            }

            public int getGap(int x, int z, int index) {
                int cx = (x - chunk.pos.x * 16) & 15;
                int cz = (z - chunk.pos.z * 16) & 15;
                HeightValues[] tmp = this.heightMap[cx][cz];
                if (index + 1 > tmp.length) {
                    return 0;
                }
                return tmp[index].gap();
            }

            private void init() {
                HeightValues[] tmp = new HeightValues[256];
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        int gap = 0;
                        int count = 0;
                        int maxY = chunk.getHeightValue(x, z);
                        for (int y = maxY; y >= 0; y--) {
                            int id = chunk.getBlockId(new ChunkTilePos(x, y, z));
                            if (Blocks.lightBlock[id] == 0) {
                                gap++;
                            } else {
                                if (gap >= MIN_GAP) {
                                    tmp[count++] = new HeightValues(y + 1, gap);
                                }
                                gap = 0;
                            }
                        }
                        heightMap[x][z] = Arrays.copyOf(tmp, count);
                    }
                }
            }
        }
    }
}

































