package teamport.aether.mixin.player;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.server.world.ServerPlayerController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.accessory.pendant.ItemPendant;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(value = ServerPlayerController.class)
public class ServerPlayerControllerPendantDamageMixin{

    @Shadow Player player;

    @Inject(method = "mineBlock(IIILnet/minecraft/core/util/helper/Side;)Z", at= @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Block;onBlockDestroyedByPlayer(Lnet/minecraft/core/world/World;IIILnet/minecraft/core/util/helper/Side;ILnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/item/Item;)V"))
    public void damagePendant(int x, int y, int z, Side side, CallbackInfoReturnable<Boolean> cir){
        if (this.player == null) return;
        ItemStack trinketSlot1 = PlayerUtil.getArmorOrAccessoryItem(this.player, TRINKET_1_SLOT);
        ItemStack trinketSlot2 = PlayerUtil.getArmorOrAccessoryItem(this.player, TRINKET_2_SLOT);
        if (trinketSlot1 != null && trinketSlot1.getItem() instanceof ItemPendant && ((ItemPendant) trinketSlot1.getItem()).canHarvestDamage()) {
            PlayerUtil.damageItemArmor(this.player, trinketSlot1, TRINKET_1_SLOT);
        }
        if (trinketSlot2 != null && trinketSlot2.getItem() instanceof ItemPendant && ((ItemPendant) trinketSlot2.getItem()).canHarvestDamage()) {
            PlayerUtil.damageItemArmor(this.player, trinketSlot2, TRINKET_2_SLOT);
        }
    }
}
