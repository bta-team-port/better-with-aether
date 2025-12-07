package teamport.aether.mixin.accessory.trinket;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.controller.PlayerController;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.item.accessory.pendant.ItemPendant;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

@Environment(EnvType.CLIENT)
@Mixin(value = PlayerController.class, remap = false)
public abstract class PlayerControllerPendantDamageMixin {
    @Shadow
    @Final
    protected Minecraft mc;
    @Inject(method = "destroyBlock", at= @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Block;onBlockDestroyedByPlayer(Lnet/minecraft/core/world/World;IIILnet/minecraft/core/util/helper/Side;ILnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/item/Item;)V"))
    public void damagePendant(int x, int y, int z, Side side, Player player, CallbackInfoReturnable<Boolean> cir){
        if (this.mc.thePlayer == null) return;
        ItemStack[] armor = this.mc.thePlayer.inventory.armorInventory;
        ItemStack trinketSlot1 = armor[TRINKET_1_SLOT];
        ItemStack trinketSlot2 = armor[TRINKET_2_SLOT];
        if (trinketSlot1 != null && trinketSlot1.getItem() instanceof ItemPendant && ((ItemPendant) trinketSlot1.getItem()).canHarvestDamage()) {
            trinketSlot1.damageItem(1, player);
            if (trinketSlot1.stackSize <= 0) {
                this.mc.thePlayer.inventory.armorInventory[TRINKET_1_SLOT] = null;
            }
        }
        if (trinketSlot2 != null && trinketSlot2.getItem() instanceof ItemPendant && ((ItemPendant) trinketSlot2.getItem()).canHarvestDamage()) {
            trinketSlot2.damageItem(1, player);
            if (trinketSlot2.stackSize <= 0) {
                this.mc.thePlayer.inventory.armorInventory[TRINKET_2_SLOT] = null;
            }
        }
    }
}
