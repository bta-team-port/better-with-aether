package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.items.AetherItems;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.items.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(value = BlockLogic.class, remap = false)
public abstract class BlockLogicHarvestBlockGoldPendant {
    @Shadow @Final @NotNull public Block<?> block;

    @WrapOperation(method = "harvestBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/Item;isSilkTouch()Z"))
    public boolean golPendantEquipped(Item instance, Operation<Boolean> original, @Local(argsOnly = true) Player player){
        boolean silkTouch = original.call(instance);
        if(silkTouch) return silkTouch;
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        return (trinketOne != null && trinketOne.getItem().id == AetherItems.ARMOR_TALISMAN_GOLD.id)
                || (trinketTwo != null && trinketTwo.getItem().id == AetherItems.ARMOR_TALISMAN_GOLD.id);
    }
}
