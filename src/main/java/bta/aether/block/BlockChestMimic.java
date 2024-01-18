package bta.aether.block;

import bta.aether.entity.EntityMimic;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;
import net.minecraft.core.world.World;

public class BlockChestMimic extends Block {
    public BlockChestMimic(String key, int id, Material material) {
        super(key, id, material);
    }

    public void setDefaultDirection(World world, int i, int j, int k) {
        if (!world.isClientSide) {
            int l = world.getBlockId(i, j, k - 1);
            int i1 = world.getBlockId(i, j, k + 1);
            int j1 = world.getBlockId(i - 1, j, k);
            int k1 = world.getBlockId(i + 1, j, k);
            byte byte0 = 3;
            if (Block.solid[l] && !Block.solid[i1]) {
                byte0 = 3;
            }

            if (Block.solid[i1] && !Block.solid[l]) {
                byte0 = 2;
            }

            if (Block.solid[j1] && !Block.solid[k1]) {
                byte0 = 5;
            }

            if (Block.solid[k1] && !Block.solid[j1]) {
                byte0 = 4;
            }

            world.setBlockMetadataWithNotify(i, j, k, byte0);
        }
    }

    public void onBlockPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight) {
        world.setBlockMetadataWithNotify(x, y, z, entity.getHorizontalPlacementDirection(side).getOpposite().getId());
    }

    public int getBlockTextureFromSideAndMetadata(Side side, int data) {
        int index = Sides.orientationLookUpHorizontal[6 * Math.min(data, 5) + side.getId()];
        return this.atlasIndices[index];
    }

    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        this.setDefaultDirection(world, x, y, z);
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        world.setBlockWithNotify(x, y, z, 0);
        EntityMimic mimic = new EntityMimic(world);
        mimic.spawnInit();
        mimic.moveTo(x, y, z, 0.0F, 0.0F);
        world.entityJoinedWorld(mimic);
        return true;
    }
}
