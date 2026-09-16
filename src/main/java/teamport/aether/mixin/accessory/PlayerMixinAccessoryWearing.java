package teamport.aether.mixin.accessory;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

    @Unique
    private final ItemStack[] oldAccessoryInventory = new ItemStack[4];

    @Inject(method = "tick", at = @At("HEAD"))
    private void checkAccessoryEquipSound(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        boolean playEquipSound = false;

        for (int i = 0; i < this.oldAccessoryInventory.length; i++) {
            ItemStack oldStack = this.oldAccessoryInventory[i];
            int slotIndex = player.inventory.getContainerSize() - 4 + i;
            ItemStack currentStack = player.inventory.getItem(slotIndex);

            if (oldStack != currentStack && (oldStack == null || currentStack == null || oldStack.itemID != currentStack.itemID)) {
                playEquipSound = true;
            }

            this.oldAccessoryInventory[i] = currentStack != null ? currentStack.copy() : null;
        }

        if (playEquipSound) {
            player.world.playSoundAtEntity(player, player, "random.equip", 2.0F, 1.0F);
        }
    }
}
