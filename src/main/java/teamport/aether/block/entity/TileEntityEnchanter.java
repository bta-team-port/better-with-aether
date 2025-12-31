package teamport.aether.block.entity;


import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.PacketTileEntityData;
import net.minecraft.core.world.World;
import teamport.aether.AetherRecipes;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.machine.BlockLogicEnchanter;
import teamport.aether.lookup.LookupFuelEnchanter;


public class TileEntityEnchanter extends AetherTileEntityMachine {

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
        return "container.enchanter.name";
    }

    @Override
    public void tick() {
        boolean isEnergyTimeHigherThan0 = this.getCurrentEnergyTime() > 0;
        boolean updateMachine = false;
        if (this.getCurrentEnergyTime() > 0) {
            this.setCurrentEnergyTime(this.getCurrentEnergyTime() - 1);
        }
        if (canProcess()) {
            setMaxProcessTime();
        }

        if (isUpdateMachine(updateMachine, isEnergyTimeHigherThan0)) {
            this.setChanged();
        }

    }

    public void setMaxProcessTime() {
        float percent = 1.0F;
        int maxProcessTimeRaw = AetherRecipes.ENCHANTER.findRecipe(containerItemStacks[0]).getData();
        if (
            containerItemStacks[0] != null
                && containerItemStacks[0].isItemStackDamageable()
                && containerItemStacks[0].getMetadata() != 0
        ) {
            int currentDurability = containerItemStacks[0].getMetadata();
            int maxDurability = containerItemStacks[0].getItem().getMaxDamage();
            percent = (float) currentDurability / maxDurability;
        }
        this.setMaxProcessTime((int) Math.floor(maxProcessTimeRaw * percent));
    }

    public boolean isUpdateMachine(boolean updateMachine, boolean isEnergyTimeHigherThan0) {
        if (this.worldObj == null || !this.worldObj.isClientSide) {
            updateMachine = eternallyLit(updateMachine);

            if (this.getCurrentEnergyTime() == 0 && this.containerItemStacks[1] != null && this.canProcess()) {
                this.setCurrentEnergyTime(this.getEnergyTimeFromItem(this.containerItemStacks[1]));
                this.setMaxEnergyTime(this.getCurrentEnergyTime());
                if (this.getCurrentEnergyTime() > 0) {
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
                this.setCurrentProcessTime(this.getCurrentProcessTime() + 1);
                if (this.getCurrentProcessTime() >= this.getMaxProcessTime()) {
                    this.setCurrentProcessTime(0);
                    this.processItem();
                    updateMachine = true;
                }
            } else {
                this.setCurrentProcessTime(0);
            }

            if (isEnergyTimeHigherThan0 != this.getCurrentEnergyTime() > 0) {
                this.updateContainer(false);
                updateMachine = true;
            }
        }
        return updateMachine;
    }

    public boolean eternallyLit(boolean updateMachine) {
        if ((this.worldObj == null
            || this.worldObj.getBlockId(this.x, this.y, this.z) == AetherBlocks.ENCHANTER_IDLE.id())
            && this.getCurrentEnergyTime() == 0 && this.containerItemStacks[0] == null
            && this.containerItemStacks[1] != null
            && this.containerItemStacks[1].itemID == AetherBlocks.BLOCK_ZANITE.id()
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
    public boolean canProcess() {
        if (this.containerItemStacks[0] == null) {
            return false;
        }
        ItemStack toProcess = containerItemStacks[0];
        ItemStack resultStack = AetherRecipes.ENCHANTER.findOutput(toProcess);
        if (resultStack == null) {
            return false;
        }
        if (toProcess.isItemStackDamageable()
//                Repairable.instance.isRepairable(toProcess)
            && toProcess.getMetadata() == 0
        ) {
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
        if (processedItem != null && processedItem.isItemStackDamageable()) {
            processedItem.setCustomName(containerItemStacks[0].getCustomName());
            processedItem.setCustomColor(containerItemStacks[0].getCustomColor());

        }

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
    public void updateContainer(boolean forceLit) {
        if (this.worldObj != null) {
            BlockLogicEnchanter.updateFurnaceBlockState(forceLit || this.getCurrentEnergyTime() > 0, this.worldObj, this.x, this.y, this.z);
            return;
        }
        if (this.carriedBlock != null) {
            this.carriedBlock.blockId = forceLit || this.getCurrentEnergyTime() > 0 ? AetherBlocks.ENCHANTER_ACTIVE.id() : AetherBlocks.ENCHANTER_IDLE.id();
        }
    }

    @Override
    public int getEnergyTimeFromItem(ItemStack itemStack) {
        return itemStack == null ? 0 : LookupFuelEnchanter.INSTANCE.getFuelYield(itemStack.getItem().id);
    }

    @Override
    public void dropContents(World world, int x, int y, int z) {
        super.dropContents(world, x, y, z);
        if (!BlockLogicEnchanter.isKeepEnchanterInventory()) {
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
                            world, x + f, y + f1, z + f2,
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

    @Override
    public Packet getDescriptionPacket() {
        return this.containerItemStacks[2] != null ? new PacketTileEntityData(this) : null;
    }

}
