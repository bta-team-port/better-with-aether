package teamport.aether.mixin.accessory.trinket;

import net.minecraft.core.block.BlockLogicFarmland;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.AetherItems;

import static teamport.aether.items.accessory.SlotAccessory.*;

@Mixin(value = BlockLogicFarmland.class, remap = false)
public class BlockLogicFarmlandLeatherPendantMixin {
    @Inject(method = "onEntityWalking", at = @At(value = "HEAD"), cancellable = true)
    public void onEntityWalking(World world, int x, int y, int z, Entity entity, CallbackInfo ci) {
        if(!(entity instanceof Player)) return;
        ItemStack[] armor = ((Player) entity).inventory.armorInventory;
        if (armor[TRINKET_1_SLOT] != null && armor[TRINKET_1_SLOT].getItem().namespaceID.equals(AetherItems.ARMOR_TALISMAN_LEATHER.namespaceID)) {
            ci.cancel();
        }
        if (armor[TRINKET_2_SLOT] != null && armor[TRINKET_2_SLOT].getItem().namespaceID.equals(AetherItems.ARMOR_TALISMAN_LEATHER.namespaceID)) {
            ci.cancel();
        }
    }

}
