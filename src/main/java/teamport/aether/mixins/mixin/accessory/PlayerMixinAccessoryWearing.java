package teamport.aether.mixin.accessory;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.item.accessory.HumanAccessoryShape;
import teamport.aether.item.accessory.IAccessoryWearing;

@Mixin(Player.class)
public abstract class PlayerMixinAccessoryWearing implements IAccessoryWearing<HumanAccessoryShape> {

    @Override
    public ItemStack getAccessoryInSlot(int slotIndex) {
        Player player = (Player) (Object) this;
        ItemStack[] accessories = ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory();
        if (accessories != null && slotIndex >= 0 && slotIndex < accessories.length) {
            return accessories[slotIndex];
        }
        return null;
    }

    @Override
    public void setAccessoryInSlot(int slotIndex, ItemStack stack) {
        Player player = (Player) (Object) this;
        ItemStack[] accessories = ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory();
        if (accessories != null && slotIndex >= 0 && slotIndex < accessories.length) {
            accessories[slotIndex] = stack;
        }
    }

    @Override
    public int getNumAccessorySlots() {
        return 4;
    }

    @Override
    public HumanAccessoryShape getSlotShape(int slotIndex) {
        return switch (slotIndex) {
            case 0 -> HumanAccessoryShape.GLOVES;
            case 1 -> HumanAccessoryShape.CAPE;
            default -> HumanAccessoryShape.TRINKET;
        };
    }
}
