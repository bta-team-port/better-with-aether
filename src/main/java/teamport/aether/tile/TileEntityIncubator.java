package teamport.aether.tile;


import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.BlockLogicIncubator;

// TODO implement the class, this should be mostly a port from 7.2
public class TileEntityIncubator extends AetherTileEntityMachine {
    /// canSmelt                -> canProcess
    /// smeltItem               -> processItem
    /// updateFurnace           -> updateContainer
    /// getBurnTimeFromItem     -> getEnergyTimeFromItem
    /// getCookProgressScaled   -> getProgressScale
    /// maxEnergyTime           -> maxBurnTime
    /// currentEnergyTime       -> currentBurnTime
    /// maxProcessTime          -> maxCookTime
    /// currentProcessTime      -> currentCookTime

    public TileEntityIncubator(){this.containerItemStacks = new ItemStack[2];}

    @Override
    public String getNameTranslationKey(){
        return "aether.container.incubator.name";
    }

    @Override
    public void tick(){}

    @Override
    public boolean canProcess() {return false;}

    @Override
    public void processItem() {}

    @Override
    public void updateContainer(boolean forceLit) {
        if (this.worldObj != null) {
            BlockLogicIncubator.updateFurnaceBlockState(forceLit | this.currentEnergyTime > 0, this.worldObj, this.x, this.y, this.z);
            return;
        }
        if (this.carriedBlock != null) {
            this.carriedBlock.blockId = forceLit | this.currentEnergyTime > 0 ? AetherBlocks.INCUBATOR_ACTIVE.id() : AetherBlocks.INCUBATOR_IDLE.id();
        }
    }

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
    public void dropContents(World world, int x, int y, int z) {
        super.dropContents(world, x, y, z);
        if (!BlockLogicIncubator.keepIncubatorInventory) {
            for (int l = 0; l < this.getContainerSize(); ++l) {
                ItemStack itemstack = this.getItem(l);
                if (itemstack != null) {
                    float f = this.random.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.random.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int i1 = this.random.nextInt(21) + 10;
                        if (i1 > itemstack.stackSize) {
                            i1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= i1;
                        EntityItem entityItem = new EntityItem(
                                world, (float) x + f, (float) y + f1, (float) z + f2,
                                new ItemStack(itemstack.itemID, i1, itemstack.getMetadata()));
                        float f3 = 0.05F;
                        entityItem.xd = (float) this.random.nextGaussian() * f3;
                        entityItem.yd = (float) this.random.nextGaussian() * f3 + 0.2F;
                        entityItem.zd = (float) this.random.nextGaussian() * f3;
                        world.entityJoinedWorld(entityItem);
                    }
                }
            }
        }
    }
}
