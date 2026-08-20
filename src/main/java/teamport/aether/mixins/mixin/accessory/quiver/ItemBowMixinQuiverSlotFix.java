package teamport.aether.mixins.mixin.accessory.quiver;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.ItemBow;
import net.minecraft.core.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.entity.player.PlayerUtil;

@Mixin(ItemBow.class)
public abstract class ItemBowMixinQuiverSlotFix {
    @WrapOperation(method = "onUse(Lnet/minecraft/core/item/ItemStack;Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/Player;)Lnet/minecraft/core/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;getItemInArmorSlot(Lnet/minecraft/core/enums/HumanArmorShape;)Lnet/minecraft/core/item/ItemStack;"))
    private ItemStack checkAdditionalSlots(Player instance, HumanArmorShape slot, @NonNull Operation<ItemStack> original) {
        ItemStack bodyItem = original.call(instance, slot);
        ItemStack capeItem = ((IContainerInventoryAether) instance.inventory).aether$getAccessoryInventory()[1];
        if (PlayerUtil.isUsableQuiver(bodyItem)) return bodyItem;
        return PlayerUtil.isUsableQuiver(capeItem) ? capeItem : bodyItem;
    }
}
