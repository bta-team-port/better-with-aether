package teamport.aether.world.chunk.skyblock;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
import teamport.aether.block.AetherBlocks;
import teamport.aether.item.AetherItems;
import teamport.aether.world.feature.terrain.WorldFeatureAetherClouds;
import teamport.aether.world.feature.terrain.WorldFeatureAetherTree;

import java.util.Random;

public class ChunkDecoratorSkyblockAether implements ChunkDecorator {
    private final World world;

    public ChunkDecoratorSkyblockAether(World world) {
        this.world = world;
    }

    @Override
    public void decorate(Chunk chunk) {
        int chunkX = chunk.xPosition;
        int chunkZ = chunk.zPosition;
        int x = chunkX * 16;
        int z = chunkZ * 16;
        Random rand = new Random(this.world.getRandomSeed());
        rand.setSeed(this.world.getRandomSeed());
        long l1 = rand.nextLong() / 2L * 2L + 1L;
        long l2 = rand.nextLong() / 2L * 2L + 1L;
        rand.setSeed(chunkX * l1 + chunkZ * l2 ^ this.world.getRandomSeed());

        int xPosition = x + 8;
        int yPosition;
        int zPosition = z + 8;

        if (rand.nextInt(24) == 0) {
            yPosition = rand.nextInt(32) + 224;
            (new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_GOLD.id(), 4)).place(this.world, rand, xPosition, yPosition, zPosition);
        }
        if (rand.nextInt(24) == 0) {
            yPosition = rand.nextInt(64) + 128;
            (new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_BLUE.id(), 8)).place(this.world, rand, xPosition, yPosition, zPosition);
        }
        if (rand.nextInt(12) == 0) {
            yPosition = rand.nextInt(256);
            (new WorldFeatureAetherClouds(AetherBlocks.AERCLOUD_WHITE.id(), 16)).place(this.world, rand, xPosition, yPosition, zPosition);
        }


        for (z = 0; z <= 1; ++z) {
            if (this.contains(chunk, 2, z)) {
                chunk.setBlockMetadata(2, 67, z & 15, 1);
                chunk.setBlockMetadata(2, 68, z & 15, 1);
                chunk.setBlockMetadata(2, 69, z & 15, 1);
            }
        }

        if (this.contains(chunk, 0, -3)) {
            (new WorldFeatureAetherTree(AetherBlocks.LEAVES_SKYROOT.id(), AetherBlocks.LOG_SKYROOT.id(), 6)).place(chunk.world, new Random(0L), 0, 67, -3);
        }

        TileEntity tileEntity;
        TileEntityChest chestEntity;
        if (this.contains(chunk, 0, -3)) {
            chunk.setBlockIDWithMetadata(0, 67, 14, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 2);
            tileEntity = chunk.getTileEntity(0, 67, 14);
            if (tileEntity instanceof TileEntityChest) {
                chestEntity = (TileEntityChest) tileEntity;
                chestEntity.setItem(0, new ItemStack(Blocks.SAPLING_CACAO, 1));
                chestEntity.setItem(1, new ItemStack(Items.ORE_RAW_IRON, 8));
                chestEntity.setItem(2, new ItemStack(Items.INGOT_STEEL_CRUDE, 6));
                chestEntity.setItem(3, new ItemStack(Items.DUST_REDSTONE, 1));
            }
        }

        if (this.contains(chunk, -67, 0)) {
            chunk.setBlockIDWithMetadata(13, 67, 0, AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 1);
            tileEntity = chunk.getTileEntity(13, 67, 0);
            if (tileEntity instanceof TileEntityChest) {
                chestEntity = (TileEntityChest) tileEntity;
                chestEntity.setItem(0, new ItemStack(AetherItems.LIFESHARD, 1));
                chestEntity.setItem(1, new ItemStack(Items.NETHERCOAL, 1));
                chestEntity.setItem(2, new ItemStack(Items.DIAMOND, 1));
                chestEntity.setItem(3, new ItemStack(AetherBlocks.SAPLING_OAK_GOLDEN, 1));
            }
        }

        this.tryPlace(chunk, 1, 67, 0, AetherBlocks.FLOWER_PURPLE.id());
        this.tryPlace(chunk, -1, 67, 2, AetherBlocks.FLOWER_WHITE.id());

    }

    public boolean contains(Chunk chunk, int xBlock, int zBlock) {
        return chunk.xPosition == xBlock >> 4 && chunk.zPosition == zBlock >> 4;
    }

    public void tryPlace(Chunk chunk, int xBlock, int yBlock, int zBlock, int blockId) {
        if (this.contains(chunk, xBlock, zBlock)) {
            chunk.setBlockID(xBlock & 15, yBlock, zBlock & 15, blockId);
        }
    }
}
