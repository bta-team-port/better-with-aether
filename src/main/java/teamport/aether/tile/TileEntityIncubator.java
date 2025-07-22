package teamport.aether.tile;


import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.AetherRecipes;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.BlockLogicIncubator;
import teamport.aether.lookup.LookupFuelIncubator;

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
    public void tick(){
        boolean isEnergyTimeHigherThan0 = this.currentEnergyTime > 0;
        boolean updateMachine = false;
        if (this.currentEnergyTime > 0) {
            --this.currentEnergyTime;
        }
        if(canProcess()){
            this.maxProcessTime = AetherRecipes.INCUBATOR.findRecipe(containerItemStacks[0]).getData();
        }
        if (isUpdateMachine(updateMachine, isEnergyTimeHigherThan0)) {
            this.setChanged();
        }
    }

    private boolean isUpdateMachine(boolean updateMachine, boolean isEnergyTimeHigherThan0) {
        if (this.worldObj == null || !this.worldObj.isClientSide) {
            updateMachine = eternallyLit(updateMachine);

            if (this.currentEnergyTime == 0 && this.containerItemStacks[1] != null && this.canProcess()) {
                this.maxEnergyTime = this.currentEnergyTime = this.getEnergyTimeFromItem(this.containerItemStacks[1]);
                if (this.currentEnergyTime > 0) {
                    updateMachine = true;
                    if (this.containerItemStacks[1] != null) {
                        --this.containerItemStacks[1].stackSize;
                        if (this.containerItemStacks[1].stackSize <= 0) {
                            this.containerItemStacks[1] = null;
                        }

                    }
                }
            }

            if (this.isProcessing() && this.canProcess()) {
                ++this.currentProcessTime;
                if (this.currentProcessTime >= this.maxProcessTime) {
                    this.currentProcessTime = 0;
                    this.processItem();
                    updateMachine = true;
                }
            } else {
                this.currentProcessTime = 0;
            }

            if (isEnergyTimeHigherThan0 != this.currentEnergyTime > 0) {
                this.updateContainer(false);
                updateMachine = true;
            }

            if(containerItemStacks[0] == null){
                updateMachine = true;
            }

        }
        return updateMachine;
    }

    public boolean eternallyLit(boolean updateMachine) {
        if ((this.worldObj == null
                || this.worldObj.getBlockId(this.x, this.y, this.z) == AetherBlocks.INCUBATOR_IDLE.id())
                && this.currentEnergyTime == 0 && this.containerItemStacks[0] == null
                && this.containerItemStacks[1] != null
                && this.containerItemStacks[1].itemID == Blocks.WOOL.id()
        ) {
            --this.containerItemStacks[1].stackSize;
            if (this.containerItemStacks[1].stackSize <= 0) {
                this.containerItemStacks[1] = null;
            }

            this.updateContainer(true);
            return true;
        }
        return updateMachine;
    }

    @Override
    public @Nullable ItemStack removeItem(int index, int takeAmount) {
        if (this.containerItemStacks[index] != null) {
            if (this.containerItemStacks[index].stackSize <= takeAmount) {
                ItemStack itemstack = this.containerItemStacks[index];
                this.containerItemStacks[index] = null;
                if (this.worldObj != null && index == 0) {
                    this.worldObj.markBlockNeedsUpdate(this.x, this.y, this.z);
                }

                return itemstack;
            } else {
                ItemStack itemstack1 = this.containerItemStacks[index].splitStack(takeAmount);
                if (this.containerItemStacks[index].stackSize <= 0) {
                    this.containerItemStacks[index] = null;
                    if (this.worldObj != null && index == 0) {
                        this.worldObj.markBlockNeedsUpdate(this.x, this.y, this.z);
                    }
                }

                return itemstack1;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setItem(int index, @Nullable ItemStack itemstack) {
        this.containerItemStacks[index] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getMaxStackSize()) {
            itemstack.stackSize = this.getMaxStackSize();
        }

        if (this.worldObj != null && index == 0) {
            this.worldObj.markBlockNeedsUpdate(this.x, this.y, this.z);
        }

    }

    @Override
    public void processItem() {
        if(!this.canProcess()){
            return;
        }
        Class<? extends Entity> entityClazz = AetherRecipes.INCUBATOR.findOutput(containerItemStacks[0]);
        if(entityClazz == null) return;
        Entity entity =  EntityDispatcher.createEntityInWorld(entityClazz, worldObj);
        entity.moveTo(this.x, this.y + 2, this.z, 0.0F, 0.0F);
        worldObj.entityJoinedWorld(entity);
        containerItemStacks[0].stackSize--;
        if (containerItemStacks[0].stackSize <= 0) containerItemStacks[0] = null;
    }

    @Override
    public boolean canProcess() {
        if (this.containerItemStacks[0] == null) {
            return false;
        }
        return AetherRecipes.INCUBATOR.findOutput(this.containerItemStacks[0]) != null;
    }



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
    public int getEnergyTimeFromItem(ItemStack itemStack) {
        // just in case where will be more options later
        return itemStack == null ? 0 : LookupFuelIncubator.instance.getFuelYield(itemStack.getItem().id);
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
