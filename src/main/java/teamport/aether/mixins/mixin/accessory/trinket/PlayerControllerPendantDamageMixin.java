package teamport.aether.mixins.mixin.accessory.trinket;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.controller.PlayerController;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.accessory.pendant.ItemPendant;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

@Environment(EnvType.CLIENT)
@Mixin(PlayerController.class)
public abstract class PlayerControllerPendantDamageMixin {
    @Shadow
    @Final
    protected Minecraft mc;
    @Inject(method = "destroyBlock", at= @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Block;onDestroyedByPlayer(Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/util/helper/Side;ILnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/item/Item;)V"))
    public void damagePendant(TilePosc tilePos, Side side, CallbackInfoReturnable<Boolean> cir){
        if (this.mc.thePlayer == null) return;
        ItemStack trinketSlot1 = PlayerUtil.getArmorOrAccessoryItem(this.mc.thePlayer, TRINKET_1_SLOT);
        ItemStack trinketSlot2 = PlayerUtil.getArmorOrAccessoryItem(this.mc.thePlayer, TRINKET_2_SLOT);
        if (trinketSlot1 != null && trinketSlot1.getItem() instanceof ItemPendant && ((ItemPendant) trinketSlot1.getItem()).canHarvestDamage()) {
            PlayerUtil.damageItemArmor(this.mc.thePlayer, trinketSlot1, TRINKET_1_SLOT);
        }
        if (trinketSlot2 != null && trinketSlot2.getItem() instanceof ItemPendant && ((ItemPendant) trinketSlot2.getItem()).canHarvestDamage()) {
            PlayerUtil.damageItemArmor(this.mc.thePlayer, trinketSlot2, TRINKET_2_SLOT);
        }
    }
}
