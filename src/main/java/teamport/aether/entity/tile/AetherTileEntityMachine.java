package teamport.aether.entity.tile;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.motion.CarriedBlock;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;

import java.util.Random;

public class AetherTileEntityMachine extends TileEntity implements Container {
    public final Random random = new Random();

    protected ItemStack[] containerItemStacks;
    private int maxEnergyTime = 0;       // maxBurnTime
    private int currentEnergyTime = 0;   // currentBurnTime
    private int maxProcessTime = 200;    // maxCookTime
    private int currentProcessTime = 0;  // currentCookTime

    public AetherTileEntityMachine() {
        this.containerItemStacks = new ItemStack[3];
    }

    public int getContainerSize() {
        return containerItemStacks.length;
    }

    public @Nullable ItemStack getItem(int index) {
        return this.containerItemStacks[index];
    }

    public @Nullable ItemStack removeItem(int index, int takeAmount) {
        if (this.containerItemStacks[index] == null) {
            return null;
        }
        if (this.containerItemStacks[index].stackSize <= takeAmount) {
            ItemStack itemstack = this.containerItemStacks[index];
            this.containerItemStacks[index] = null;
            if (this.worldObj != null && index == 2) {
                this.worldObj.markBlockNeedsUpdate(this.x, this.y, this.z);
            }
            return itemstack;
        }
        ItemStack itemStack = this.containerItemStacks[index].splitStack(takeAmount);
        if (this.containerItemStacks[index].stackSize <= 0) {
            this.containerItemStacks[index] = null;
            if (this.worldObj != null && index == 2) {
                this.worldObj.markBlockNeedsUpdate(this.x, this.y, this.z);
            }
        }
        return itemStack;
    }

    public void setItem(int index, @Nullable ItemStack itemStack) {
        this.containerItemStacks[index] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getMaxStackSize()) {
            itemStack.stackSize = this.getMaxStackSize();
        }

        if (this.worldObj != null && index == 2 && itemStack == null) {
            this.worldObj.markBlockNeedsUpdate(this.x, this.y, this.z);
        }
    }

    public String getNameTranslationKey() { return ""; }

    @Override
    public void readFromNBT(CompoundTag compoundTag) {
        super.readFromNBT(compoundTag);
        ListTag listTag = compoundTag.getList("Items");
        this.containerItemStacks = new ItemStack[this.getContainerSize()];

        for (int i = 0; i < listTag.tagCount(); ++i) {
            CompoundTag compoundTagTwo = (CompoundTag) listTag.tagAt(i);
            byte byte0 = compoundTagTwo.getByte("Slot");
            if (byte0 >= 0 && byte0 < this.containerItemStacks.length) {
                this.containerItemStacks[byte0] = ItemStack.readItemStackFromNbt(compoundTagTwo);
            }
        }
        this.currentEnergyTime = compoundTag.getShort("EnergyTime");
        this.currentProcessTime = compoundTag.getShort("ProcessTime");
        this.maxEnergyTime = compoundTag.getShort("MaxEnegryTime");
    }

    @Override
    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        compoundTag.putShort("EnergyTime", (short) this.currentEnergyTime);
        compoundTag.putShort("ProcessTime", (short) this.currentProcessTime);
        compoundTag.putShort("MaxEnegryTime", (short) this.maxEnergyTime);
        ListTag listTag = new ListTag();

        for (int i = 0; i < this.containerItemStacks.length; ++i) {
            if (this.containerItemStacks[i] != null) {
                CompoundTag compoundTagTwo = new CompoundTag();
                compoundTagTwo.putByte("Slot", (byte) i);
                this.containerItemStacks[i].writeToNBT(compoundTagTwo);
                listTag.addTag(compoundTagTwo);
            }
        }
        compoundTag.put("Items", listTag);
    }

    public int getMaxStackSize() {
        return 64;
    }

    public int getProcessProgressScaled(int i) {
        return this.maxProcessTime == 0 ? 0 : this.currentProcessTime * i / this.maxProcessTime;
    }

    public int getEnergyTimeRemainingScaled(int i) {
        return this.maxEnergyTime == 0 ? 0 : this.currentEnergyTime * i / this.maxEnergyTime;
    }

    public boolean isProcessing() {
        return this.currentEnergyTime > 0;
    }

    @Override
    public void tick() {}

    public boolean canProcess() {
        return false;
    }

    public void processItem() {}

    public void updateContainer(boolean forceLit) {}

    @SuppressWarnings("java:S1172")
    public int getEnergyTimeFromItem(ItemStack itemStack) {
        return 0;
    }

    public boolean stillValid(Player entityplayer) {
        if (this.worldObj != null && this.worldObj.getTileEntity(this.x, this.y, this.z) == this) {
            return entityplayer.distanceToSqr(this.x + 0.5, this.y + 0.5, this.z + 0.5) <= 64.0;
        } else {
            return false;
        }
    }

    @Override
    public void sortContainer() {}

    @Override
    public void dropContents(World world, int x, int y, int z) {
    }

    @Override
    public Packet getDescriptionPacket() {
        return null;
    }

    @Override
    public void heldTick(World world, Entity holder) {
        this.tick();
    }

    @Override
    public boolean tryPlace(World world, Entity holder, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        boolean success = super.tryPlace(world, holder, blockX, blockY, blockZ, side, xPlaced, yPlaced);
        if (success) {
            this.updateContainer(false);
        }

        return success;
    }

    @Override
    public boolean canBeCarried(World world, Entity potentialHolder) {
        return true;
    }

    @Override
    public CarriedBlock getCarriedEntry(World world, Entity holder, Block<?> currentBlock, int currentMeta) {
        return super.getCarriedEntry(world, holder, currentBlock, currentMeta & -8 | 2);
    }
    public int getMaxEnergyTime() {
        return maxEnergyTime;
    }
    public void setMaxEnergyTime(int maxEnergyTime) {
        this.maxEnergyTime = maxEnergyTime;
    }
    public int getCurrentEnergyTime() {
        return currentEnergyTime;
    }
    public void setCurrentEnergyTime(int currentEnergyTime) {
        this.currentEnergyTime = currentEnergyTime;
    }
    public int getMaxProcessTime() {
        return maxProcessTime;
    }
    public void setMaxProcessTime(int maxProcessTime) {
        this.maxProcessTime = maxProcessTime;
    }
    public int getCurrentProcessTime() {
        return currentProcessTime;
    }
    public void setCurrentProcessTime(int currentProcessTime) {
        this.currentProcessTime = currentProcessTime;
    }
}
