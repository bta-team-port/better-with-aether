package bta.aether.tile;

import bta.aether.Aether;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;

import java.security.MessageDigest;

public class TileEntityChestLocked extends TileEntityChest {
    private boolean isLocked;
    private String password = "BLANK PASSWORD";

    public void setLocked(boolean isLocked){
        this.isLocked = isLocked;
    }

    public boolean getLocked() {
        return isLocked;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public String hashString(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            return new String(messageDigest.digest());
        }
        catch (Exception exception) {
            Aether.LOGGER.error("Failed to perform hash function!");
            Aether.LOGGER.error(exception.toString());

            this.setLocked(false);
            return "HASH FAILURE";
        }
    }

    public void setPasswordHashed(String password) {
        this.setPassword(this.hashString(password));
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
        return I18n.getInstance().translateKey("tile.aether.chest.treasure.name");
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
