package bta.aether.block;

import bta.aether.entity.fx.EntityFlameAmbrosiumFX;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.fx.EntityFlameFX;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;

import java.util.Random;

public class BlockAmbrosiumTorch extends Block {
    public BlockAmbrosiumTorch(String key, int id) {
        super(key, id, Material.decoration);
        this.setTicking(true);
    }

    public AABB getCollisionBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
        return null;
    }

    public boolean isSolidRender() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    private boolean canPlaceOnTop(World world, int x, int y, int z) {
        int id = world.getBlockId(x, y, z);
        return world.canPlaceOnSurfaceOfBlock(x, y, z) || id == Block.fencePlanksOak.id || id == Block.fencePlanksOakPainted.id;
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        if (world.isBlockNormalCube(x - 1, y, z)) {
            return true;
        } else if (world.isBlockNormalCube(x + 1, y, z)) {
            return true;
        } else if (world.isBlockNormalCube(x, y, z - 1)) {
            return true;
        } else {
            return world.isBlockNormalCube(x, y, z + 1) || world.canPlaceOnSurfaceOfBlock(x, y - 1, z);
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

    public void onBlockAdded(World world, int x, int y, int z) {
        if (world.isBlockNormalCube(x - 1, y, z)) {
            world.setBlockMetadataWithNotify(x, y, z, 1);
        } else if (world.isBlockNormalCube(x + 1, y, z)) {
            world.setBlockMetadataWithNotify(x, y, z, 2);
        } else if (world.isBlockNormalCube(x, y, z - 1)) {
            world.setBlockMetadataWithNotify(x, y, z, 3);
        } else if (world.isBlockNormalCube(x, y, z + 1)) {
            world.setBlockMetadataWithNotify(x, y, z, 4);
        } else if (this.canPlaceOnTop(world, x, y - 1, z)) {
            world.setBlockMetadataWithNotify(x, y, z, 5);
        }

        this.dropTorchIfCantStay(world, x, y, z);
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        if (this.dropTorchIfCantStay(world, x, y, z)) {
            int i1 = world.getBlockMetadata(x, y, z);
            boolean flag = !world.isBlockNormalCube(x - 1, y, z) && i1 == 1;

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
                this.dropBlockWithCause(world, EnumDropCause.WORLD, x, y, z, i1, null);
                world.setBlockWithNotify(x, y, z, 0);
            }
        }

    }

    private boolean dropTorchIfCantStay(World world, int i, int j, int k) {
        if (!this.canPlaceBlockAt(world, i, j, k)) {
            this.dropBlockWithCause(world, EnumDropCause.WORLD, i, j, k, world.getBlockMetadata(i, j, k), null);
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
            this.setBlockBounds(0.0, 0.20000000298023224, 0.5F - f, f * 2.0F, 0.800000011920929, 0.5F + f);
        } else if (l == 2) {
            this.setBlockBounds(1.0F - f * 2.0F, 0.20000000298023224, 0.5F - f, 1.0, 0.800000011920929, 0.5F + f);
        } else if (l == 3) {
            this.setBlockBounds(0.5F - f, 0.20000000298023224, 0.0, 0.5F + f, 0.800000011920929, f * 2.0F);
        } else if (l == 4) {
            this.setBlockBounds(0.5F - f, 0.20000000298023224, 1.0F - f * 2.0F, 0.5F + f, 0.800000011920929, 1.0);
        } else {
            float f1 = 0.1F;
            this.setBlockBounds(0.5F - f1, 0.0, 0.5F - f1, 0.5F + f1, 0.6000000238418579, 0.5F + f1);
        }

        return super.collisionRayTrace(world, x, y, z, start, end);
    }

    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        int meta = world.getBlockMetadata(x, y, z);
        Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
        double xPos = (double)x + 0.5;
        double yPos = (double)y + 0.7;
        double zPos = (double)z + 0.5;
        double d3 = 0.22;
        double d4 = 0.27;
        if (meta == 1) {
            mc.effectRenderer.addEffect(new EntityFlameAmbrosiumFX(world, xPos - d4, yPos + d3, zPos, 0.0, 0.0, 0.0, EntityFlameFX.Type.ORANGE));
        } else if (meta == 2) {
            mc.effectRenderer.addEffect(new EntityFlameAmbrosiumFX(world, xPos + d4, yPos + d3, zPos, 0.0, 0.0, 0.0, EntityFlameFX.Type.ORANGE));
        } else if (meta == 3) {
            mc.effectRenderer.addEffect(new EntityFlameAmbrosiumFX(world, xPos, yPos + d3, zPos - d4, 0.0, 0.0, 0.0, EntityFlameFX.Type.ORANGE));
        } else if (meta == 4) {
            mc.effectRenderer.addEffect(new EntityFlameAmbrosiumFX(world, xPos, yPos + d3, zPos + d4, 0.0, 0.0, 0.0, EntityFlameFX.Type.ORANGE));
        } else {
            mc.effectRenderer.addEffect(new EntityFlameAmbrosiumFX(world, xPos, yPos, zPos, 0.0, 0.0, 0.0, EntityFlameFX.Type.ORANGE));
        }

    }

    public int getLightmapCoord(WorldSource blockAccess, int x, int y, int z) {
        return blockAccess.getLightmapCoord(x, y, z, lightEmission[this.id] > 0 ? 15 : 0);
    }

}

