package teamport.aether.mixin.accessory.trinket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.controller.PlayerController;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.AetherItems;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.items.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(value = PlayerController.class, remap = false)
public abstract class PlayerControllerDiamondDelayMixin {
    @Shadow
    protected int blockHitDelay;
    @Shadow
    @Final
    protected Minecraft mc;
    @Inject(method = "continueDestroyBlock", at = @At("HEAD"))
    public void BlockHitDelay(CallbackInfo callbackInfo) {
        if (this.mc.thePlayer == null) return;
        ItemStack[] armor = this.mc.thePlayer.inventory.armorInventory;
        if (armor[TRINKET_1_SLOT] != null && armor[TRINKET_1_SLOT].getItem().namespaceID.equals(AetherItems.ARMOR_TALISMAN_DIAMOND.namespaceID)) {
            blockHitDelay = blockHitDelay / 2;
        }
        if (armor[TRINKET_2_SLOT] != null && armor[TRINKET_2_SLOT].getItem().namespaceID.equals(AetherItems.ARMOR_TALISMAN_DIAMOND.namespaceID)) {
            blockHitDelay = blockHitDelay / 2;
        }
    }
}
