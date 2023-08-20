package bta.aether.block;

import bta.aether.tile.TileEntityIncubator;
import net.minecraft.core.block.BlockTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;

public class BlockIncubator extends BlockTileEntity {
    public BlockIncubator(String key, int id, Material material) {
        super(key, id, material);
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityIncubator();
    }
}
