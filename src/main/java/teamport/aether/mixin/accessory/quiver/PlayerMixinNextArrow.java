package teamport.aether.mixin.accessory.quiver;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.entity.player.PlayerUtil;

@Mixin(Player.class)
public abstract class PlayerMixinNextArrow {

    @Shadow
    @Final
    @NonNull
    public ContainerInventory inventory;

    @WrapOperation(method = "getNextArrow", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;getItemInArmorSlot(Lnet/minecraft/core/enums/HumanArmorShape;)Lnet/minecraft/core/item/ItemStack;"))
    private ItemStack checkAdditionalSlots(Player instance, HumanArmorShape slot, @NonNull Operation<ItemStack> original) {
        ItemStack bodyItem = original.call(instance, slot);
        ItemStack capeItem = ((IContainerInventoryAether) inventory).aether$getAccessoryInventory()[1];
        if (PlayerUtil.isUsableQuiver(bodyItem)) return bodyItem;
        return PlayerUtil.isUsableQuiver(capeItem) ? capeItem : bodyItem;
    }
}
