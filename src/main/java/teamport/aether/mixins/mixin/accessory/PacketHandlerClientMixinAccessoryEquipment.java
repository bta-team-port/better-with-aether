package teamport.aether.mixins.mixin.accessory;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.net.handler.PacketHandlerClient;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.PacketSetEquippedItem;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.ducks.IContainerInventoryAether;

@Environment(EnvType.CLIENT)
@Mixin(PacketHandlerClient.class)
public abstract class PacketHandlerClientMixinAccessoryEquipment {
    @Shadow
    protected abstract Entity getEntityByID(int entityID);

    @Inject(method = "handleSetEquippedItem", at = @At("HEAD"), cancellable = true)
    private void handleAccessoryEquipment(@NonNull PacketSetEquippedItem packetSetEquippedItem, CallbackInfo ci) {
        if (packetSetEquippedItem.slot < 5 || packetSetEquippedItem.slot >= 9) {
            return;
        }

        Entity entity = this.getEntityByID(packetSetEquippedItem.entityID);
        if (entity instanceof Player) {
            ItemStack stack = packetSetEquippedItem.itemID < 0
                ? null
                : new ItemStack(packetSetEquippedItem.itemID, 1, packetSetEquippedItem.itemMeta, packetSetEquippedItem.itemData);
            ((IContainerInventoryAether) ((Player) entity).inventory).aether$getAccessoryInventory()[packetSetEquippedItem.slot - 5] = stack;
        }
        ci.cancel();
    }
}
