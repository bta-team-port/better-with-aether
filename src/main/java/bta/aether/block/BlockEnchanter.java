package bta.aether.block;

import bta.aether.gui.GuiEnchanter;
import bta.aether.tile.TileEntityEnchanter;
import net.minecraft.client.Minecraft;
import net.minecraft.core.block.BlockTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;

public class BlockEnchanter extends BlockTileEntity {
    public BlockEnchanter(String key, int id, Material material) {
        super(key, id, material);
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        if (!world.isClientSide) {
            TileEntityEnchanter tile = (TileEntityEnchanter) world.getBlockTileEntity(x, y, z);
            if (tile != null) {
                //TODO: mp eventually -martin
                Minecraft.getMinecraft(Minecraft.class).displayGuiScreen(new GuiEnchanter(player.inventory, tile));
            }
        }
        return true;
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityEnchanter();
    }


}
