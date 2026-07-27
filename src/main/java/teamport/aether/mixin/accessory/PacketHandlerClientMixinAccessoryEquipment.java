package teamport.aether.mixin.accessory;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.net.handler.PacketHandlerClient;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.PacketSetEquippedItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.ducks.IContainerInventoryAether;

@Environment(EnvType.CLIENT)
@Mixin(value = PacketHandlerClient.class, remap = false)
public abstract class PacketHandlerClientMixinAccessoryEquipment {
    @Shadow
    protected abstract Entity getEntityByID(int entityId);

    @Inject(method = "handleSetEquippedItem", at = @At("HEAD"), cancellable = true)
    private void handleAccessoryEquipment(PacketSetEquippedItem packet, CallbackInfo ci) {
        if (packet.slot < 5 || packet.slot >= 9) {
            return;
        }

        Entity entity = this.getEntityByID(packet.entityID);
        if (entity instanceof Player) {
            ItemStack stack = packet.itemID < 0
                ? null
                : new ItemStack(packet.itemID, 1, packet.itemMeta, packet.itemData);
            ((IContainerInventoryAether) ((Player) entity).inventory).aether$getAccessoryInventory()[packet.slot - 5] = stack;
        }
        ci.cancel();
    }
}
