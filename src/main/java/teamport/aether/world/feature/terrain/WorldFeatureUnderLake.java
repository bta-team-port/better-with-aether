package teamport.aether.world.feature.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import teamport.aether.block.AetherBlockTags;

import java.util.*;

public class WorldFeatureUnderLake implements WorldFeatureInterface {
    private static int MAX_DISTANCE = 64;
    private final WorldFeatureAetherLake lake;
    private final Block<?> flowingBlock;
    private final boolean isFluidBlock;


    public WorldFeatureUnderLake(
        @NotNull Block<?> fluidBlock, @NotNull Block<?> flowingBlock,
        @NotNull Block<?> shoreBase, @NotNull Block<?> shoreCover,
        boolean isWorldFiller, boolean isFluidBlock
    ) {
        this(new WorldFeatureAetherLake(fluidBlock, shoreBase, shoreCover, isWorldFiller), flowingBlock, isFluidBlock);
    }

    public WorldFeatureUnderLake(
        @NotNull Block<?> fluidBlock, @NotNull Block<?> flowingBlock,
        @NotNull Block<?> shoreBase, @NotNull Block<?> shoreCover,
        boolean isWorldFiller
    ) {
        this(new WorldFeatureAetherLake(fluidBlock, shoreBase, shoreCover, isWorldFiller), flowingBlock, true);
    }

    public WorldFeatureUnderLake(WorldFeatureAetherLake lake, @NotNull Block<?> flowingBlock) {
        this(lake, flowingBlock, true);
    }

    public WorldFeatureUnderLake(WorldFeatureAetherLake lake, @NotNull Block<?> flowingBlock, boolean isFluidBlock) {
        this.lake = lake;
        this.flowingBlock = flowingBlock;
        this.isFluidBlock = isFluidBlock;
    }

    public static final class WaterFallConfig {
        private final WorldFeatureAetherLake.LakeConfig lakeConfig;
        private final TilePosc fallPos;

        public WaterFallConfig(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc, int gap){
            this.lakeConfig = WorldFeatureAetherLake.LakeConfig.prepare(world, random, tilePosc);
            this.fallPos = new TilePos(tilePosc.x(), tilePosc.y() + gap, tilePosc.z());
        }

        public WorldFeatureAetherLake.LakeConfig lakeConfig() {
            return lakeConfig;
        }

        public TilePosc fallPos() {
            return fallPos;
        }

        public static WaterFallConfig prepare(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc, int gap) {
            return new WaterFallConfig(world, random, tilePosc, gap);
        }
    }

    @Override
    public boolean place(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc) {
        TilePos tmp = new TilePos();
        int gap = 1;
        while(world.getBlockType(tmp.set(tilePosc.x(), tilePosc.y() + gap, tilePosc.z())).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)){
            gap++;
        }
        WaterFallConfig lakeConfig = WaterFallConfig.prepare(world, random, tilePosc, gap);
        if (!this.canPlace(world, lakeConfig)) {
            return false;
        }
        return this.placeFeature(world, random, lakeConfig);
    }

    public boolean canPlace(@NotNull World world, @NotNull WaterFallConfig config) {
        return this.lake.canPlace(world, config.lakeConfig) && WorldFeatureUnderLake.canPlaceFall(world, config);
    }

    private static boolean canPlaceFall(@NotNull World world, @NotNull WaterFallConfig config) {
        TilePos tmp = new TilePos(0, 0, 0);
        int checker = 0;
        int x = config.fallPos().x();
        int y = config.fallPos().y();
        int z = config.fallPos().z();
        if (world.getBlockType(tmp.set(x + 1, y, z)).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) checker++;
        if (world.getBlockType(tmp.set(x - 1, y, z)).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) checker++;
        if (world.getBlockType(tmp.set(x, y, z + 1)).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) checker++;
        if (world.getBlockType(tmp.set(x, y, z - 1)).hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) checker++;
        return checker == 4;
    }

    public boolean placeFeature(@NotNull World world, @NotNull Random random, @NotNull WaterFallConfig config) {
        if(this.isFluidBlock) {
            world.scheduledUpdatesAreImmediate = true;
            world.setBlockType(config.fallPos, this.flowingBlock);
            this.flowingBlock.updateTick(world, config.fallPos, random, false);
            world.scheduledUpdatesAreImmediate = false;
        }
//        else{
//            this.pathFindSolidDown(world, random, config.fallPos, config.lakeConfig.origin());
//        }
        this.lake.placeFeature(world, random, config.lakeConfig);
        return true;
    }

    // work in progress
    private void pathFindSolidDown(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc, @NotNull TilePosc end) {
        boolean maxReached = false;
        Deque<TilePosc> stack = new ArrayDeque<>();
        Set<TilePosc> visited = new HashSet<>();
        stack.add(tilePosc);
        while(!stack.isEmpty()){
            if(stack.size() > 64){
                maxReached = true;
            }
            TilePosc next = stack.pop();
            if(next.equals(end)){
                return;
            }
            int x = next.x();
            int y = next.y();
            int z = next.z();
            if(visited.contains(next)){
                continue;
            }
            visited.add(next);
            int[] shuffled = this.shuffel(random);
            TilePos down = new TilePos(x, y - 1, z);
            if (world.getBlockType(down) == Blocks.AIR) {
                world.setBlockType(next, this.flowingBlock);
                if(!maxReached){
                    stack.add(down);
                }
                continue;
            }
            for (int i : shuffled) {
                Direction direction = Direction.horizontal[i];
                TilePos ndown = new TilePos(x, y - 1, z).add(direction);
                if(world.getBlockType(ndown) == Blocks.AIR){
                    world.setBlockType(ndown, this.flowingBlock);
                    if(!maxReached){
                        stack.add(ndown);
                    }
                    continue;
                }
                TilePos lvl = new TilePos(x, y, z).add(direction);
                if(world.getBlockType(lvl) == Blocks.AIR){
                    world.setBlockType(lvl, this.flowingBlock);
                    if(!maxReached){
                        stack.add(lvl);
                    }
                }
            }
        }
    }

    private int[] shuffel(Random random){
        int[] p = {0, 1, 2, 3};
        for (int i = p.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int tmp = p[i];
            p[i] = p[j];
            p[j] = tmp;
        }
        return p;
    }
}
