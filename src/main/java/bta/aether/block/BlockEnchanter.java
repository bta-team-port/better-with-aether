package bta.aether.block;

import bta.aether.gui.IAetherGuis;
import bta.aether.tile.TileEntityEnchanter;
import net.minecraft.core.block.BlockTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import java.util.Random;

public class BlockEnchanter extends BlockTileEntity {
    public BlockEnchanter(String key, int id, Material material) {
        super(key, id, material);
    }

    @Override
    public void onBlockRemoved(World world, int i, int j, int k, int data) {
        TileEntityEnchanter tile = (TileEntityEnchanter) world.getBlockTileEntity(i, j, k);
        if (tile != null) {
            Random random = new Random();
            for (int l = 0; l < tile.getSizeInventory(); ++l) {
                ItemStack itemstack = tile.getStackInSlot(l);
                if (itemstack != null) {
                    float f = random.nextFloat() * 0.8F + 0.1F;
                    float f1 = random.nextFloat() * 0.8F + 0.1F;
                    float f2 = random.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int i1 = random.nextInt(21) + 10;
                        if (i1 > itemstack.stackSize) {
                            i1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= i1;
                        EntityItem entityitem = new EntityItem(world, (float) i + f, (float) j + f1, (float) k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getMetadata()));
                        float f3 = 0.05F;
                        entityitem.xd = (float) random.nextGaussian() * f3;
                        entityitem.yd = (float) random.nextGaussian() * f3 + 0.2F;
                        entityitem.zd = (float) random.nextGaussian() * f3;
                        world.entityJoinedWorld(entityitem);
                    }
                }
            }
        }

        super.onBlockRemoved(world, i, j, k, data);
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        if (!world.isClientSide) {
            TileEntityEnchanter tile = (TileEntityEnchanter) world.getBlockTileEntity(x, y, z);
            if (tile != null) {
                ((IAetherGuis)player).aether$displayGUIEnchanter(tile);
            }
        }
        return true;
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityEnchanter();
    }


}
