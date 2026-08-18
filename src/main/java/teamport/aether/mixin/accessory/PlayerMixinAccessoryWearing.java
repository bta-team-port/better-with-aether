package teamport.aether.mixin.accessory;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.item.accessory.HumanAccessoryShape;
import teamport.aether.item.accessory.IAccessoryWearing;

@Mixin(Player.class)
public abstract class PlayerMixinAccessoryWearing implements IAccessoryWearing<HumanAccessoryShape> {

    @Override
    public ItemStack getAccessoryInSlot(@NonNull HumanAccessoryShape slot) {
        Player player = (Player) (Object) this;
        ItemStack[] accessories = ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory();
        if (accessories != null && slot.getSlotIndex() < accessories.length) {
            return accessories[slot.getSlotIndex()];
        }
        return null;
    }

    @Override
    public void setAccessoryInSlot(@NonNull HumanAccessoryShape slot, ItemStack stack) {
        Player player = (Player) (Object) this;
        ItemStack[] accessories = ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory();
        if (accessories != null && slot.getSlotIndex() < accessories.length) {
            accessories[slot.getSlotIndex()] = stack;
        }
    }

    @Override
    public int getNumAccessorySlots() {
        return HumanAccessoryShape.values().length;
    }

    @Override
    public HumanAccessoryShape getAccessorySlotByIndex(int index) {
        HumanAccessoryShape[] values = HumanAccessoryShape.values();
        if (index >= 0 && index < values.length) {
            return values[index];
        }
        return null;
    }
}
