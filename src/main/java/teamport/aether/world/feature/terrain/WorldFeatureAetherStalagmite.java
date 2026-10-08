package teamport.aether.world.feature.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLeavesBase;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;

import java.util.Random;

public class WorldFeatureAetherStalagmite implements WorldFeatureInterface {
    private static final int MAX_SUPPORT_DEPTH = 5;
    private final Block<?> block;
    private final int heightMod;

    public WorldFeatureAetherStalagmite(Block<?> block, int heightMod) {
        this.block = block;
        this.heightMod = heightMod;
    }

    private boolean isFootPrintClear(@NotNull World world, @NotNull TilePosc tilePosc, int baseRadius) {
        int ix = tilePosc.x();
        int iy = tilePosc.y();
        int iz = tilePosc.z();
        TilePos temp = new TilePos();
        for (int x = -baseRadius; x <= baseRadius; x++) {
            for (int z = -baseRadius; z <= baseRadius; z++) {
                for(int y = 1; y < 5; y++){
                    Block<?> tile = world.getBlockType(temp.set(ix + x, iy - y, iz + z));
                    if(tile.getLogic() instanceof BlockLogicLeavesBase && tile  != AetherBlocks.QUICKSOIL){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean place(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc) {
        int ix = tilePosc.x();
        int iy = tilePosc.y();
        int iz = tilePosc.z();
        TilePos temp = new TilePos(tilePosc);
        if (world.getBlockType(temp) != Blocks.AIR || world.getBlockType(temp.down()) == Blocks.AIR) {
            return false;
        }
        int height = 4 + random.nextInt(8) + heightMod;
        int baseRadius = 1 + random.nextInt(3) + heightMod / 7;
        if (!this.isFootPrintClear(world, tilePosc, baseRadius)) {
            return false;
        }

        for (int dy = 0; dy < height; dy++) {
            double r = baseRadius * (1.0 - (double) dy / height);
            double r2 = r * r;
            for (int dx = -baseRadius; dx <= baseRadius; dx++) {
                for (int dz = -baseRadius; dz <= baseRadius; dz++) {
                    boolean center = dx == 0 && dz == 0;
                    if (!center && dx * dx + dz * dz > r2 + random.nextFloat() * 0.5) {
                        continue;
                    }
                    temp.set(ix + dx, iy + dy, iz + dz);
                    if (world.getBlockType(temp) == Blocks.AIR) {
                        this.placeWithSupport(world, temp, ix + dx, iy + dy, iz + dz, center);
                    } else if (center) {
                        return true;
                    }
                }
            }
        }
        return true;
    }

    private void placeWithSupport(World world, TilePos temp, int x, int y, int z, boolean force) {
        int depth = 1;
        while (depth <= MAX_SUPPORT_DEPTH) {
            temp.set(x, y - depth, z);
            Block<?> tile = world.getBlockType(temp);
            if (tile == Blocks.AIR || !tile.hasTag(AetherBlockTags.AETHER_TERRAIN_BLOCK)) {
                depth++;
            } else {
                break;
            }
        }
        if (depth > MAX_SUPPORT_DEPTH && !force) {
            return;
        }
        int bottom = Math.min(depth, MAX_SUPPORT_DEPTH + 1);
        for (int i = 0; i < bottom; i++) {
            temp.set(x, y - i, z);
            world.setBlockType(temp, block);
        }
    }
}
