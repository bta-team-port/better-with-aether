package teamport.aether.tile;


import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.AetherRecipes;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.BlockLogicEnchanter;
import teamport.aether.lookup.LookupFuelEnchanter;


// TODO implement the class, this should be mostly a port from 7.2
public class TileEntityEnchanter extends AetherTileEntityProcessor {

    /// missing tick                    -> tick
    /// missing canSmelt                -> canProcess
    /// missing smeltItem               -> processItem
    /// missing updateFurnace           -> updateContainer
    /// missing getBurnTimeFromItem     -> getEnergyTimeFromItem
    ///  missing getCookProgressScaled  -> getProgressScale

    @Override
    public String getNameTranslationKey() {
        return "aether.container.enchanter.name";
    }

    @Override
    public void tick() {
//        boolean isBurnTimeHigherThan0 = this.currentBurnTime > 0;
//        boolean furnaceUpdated = false;
//        if (this.currentBurnTime > 0) {
//            --this.currentBurnTime;
//        }
//
//        if (this.worldObj == null || !this.worldObj.isClientSide) {
//            if ((this.worldObj == null || this.worldObj.getBlockId(this.x, this.y, this.z) == Blocks.FURNACE_STONE_IDLE.id()) && this.currentBurnTime == 0 && this.furnaceItemStacks[0] == null && this.furnaceItemStacks[1] != null && this.furnaceItemStacks[1].itemID == Blocks.COBBLE_NETHERRACK.id()) {
//                --this.furnaceItemStacks[1].stackSize;
//                if (this.furnaceItemStacks[1].stackSize <= 0) {
//                    this.furnaceItemStacks[1] = null;
//                }
//
//                this.updateFurnace(true);
//                furnaceUpdated = true;
//            }
//
//            if (this.currentBurnTime == 0 && this.furnaceItemStacks[1] != null && this.canSmelt()) {
//                this.maxBurnTime = this.currentBurnTime = this.getBurnTimeFromItem(this.furnaceItemStacks[1]);
//                if (this.currentBurnTime > 0) {
//                    furnaceUpdated = true;
//                    if (this.furnaceItemStacks[1] != null) {
//                        if (this.furnaceItemStacks[1].getItem() == Items.BUCKET_LAVA) {
//                            this.furnaceItemStacks[1] = new ItemStack(Items.BUCKET);
//                        } else {
//                            --this.furnaceItemStacks[1].stackSize;
//                            if (this.furnaceItemStacks[1].stackSize <= 0) {
//                                this.furnaceItemStacks[1] = null;
//                            }
//                        }
//                    }
//                }
//            }
//
//            if (this.isBurning() && this.canSmelt()) {
//                ++this.currentCookTime;
//                if (this.currentCookTime == this.maxCookTime) {
//                    this.currentCookTime = 0;
//                    this.smeltItem();
//                    furnaceUpdated = true;
//                }
//            } else {
//                this.currentCookTime = 0;
//            }
//
//            if (isBurnTimeHigherThan0 != this.currentBurnTime > 0) {
//                furnaceUpdated = true;
//                this.updateFurnace(false);
//            }
//        }
//
//        if (furnaceUpdated) {
//            this.setChanged();
//        }

    }

    @Override
    protected boolean canProcess() {
        if (this.containerItemStacks[0] == null) {
            return false;
        }
        ItemStack resultStack = AetherRecipes.ENCHANTER.findOutput(containerItemStacks[0]);
        if (resultStack == null) {
            return false;
        }
        ItemStack resultItem = this.containerItemStacks[2];
        if (resultItem == null) {
            return true;
        }
        if (!resultItem.isItemEqual(resultStack)) {
            return false;
        }
        if (resultItem.stackSize < this.getMaxStackSize()
                && resultItem.stackSize < resultItem.getMaxStackSize()) {
            return true;
        }
        return resultItem.stackSize < resultStack.getMaxStackSize();
    }

    @Override
    public void processItem() {
        if (!this.canProcess()) {
            return;
        }
        ItemStack processedItem = AetherRecipes.ENCHANTER.findOutput(containerItemStacks[0]);

        boolean wasEmpty = this.containerItemStacks[2] == null;
        if (this.containerItemStacks[2] == null && processedItem != null) {
            this.containerItemStacks[2] = processedItem.copy();
        } else if (this.containerItemStacks[2] != null && processedItem != null && this.containerItemStacks[2].itemID == processedItem.itemID) {
            ItemStack resultItem = this.containerItemStacks[2];
            resultItem.stackSize += processedItem.stackSize;
        }

        --this.containerItemStacks[0].stackSize;
        if (this.containerItemStacks[0].stackSize <= 0) {
            this.containerItemStacks[0] = null;
        }

        if (this.worldObj != null && wasEmpty && this.containerItemStacks[2] != null) {
            this.worldObj.markBlockNeedsUpdate(this.x, this.y, this.z);
        }
    }

    @Override
    protected void updateContainer(boolean forceLit) {
        if (this.worldObj != null) {
            BlockLogicEnchanter.updateFurnaceBlockState(forceLit | this.currentEnergyTime > 0, this.worldObj, this.x, this.y, this.z);
            return;
        }
        if (this.carriedBlock != null) {
            this.carriedBlock.blockId = forceLit | this.currentEnergyTime > 0 ? AetherBlocks.ENCHANTER_ACTIVE.id() : AetherBlocks.ENCHANTER_IDLE.id();
        }
    }

    @Override
    public int getEnergyTimeFromItem(ItemStack itemStack) {
        return itemStack == null ? 0 : LookupFuelEnchanter.instance.getFuelYield(itemStack.getItem().id);
    }

    @Override
    public boolean stillValid(Player entityplayer) {
        if (this.worldObj != null && this.worldObj.getTileEntity(this.x, this.y, this.z) == this) {
            return entityplayer.distanceToSqr((double) this.x + (double) 0.5F, (double) this.y + (double) 0.5F, (double) this.z + (double) 0.5F) <= (double) 64.0F;
        } else {
            return false;
        }
    }

    @Override
    public void dropContents(World world, int x, int y, int z) {
        super.dropContents(world, x, y, z);
        if (!BlockLogicEnchanter.keepEnchanterInventory) {
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
                                world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2),
                                new ItemStack(itemstack.itemID, i1, itemstack.getMetadata()));
                        float f3 = 0.05F;
                        entityItem.xd = (double) ((float) this.random.nextGaussian() * f3);
                        entityItem.yd = (double) ((float) this.random.nextGaussian() * f3 + 0.2F);
                        entityItem.zd = (double) ((float) this.random.nextGaussian() * f3);
                        world.entityJoinedWorld(entityItem);
                    }
                }
            }
        }
    }
}
