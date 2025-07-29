package teamport.aether.mixin.accessory.functional;

import net.minecraft.core.block.BlockLogicFarmland;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.AetherItems;

import static teamport.aether.items.accessory.SlotAccessory.WILDCARD_1_SLOT;
import static teamport.aether.items.accessory.SlotAccessory.WILDCARD_2_SLOT;

@Mixin(value = BlockLogicFarmland.class, remap = false)
public class BlockLogicFarmlandLeatherPendantMixin {
    @Inject(method = "onEntityWalking", at = @At(value = "HEAD"), cancellable = true)
    public void onEntityWalking(World world, int x, int y, int z, Entity entity, CallbackInfo ci) {
        if (((Player) entity).inventory.armorInventory[WILDCARD_1_SLOT] != null && ((Player) entity).inventory.armorInventory[WILDCARD_1_SLOT].getItem().equals(AetherItems.ARMOR_TALISMAN_LEATHER)) {
            ci.cancel();
        }
        if (((Player) entity).inventory.armorInventory[WILDCARD_2_SLOT] != null && ((Player) entity).inventory.armorInventory[WILDCARD_2_SLOT].getItem().equals(AetherItems.ARMOR_TALISMAN_LEATHER)) {
            ci.cancel();
        }
    }

}
