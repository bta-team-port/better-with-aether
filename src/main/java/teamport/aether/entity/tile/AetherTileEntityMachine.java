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
    public ItemStack[] containerItemStacks;
    public int maxEnergyTime = 0;       // maxBurnTime
    public int currentEnergyTime = 0;   // currentBurnTime
    public int maxProcessTime = 200;    // maxCookTime
    public int currentProcessTime = 0;  // currentCookTime

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
        ItemStack itemstack1 = this.containerItemStacks[index].splitStack(takeAmount);
        if (this.containerItemStacks[index].stackSize <= 0) {
            this.containerItemStacks[index] = null;
            if (this.worldObj != null && index == 2) {
                this.worldObj.markBlockNeedsUpdate(this.x, this.y, this.z);
            }
        }
        return itemstack1;
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

    //override

    public String getNameTranslationKey() {
        return "";
    }

    public void readFromNBT(CompoundTag nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        ListTag nbttaglist = nbttagcompound.getList("Items");
        this.containerItemStacks = new ItemStack[this.getContainerSize()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            CompoundTag nbttagcompound1 = (CompoundTag) nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if (byte0 >= 0 && byte0 < this.containerItemStacks.length) {
                this.containerItemStacks[byte0] = ItemStack.readItemStackFromNbt(nbttagcompound1);
            }
        }
        this.currentEnergyTime = nbttagcompound.getShort("EnergyTime");
        this.currentProcessTime = nbttagcompound.getShort("ProcessTime");
        this.maxEnergyTime = nbttagcompound.getShort("MaxEnegryTime");
    }

    public void writeToNBT(CompoundTag nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.putShort("EnergyTime", (short) this.currentEnergyTime);
        nbttagcompound.putShort("ProcessTime", (short) this.currentProcessTime);
        nbttagcompound.putShort("MaxEnegryTime", (short) this.maxEnergyTime);
        ListTag nbttaglist = new ListTag();

        for (int i = 0; i < this.containerItemStacks.length; ++i) {
            if (this.containerItemStacks[i] != null) {
                CompoundTag nbttagcompound1 = new CompoundTag();
                nbttagcompound1.putByte("Slot", (byte) i);
                this.containerItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.addTag(nbttagcompound1);
            }
        }
        nbttagcompound.put("Items", nbttaglist);
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

    //override

    public void tick() {
    }
    //override

    public boolean canProcess() {
        return false;
    }
    //override

    public void processItem() {
    }
    //override

    public void updateContainer(boolean forceLit) {
    }
    //override

    public int getEnergyTimeFromItem(ItemStack itemStack) {
        return 0;
    }

    public boolean stillValid(Player entityplayer) {
        if (this.worldObj != null && this.worldObj.getTileEntity(this.x, this.y, this.z) == this) {
            return entityplayer.distanceToSqr((double) this.x + (double) 0.5F, (double) this.y + (double) 0.5F, (double) this.z + (double) 0.5F) <= (double) 64.0F;
        } else {
            return false;
        }
    }


    public void sortContainer() {
    }

    //override

    public void dropContents(World world, int x, int y, int z) {
    }

    public Packet getDescriptionPacket() {
        return null;
    }
    //override

    @Override
    public void heldTick(World world, Entity holder) {
        this.tick();
    }

    public boolean tryPlace(World world, Entity holder, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        boolean success = super.tryPlace(world, holder, blockX, blockY, blockZ, side, xPlaced, yPlaced);
        if (success) {
            this.updateContainer(false);
        }

        return success;
    }

    public boolean canBeCarried(World world, Entity potentialHolder) {
        return true;
    }

    public CarriedBlock getCarriedEntry(World world, Entity holder, Block<?> currentBlock, int currentMeta) {
        return super.getCarriedEntry(world, holder, currentBlock, currentMeta & -8 | 2);
    }
}
