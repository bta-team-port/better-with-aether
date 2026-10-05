package teamport.aether.block.entity;


import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.PacketTileEntityData;
import org.jspecify.annotations.NonNull;
import teamport.aether.AetherRecipes;
import teamport.aether.block.AetherBlocks;
import teamport.aether.block.machine.BlockLogicFreezer;
import teamport.aether.item.AetherItems;
import teamport.aether.lookup.LookupFuelFreezer;

public class TileEntityFreezer extends AetherTileEntityMachine {

    @Override
    public @NonNull String getNameTranslationKey() {
        return "container.freezer.name";
    }

    @Override
    public void tick() {
        boolean isEnergyTimeHigherThan0 = this.getCurrentEnergyTime() > 0;
        boolean updateMachine = false;
        if (this.getCurrentEnergyTime() > 0) {
            this.setCurrentEnergyTime(this.getCurrentEnergyTime() - 1);
        }
        if (canProcess()) {
            this.setMaxProcessTime(AetherRecipes.FREEZER.findRecipe(containerItemStacks[0]).getData());
        }

        if (isUpdateMachine(updateMachine, isEnergyTimeHigherThan0)) {
            this.setChanged();
        }
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
                        consumeItemOrBucketLevel(1);
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
            || this.worldObj.getBlockType(this.tilePos) == AetherBlocks.FREEZER_IDLE)
            && this.getCurrentEnergyTime() == 0 && this.containerItemStacks[0] == null
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
        if (processedItem != null && processedItem.isItemStackDamageable()) {
            processedItem.setCustomName(containerItemStacks[0].getCustomName());
            processedItem.setCustomColor(containerItemStacks[0].getCustomColor());
        }

        boolean wasEmpty = this.containerItemStacks[2] == null;
        if (this.containerItemStacks[2] == null && processedItem != null) {
            if (
                containerItemStacks[0] != null
                    && containerItemStacks[0].isItemStackDamageable()
                    && processedItem.isItemStackDamageable()
            ) {
                processedItem.setMetadata(containerItemStacks[0].getMetadata());
            }
            this.containerItemStacks[2] = processedItem.copy();
        } else if (this.containerItemStacks[2] != null && processedItem != null && this.containerItemStacks[2].itemID == processedItem.itemID) {
            ItemStack resultItem = this.containerItemStacks[2];
            resultItem.stackSize += processedItem.stackSize;
        }

        consumeItemOrBucketLevel(0);

        if (this.worldObj != null && wasEmpty && this.containerItemStacks[2] != null) {
            this.worldObj.markBlockNeedsUpdate(this.tilePos);
        }
    }

    private void consumeItemOrBucketLevel(int slotIndex) {
        ItemStack stack = this.containerItemStacks[slotIndex];
        if (stack == null) {
            return;
        }

        if (stack.getItem().equals(AetherItems.BUCKET_SKYROOT_WATER)) {
            this.containerItemStacks[slotIndex] = new ItemStack(AetherItems.BUCKET_SKYROOT);
        } else if (stack.getItem() instanceof ItemBucket && !ItemBucket.STATE_EMPTY.equals(ItemBucket.getState(stack))) {
            ItemBucket.setCharges(stack, ItemBucket.getCharges(stack) - 1);
            if (ItemBucket.getCharges(stack) <= 0) {
                ItemBucket.setState(stack, ItemBucket.STATE_EMPTY);
            }
        } else {
            --stack.stackSize;
            if (stack.stackSize <= 0) {
                this.containerItemStacks[slotIndex] = null;
            }
        }
    }

    @Override
    public void updateContainer(boolean forceLit) {
        if (this.worldObj != null) {
            BlockLogicFreezer.updateFurnaceBlockState(this.worldObj, this.tilePos, forceLit || this.getCurrentEnergyTime() > 0);
            return;
        }
        if (this.carriedBlock != null) {
            this.carriedBlock.blockId = forceLit || this.getCurrentEnergyTime() > 0 ? AetherBlocks.FREEZER_ACTIVE.id() : AetherBlocks.FREEZER_IDLE.id();
        }
    }

    @Override
    public int getEnergyTimeFromItem(ItemStack itemStack) {
        return itemStack == null ? 0 : LookupFuelFreezer.INSTANCE.getFuelYield(itemStack.getItem().id);
    }

    @Override
    public Packet getDescriptionPacket() {
        return this.containerItemStacks[2] != null ? new PacketTileEntityData(this) : null;
    }

}
