package bta.aether.tile;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;

public class TileEntityIncubator extends TileEntity {

    // TODO this is an IINVENTORY method, yet this class does not implement IINVENTORY
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        if(worldObj.getBlockTileEntity(x, y, z) != this)
        {
            return false;
        }
        return entityplayer.distanceToSqr((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D) <= 64D;
    }
}
