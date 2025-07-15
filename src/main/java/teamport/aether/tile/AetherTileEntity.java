package teamport.aether.tile;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import org.jetbrains.annotations.Nullable;

// TODO all function that incubator, freezer and enchanter share need to be implemented
public class AetherTileEntity extends TileEntity implements Container {
    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public @Nullable ItemStack getItem(int i) {
        return null;
    }

    @Override
    public @Nullable ItemStack removeItem(int i, int j) {
        return null;
    }

    @Override
    public void setItem(int i, @Nullable ItemStack itemStack) {

    }

    @Override
    public String getNameTranslationKey() {
        return "";
    }

    @Override
    public int getMaxStackSize() {
        return 0;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    @Override
    public void sortContainer() {

    }

    public boolean isBurning() {
        return false;
    }

    public int getBurnTimeRemainingScaled(int i) {
        return 0;
    }

    public int getCookProgressScaled(int i) {
        return 0;
    }
}
