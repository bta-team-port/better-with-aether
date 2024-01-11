package bta.aether.entity.tileEntity;

import bta.aether.Aether;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.item.ItemStack;

public class TileEntityChestLocked extends TileEntityChest {
    private boolean isLocked;
    private String password;

    public void setLocked(boolean isLocked){
        this.isLocked = isLocked;
    }

    public boolean getlocked() {
        return isLocked;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        if (isLocked) {
            Aether.LOGGER.warn("You cannot look inside a locked chest!");
            return null;
        }
        return super.getStackInSlot(i);
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (isLocked) {
            Aether.LOGGER.warn("You cannot Interact with a locked chest!");
            return null;
        }
        return super.decrStackSize(i, j);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        if (isLocked) {
            Aether.LOGGER.warn("You cannot place items inside a locked chest!");
            this.worldObj.dropItem(this.x, this.y + 1, this.z,itemstack);
            return;
        }
        super.setInventorySlotContents(i, itemstack);
    }

    @Override
    public String getInvName() {
        return "Dungeon Chest";
    }

    @Override
    public void readFromNBT(CompoundTag nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        isLocked = nbttagcompound.getBoolean("isLocked");
        password = nbttagcompound.getString("password");
    }

    @Override
    public void writeToNBT(CompoundTag nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.putBoolean("isLocked", isLocked);
        nbttagcompound.putString("password", password);
    }
}
