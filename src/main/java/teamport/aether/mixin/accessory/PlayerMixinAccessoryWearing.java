package teamport.aether.mixin.accessory;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.effect.AetherEffects;
import teamport.aether.item.accessory.HumanAccessoryShape;
import teamport.aether.item.accessory.IAccessoryWearing;

@Mixin(Player.class)
public abstract class PlayerMixinAccessoryWearing implements IAccessoryWearing<HumanAccessoryShape> {

    @Override
    public ItemStack better_with_aether$getAccessoryInSlot(int slotIndex) {
        Player player = (Player) (Object) this;
        ItemStack[] accessories = ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory();
        if (accessories != null && slotIndex >= 0 && slotIndex < accessories.length) {
            return accessories[slotIndex];
        }
        return null;
    }

    @Override
    public void better_with_aether$setAccessoryInSlot(int slotIndex, ItemStack stack) {
        Player player = (Player) (Object) this;
        ItemStack[] accessories = ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory();
        if (accessories != null && slotIndex >= 0 && slotIndex < accessories.length) {
            accessories[slotIndex] = stack;
        }
    }

    @Override
    public int better_with_aether$getNumAccessorySlots() {
        return 4;
    }

    @Override
    public HumanAccessoryShape better_with_aether$getSlotShape(int slotIndex) {
        return switch (slotIndex) {
            case 0 -> HumanAccessoryShape.GLOVES;
            case 1 -> HumanAccessoryShape.CAPE;
            default -> HumanAccessoryShape.TRINKET;
        };
    }


    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(World world, CallbackInfo ci) {
        ((IHasEffects<Player>) this)
            .getContainer()
            .additionalModifierSuppliers
            .add(AetherEffects.getPlayerAccessoriesModifiers());
    }

}
