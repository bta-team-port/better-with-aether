package bta.aether.block;

import bta.aether.Aether;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;

import java.util.Random;

public class BlockAetherFlower extends Block {
    public boolean killedByWeather = false;
    public BlockAetherFlower(String key, int id) {
        super(key, id, Material.plant);
        this.setTicking(true);
        float f = 0.2F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
    }


    public Block setKilledByWeather() {
        this.killedByWeather = true;
        return this;
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z) && this.canThisPlantGrowOnThisBlockID(world.getBlockId(x, y - 1, z));
    }

    protected boolean canThisPlantGrowOnThisBlockID(int i) {
        return i == AetherBlocks.grassAether.id;
    }


    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        super.onNeighborBlockChange(world, x, y, z, blockId);
        this.func_268_h(world, x, y, z);
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        this.func_268_h(world, x, y, z);
        if (world.seasonManager.getCurrentSeason() != null && world.seasonManager.getCurrentSeason().killFlowers && this.killedByWeather && rand.nextInt(256) == 0) {
            world.setBlockWithNotify(x, y, z, 0);
        }

    }

    protected final void func_268_h(World world, int i, int j, int k) {
        if (!this.canBlockStay(world, i, j, k)) {
            this.dropBlockWithCause(world, EnumDropCause.WORLD, i, j, k, world.getBlockMetadata(i, j, k), null);
            world.setBlockWithNotify(i, j, k, 0);
        }

    }

    public boolean canBlockStay(World world, int x, int y, int z) {
        return (world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z));
    }

    public AABB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }
}

