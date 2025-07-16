package teamport.aether.tile;


import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

// TODO implement the class, this should be mostly a port from 7.2
public class TileEntityIncubator extends AetherTileEntityProcessor {

    /// missing tick                    -> tick
    /// missing canSmelt                -> canProcess
    /// missing smeltItem               -> processItem
    /// missing updateFurnace           -> updateContainer
    /// missing getBurnTimeFromItem     -> getEnergyTimeFromItem
    ///  missing getCookProgressScaled  -> getProgressScale

    @Override
    public String getNameTranslationKey(){
        return "aether.container.enchanter.name";
    }

    @Override
    public void tick(){}

    @Override
    public boolean canProcess() {return false;}

    @Override
    public void processItem() {}

    @Override
    protected void updateContainer(boolean forceLit){}

    @Override
    public int getEnergyTimeFromItem(ItemStack itemStack){return 0;}

    @Override
    public boolean stillValid(Player entityplayer) {
        if (this.worldObj != null && this.worldObj.getTileEntity(this.x, this.y, this.z) == this) {
            return entityplayer.distanceToSqr((double)this.x + (double)0.5F, (double)this.y + (double)0.5F, (double)this.z + (double)0.5F) <= (double)64.0F;
        } else {
            return false;
        }
    }

    @Override
    public void dropContents(World world, int x, int y, int z) {}

    @Override
    public void sortContainer() {}

}
