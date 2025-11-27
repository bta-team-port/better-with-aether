package teamport.aether.gui;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import org.jspecify.annotations.Nullable;

public class ContainerLorebook implements Container {
    private final ItemStack[] items = new ItemStack[1];
    private final MenuAbstract menu;

    public ContainerLorebook(MenuAbstract menu) {
        this.menu = menu;
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public @Nullable ItemStack getItem(int index) {
        return index == 0 ? items[0] : null;
    }

    @Override
    public @Nullable ItemStack removeItem(int index, int count) {
        if (index != 0 || items[0] == null) return null;
        ItemStack stack = items[0];
        if (stack.stackSize <= count) {
            items[0] = null;
            menu.slotsChanged(this);
            return stack;
        }
        ItemStack split = stack.splitStack(count);
        if (items[0].stackSize <= 0) items[0] = null;
        menu.slotsChanged(this);
        return split;
    }

    @Override
    public void setItem(int index, @Nullable ItemStack stack) {
        if (index == 0) {
            items[0] = stack;
            menu.slotsChanged(this);
        }
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public void setChanged() {
        menu.slotsChanged(this);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public String getNameTranslationKey() {
        return "container.aether.lorebook";
    }

    @Override
    public void sortContainer() {
    }
}
