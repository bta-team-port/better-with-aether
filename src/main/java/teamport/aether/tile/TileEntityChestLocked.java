package teamport.aether.tile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.entity.TileEntityChest;

public class TileEntityChestLocked extends TileEntityChest {
    private boolean isLocked;

    public TileEntityChestLocked(){
        super();
        this.isLocked = true;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked){
        this.isLocked = isLocked;
    }

//    @Override
//    public void setItem(int i, ItemStack itemstack) {
//        if (isLocked) {
//            AetherMod.LOGGER.warn("You cannot place items inside a locked chest!");
//            this.worldObj.dropItem(this.x, this.y + 1, this.z,itemstack);
//            return;
//        }
//        super.setItem(i, itemstack);
//    }

//    @Override
//    public ItemStack getItem(int i) {
//        if (isLocked) {
//            AetherMod.LOGGER.warn("You cannot look inside a locked chest!");
//            return null;
//        }
//        return super.getItem(i);
//    }

//    @Override
//    public ItemStack removeItem(int i, int j) {
//        if (isLocked) {
//            AetherMod.LOGGER.warn("You cannot Interact with a locked chest!");
//            return null;
//        }
//        return super.removeItem(i, j);
//    }

    @Override
    public void readFromNBT(CompoundTag nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        isLocked = nbttagcompound.getBoolean("isLocked");
    }

    @Override
    public void writeToNBT(CompoundTag nbttagcompound){
        super.writeToNBT(nbttagcompound);
        nbttagcompound.putBoolean("isLocked", isLocked);
    }

    @Override
    public String getNameTranslationKey() {
        return "aether.container.locked.chest.name";
    }
}
