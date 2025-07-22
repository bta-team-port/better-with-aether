package teamport.aether.tile;


import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import teamport.aether.AetherRecipes;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.BlockLogicFreezer;
import teamport.aether.items.AetherItems;
import teamport.aether.lookup.LookupFuelFreezer;
import teamport.aether.lookup.Repairable;

import java.util.HashMap;

// TODO implement the class, this should be mostly a port from 7.2
public class TileEntityFreezer extends AetherTileEntityMachine {
    public static final HashMap<Integer, Integer> buckets = new HashMap<>();

    static {
        buckets.put(Items.BUCKET_WATER.id, Items.BUCKET.id);
        buckets.put(Items.BUCKET_LAVA.id, Items.BUCKET.id);
        buckets.put(AetherItems.BUCKET_SKYROOT_WATER.id, AetherItems.BUCKET_SKYROOT.id);
    }

    /// canSmelt                -> canProcess
    /// smeltItem               -> processItem
    /// updateFurnace           -> updateContainer
    /// getBurnTimeFromItem     -> getEnergyTimeFromItem
    /// getCookProgressScaled   -> getProgressScale
    /// maxEnergyTime           -> maxBurnTime
    /// currentEnergyTime       -> currentBurnTime
    /// maxProcessTime          -> maxCookTime
    /// currentProcessTime      -> currentCookTime

    @Override
    public String getNameTranslationKey() {
        return "aether.container.freezer.name";
    }

    @Override
    public void tick() {
        boolean isEnergyTimeHigherThan0 = this.currentEnergyTime > 0;
        boolean updateMachine = false;
        if (this.currentEnergyTime > 0) {
            --this.currentEnergyTime;
        }
        if(canProcess()){
            this.maxProcessTime = AetherRecipes.FREEZER.findRecipe(containerItemStacks[0]).getData();
        }

        if (isUpdateMachine(updateMachine, isEnergyTimeHigherThan0)) {
            this.setChanged();
        }
    }

    public boolean isUpdateMachine(boolean updateMachine, boolean isEnergyTimeHigherThan0) {
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
        }
        return updateMachine;
    }

    public boolean eternallyLit(boolean updateMachine) {
        if ((this.worldObj == null
                || this.worldObj.getBlockId(this.x, this.y, this.z) == AetherBlocks.FREEZER_IDLE.id())
                && this.currentEnergyTime == 0 && this.containerItemStacks[0] == null
                && this.containerItemStacks[1] != null
                && this.containerItemStacks[1].itemID == AetherItems.ARMOR_TALISMAN_ICE.id
        ) {
            this.updateContainer(true);
            return true;
        }
        return updateMachine;
    }

    @Override
    public boolean canProcess() {
        if (this.containerItemStacks[0] == null) {
            return false;
        }
        ItemStack toProcess = containerItemStacks[0];
        ItemStack resultStack = AetherRecipes.FREEZER.findOutput(toProcess);
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
        ItemStack processedItem = AetherRecipes.FREEZER.findOutput(containerItemStacks[0]);

        boolean wasEmpty = this.containerItemStacks[2] == null;
        if (this.containerItemStacks[2] == null && processedItem != null) {
            if(Repairable.instance.isRepairable(containerItemStacks[0]) && Repairable.instance.isRepairable(processedItem)){
                processedItem.setMetadata(containerItemStacks[0].getMetadata());
            }
            this.containerItemStacks[2] = processedItem.copy();
        } else if (this.containerItemStacks[2] != null && processedItem != null && this.containerItemStacks[2].itemID == processedItem.itemID) {
            ItemStack resultItem = this.containerItemStacks[2];
            resultItem.stackSize += processedItem.stackSize;
        }


        if (isBucket(containerItemStacks[0])) {
            this.containerItemStacks[0] = this.getBucket(containerItemStacks[0]);
        } else {
            --this.containerItemStacks[0].stackSize;
            if (this.containerItemStacks[0].stackSize <= 0) {
                this.containerItemStacks[0] = null;
            }
        }

        if (this.worldObj != null && wasEmpty && this.containerItemStacks[2] != null) {
            this.worldObj.markBlockNeedsUpdate(this.x, this.y, this.z);
        }
    }

    public boolean isBucket(ItemStack itemStack) {
        for (Integer id : buckets.keySet()) {
            if (itemStack.getItem().id == id) {
                return true;
            }
        }
        return false;
    }

    public ItemStack getBucket(ItemStack stack) {
        int id = buckets.get(stack.getItem().id);
        Item item = Item.getItem(id);
        assert (item != null); // something went wrong if that's the case
        return new ItemStack(item, 1);
    }


    @Override
    public void updateContainer(boolean forceLit) {
        if (this.worldObj != null) {
            BlockLogicFreezer.updateFurnaceBlockState(forceLit | this.currentEnergyTime > 0, this.worldObj, this.x, this.y, this.z);
            return;
        }
        if (this.carriedBlock != null) {
            this.carriedBlock.blockId = forceLit | this.currentEnergyTime > 0 ? AetherBlocks.FREEZER_ACTIVE.id() : AetherBlocks.FREEZER_IDLE.id();
        }
    }

    @Override
    public int getEnergyTimeFromItem(ItemStack itemStack) {
        return itemStack == null ? 0 : LookupFuelFreezer.instance.getFuelYield(itemStack.getItem().id);
    }

    @Override
    public void dropContents(World world, int x, int y, int z) {
        super.dropContents(world, x, y, z);
        if (!BlockLogicFreezer.keepFreezerInventory) {
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
                        EntityItem entityItem = new EntityItem
                                (world, (float) x + f, (float) y + f1, (float) z + f2,
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
