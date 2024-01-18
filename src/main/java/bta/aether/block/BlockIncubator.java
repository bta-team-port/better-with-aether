package bta.aether.block;

import bta.aether.gui.IAetherGuis;
import bta.aether.tile.TileEntityEnchanter;
import bta.aether.tile.TileEntityIncubator;
import net.minecraft.core.block.BlockTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;

public class BlockIncubator extends BlockTileEntity {
    public BlockIncubator(String key, int id, Material material) {
        super(key, id, material);
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityIncubator();
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        if (!world.isClientSide) {
            TileEntityIncubator tile = (TileEntityIncubator) world.getBlockTileEntity(x, y, z);
            if (tile != null) {
                ((IAetherGuis)player).aether$displayGUIIncubator(tile);
            }
        }
        return true;
    }

}
