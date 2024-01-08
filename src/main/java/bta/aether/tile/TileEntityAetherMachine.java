package bta.aether.tile;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.InventorySorter;

public class TileEntityAetherMachine extends TileEntity implements IInventory {

    public int fuelBurnTicks = 0;
    public int fuelMaxBurnTicks = 0;
    public int progressTicks = 0;
    public int progressMaxTicks = 200;
    public ItemStack[] contents = new ItemStack[3];

    @Override
    public int getSizeInventory() {
        return this.contents.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.contents[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (this.contents[i] != null) {
            ItemStack itemstack1;
            if (this.contents[i].stackSize <= j) {
                itemstack1 = this.contents[i];
                this.contents[i] = null;
                return itemstack1;
            } else {
                itemstack1 = this.contents[i].splitStack(j);
                if (this.contents[i].stackSize <= 0) {
                    this.contents[i] = null;
                }

                return itemstack1;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.contents[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }

    }

    @Override
    public String getInvName() {
        return "Enchanter";
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void writeToNBT(CompoundTag nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.putShort("BurnTime", (short)this.fuelBurnTicks);
        nbttagcompound.putShort("ProcessTime", (short)this.progressTicks);
        nbttagcompound.putShort("MaxBurnTime", (short)this.fuelMaxBurnTicks);
        nbttagcompound.putInt("MaxProcessTime",this.progressMaxTicks);

        ListTag nbttaglist = new ListTag();

        for(int i = 0; i < this.contents.length; ++i) {
            if (this.contents[i] != null) {
                CompoundTag nbttagcompound1 = new CompoundTag();
                nbttagcompound1.putByte("Slot", (byte)i);
                this.contents[i].writeToNBT(nbttagcompound1);
                nbttaglist.addTag(nbttagcompound1);
            }
        }

        nbttagcompound.put("Items", nbttaglist);
    }

    @Override
    public void readFromNBT(CompoundTag nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        ListTag nbttaglist = nbttagcompound.getList("Items");
        this.contents = new ItemStack[this.getSizeInventory()];

        for(int i = 0; i < nbttaglist.tagCount(); ++i) {
            CompoundTag nbttagcompound1 = (CompoundTag)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if (byte0 >= 0 && byte0 < this.contents.length) {
                this.contents[byte0] = ItemStack.readItemStackFromNbt(nbttagcompound1);
            }
        }
        fuelBurnTicks = nbttagcompound.getShort("BurnTime");
        progressTicks = nbttagcompound.getShort("ProcessTime");
        progressMaxTicks = nbttagcompound.getInteger("MaxProcessTime");
        fuelMaxBurnTicks = nbttagcompound.getShort("MaxBurnTime");
    }

    public int getProgressScaled(int paramInt) {
        return this.progressTicks * paramInt / progressMaxTicks;
    }

    public int getBurnTimeRemainingScaled(int paramInt) {
        if(this.fuelMaxBurnTicks == 0) {
            this.fuelMaxBurnTicks = 200;
        }
        return this.fuelBurnTicks * paramInt / this.fuelMaxBurnTicks;
    }



    public boolean isBurning(){
        return fuelBurnTicks > 0;
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        if(worldObj.getBlockTileEntity(x, y, z) != this)
        {
            return false;
        }
        return entityplayer.distanceToSqr((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D) <= 64D;
    }

    @Override
    public void sortInventory() {
        InventorySorter.sortInventory(contents);
    }

}
