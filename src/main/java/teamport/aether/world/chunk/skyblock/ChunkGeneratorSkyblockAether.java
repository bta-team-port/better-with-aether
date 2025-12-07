package teamport.aether.world.chunk.skyblock;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import teamport.aether.block.AetherBlocks;

public class ChunkGeneratorSkyblockAether extends ChunkGenerator {
    public ChunkGeneratorSkyblockAether(World world) {
        super(world, new ChunkDecoratorSkyblockAether(world));
    }

    @Override
    public ChunkGeneratorResult doBlockGeneration(Chunk chunk) {
        ChunkGeneratorResult result = new ChunkGeneratorResult();

        int x;
        int z;
        for (x = -1; x <= 1; ++x) {
            for (z = -4; z <= 5; ++z) {
                this.tryPlace(chunk, result, x, 64, z, AetherBlocks.DIRT_AETHER.id());
                this.tryPlace(chunk, result, x, 65, z, AetherBlocks.DIRT_AETHER.id());
                this.tryPlace(chunk, result, x, 66, z, AetherBlocks.GRASS_AETHER.id());
            }
        }

        for (z = -1; z <= 2; ++z) {
            if (z != -1 && z != 2) {
                this.tryPlace(chunk, result, 2, 66, z, Blocks.GLOWSTONE.id());
                this.tryPlace(chunk, result, 2, 67, z, AetherBlocks.PORTAL_AETHER.id());
                this.tryPlace(chunk, result, 2, 68, z, AetherBlocks.PORTAL_AETHER.id());
                this.tryPlace(chunk, result, 2, 69, z, AetherBlocks.PORTAL_AETHER.id());
                this.tryPlace(chunk, result, 2, 70, z, Blocks.GLOWSTONE.id());
            } else {
                this.tryPlace(chunk, result, 2, 66, z, Blocks.GLOWSTONE.id());
                this.tryPlace(chunk, result, 2, 67, z, Blocks.GLOWSTONE.id());
                this.tryPlace(chunk, result, 2, 68, z, Blocks.GLOWSTONE.id());
                this.tryPlace(chunk, result, 2, 69, z, Blocks.GLOWSTONE.id());
                this.tryPlace(chunk, result, 2, 70, z, Blocks.GLOWSTONE.id());
            }
        }

        for (x = -68; x <= -66; ++x) {
            for (z = -2; z <= 2; ++z) {
                this.tryPlace(chunk, result, x, 64, z, AetherBlocks.QUICKSOIL.id());
                this.tryPlace(chunk, result, x, 65, z, AetherBlocks.QUICKSOIL.id());
                this.tryPlace(chunk, result, x, 66, z, AetherBlocks.QUICKSOIL.id());
            }
        }

        return result;
    }

    public boolean contains(Chunk chunk, int xBlock, int zBlock) {
        return chunk.xPosition == xBlock >> 4 && chunk.zPosition == zBlock >> 4;
    }

    public void tryPlace(Chunk chunk, ChunkGeneratorResult result, int xBlock, int yBlock, int zBlock, int blockId) {
        if (this.contains(chunk, xBlock, zBlock)) {
            result.setBlock(xBlock & 15, yBlock, zBlock & 15, blockId);
        }
    }
}
