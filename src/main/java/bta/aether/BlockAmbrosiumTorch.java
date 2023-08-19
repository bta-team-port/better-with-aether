package bta.aether;

import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;

import java.util.Random;

public class BlockAmbrosiumTorch extends Block {
    public BlockAmbrosiumTorch(String key, int id) {
        super(key, id, Material.decoration);
        this.setTickOnLoad(true);
    }

    public int getBlockTextureFromSideAndMetadata(Side side, int meta) {
        return side == Side.TOP ? Block.wireRedstone.getBlockTextureFromSideAndMetadata(side, meta) : super.getBlockTextureFromSideAndMetadata(side, meta);
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

    private boolean canPlaceOnTop(World world, int i, int j, int k) {
        int id = world.getBlockId(i, j, k);
        return world.isBlockNormalCube(i, j, k) || id == Block.fencePlanksOak.id || id == Block.fencePlanksOakPainted.id;
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        if (world.isBlockNormalCube(x - 1, y, z)) {
            return true;
        } else if (world.isBlockNormalCube(x + 1, y, z)) {
            return true;
        } else if (world.isBlockNormalCube(x, y, z - 1)) {
            return true;
        } else {
            return world.isBlockNormalCube(x, y, z + 1) ? true : world.canPlaceOnSurfaceOfBlock(x, y - 1, z);
        }
    }

    public void onBlockPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight) {
        int l = side.getId();
        int i1 = world.getBlockMetadata(x, y, z);
        if (l == 1 && this.canPlaceOnTop(world, x, y - 1, z)) {
            i1 = 5;
        }

        if (l == 2 && world.isBlockNormalCube(x, y, z + 1)) {
            i1 = 4;
        }

        if (l == 3 && world.isBlockNormalCube(x, y, z - 1)) {
            i1 = 3;
        }

        if (l == 4 && world.isBlockNormalCube(x + 1, y, z)) {
            i1 = 2;
        }

        if (l == 5 && world.isBlockNormalCube(x - 1, y, z)) {
            i1 = 1;
        }

        world.setBlockMetadataWithNotify(x, y, z, i1);
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        if (world.getBlockMetadata(x, y, z) == 0) {
            this.onBlockAdded(world, x, y, z);
        }

    }

    public void onBlockAdded(World world, int i, int j, int k) {
        if (world.isBlockNormalCube(i - 1, j, k)) {
            world.setBlockMetadataWithNotify(i, j, k, 1);
        } else if (world.isBlockNormalCube(i + 1, j, k)) {
            world.setBlockMetadataWithNotify(i, j, k, 2);
        } else if (world.isBlockNormalCube(i, j, k - 1)) {
            world.setBlockMetadataWithNotify(i, j, k, 3);
        } else if (world.isBlockNormalCube(i, j, k + 1)) {
            world.setBlockMetadataWithNotify(i, j, k, 4);
        } else if (this.canPlaceOnTop(world, i, j - 1, k)) {
            world.setBlockMetadataWithNotify(i, j, k, 5);
        }

        this.dropTorchIfCantStay(world, i, j, k);
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        if (this.dropTorchIfCantStay(world, x, y, z)) {
            int i1 = world.getBlockMetadata(x, y, z);
            boolean flag = false;
            if (!world.isBlockNormalCube(x - 1, y, z) && i1 == 1) {
                flag = true;
            }

            if (!world.isBlockNormalCube(x + 1, y, z) && i1 == 2) {
                flag = true;
            }

            if (!world.isBlockNormalCube(x, y, z - 1) && i1 == 3) {
                flag = true;
            }

            if (!world.isBlockNormalCube(x, y, z + 1) && i1 == 4) {
                flag = true;
            }

            if (!this.canPlaceOnTop(world, x, y - 1, z) && i1 == 5) {
                flag = true;
            }

            if (flag) {
                this.dropBlockWithCause(world, EnumDropCause.WORLD, x, y, z, i1, (TileEntity)null);
                world.setBlockWithNotify(x, y, z, 0);
            }
        }

    }

    private boolean dropTorchIfCantStay(World world, int i, int j, int k) {
        if (!this.canPlaceBlockAt(world, i, j, k)) {
            this.dropBlockWithCause(world, EnumDropCause.WORLD, i, j, k, world.getBlockMetadata(i, j, k), (TileEntity)null);
            world.setBlockWithNotify(i, j, k, 0);
            return false;
        } else {
            return true;
        }
    }

    public HitResult collisionRayTrace(World world, int x, int y, int z, Vec3d start, Vec3d end) {
        int l = world.getBlockMetadata(x, y, z) & 7;
        float f = 0.15F;
        if (l == 1) {
            this.setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        } else if (l == 2) {
            this.setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        } else if (l == 3) {
            this.setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        } else if (l == 4) {
            this.setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        } else {
            float f1 = 0.1F;
            this.setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, 0.6F, 0.5F + f1);
        }

        return super.collisionRayTrace(world, x, y, z, start, end);
    }

    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        int l = world.getBlockMetadata(x, y, z);
        double d = (double)((float)x + 0.5F);
        double d1 = (double)((float)y + 0.7F);
        double d2 = (double)((float)z + 0.5F);
        double d3 = 0.22;
        double d4 = 0.27;
        if (l == 1) {
            world.spawnParticle("smoke", d - d4, d1 + d3, d2, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", d - d4, d1 + d3, d2, 0.0, 0.0, 0.0);
        } else if (l == 2) {
            world.spawnParticle("smoke", d + d4, d1 + d3, d2, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", d + d4, d1 + d3, d2, 0.0, 0.0, 0.0);
        } else if (l == 3) {
            world.spawnParticle("smoke", d, d1 + d3, d2 - d4, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", d, d1 + d3, d2 - d4, 0.0, 0.0, 0.0);
        } else if (l == 4) {
            world.spawnParticle("smoke", d, d1 + d3, d2 + d4, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", d, d1 + d3, d2 + d4, 0.0, 0.0, 0.0);
        } else {
            world.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", d, d1, d2, 0.0, 0.0, 0.0);
        }

    }
}

