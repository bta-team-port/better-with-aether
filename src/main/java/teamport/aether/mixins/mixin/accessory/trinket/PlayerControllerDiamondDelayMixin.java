package teamport.aether.mixins.mixin.accessory.trinket;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.controller.PlayerController;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.item.AetherItems;

import static teamport.aether.item.accessory.SlotAccessory.GLOVES_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

@Environment(EnvType.CLIENT)
@Mixin(PlayerController.class)
public abstract class PlayerControllerDiamondDelayMixin {
    @Shadow
    protected int destroyDelay;
    @Shadow
    @Final
    protected Minecraft mc;
    @Inject(method = "continueDestroyBlock", at = @At("HEAD"))
    private void BlockHitDelay(CallbackInfo callbackInfo) {
        if (this.mc.thePlayer == null) return;
        ItemStack[] accessories = ((IContainerInventoryAether) this.mc.thePlayer.inventory).aether$getAccessoryInventory();
        int trinketOne = TRINKET_1_SLOT - GLOVES_SLOT;
        int trinketTwo = TRINKET_2_SLOT - GLOVES_SLOT;
        if (accessories[trinketOne] != null && accessories[trinketOne].getItem().namespaceID.equals(AetherItems.ARMOR_TALISMAN_DIAMOND.namespaceID)) {
            destroyDelay = destroyDelay / 2;
        }
        if (accessories[trinketTwo] != null && accessories[trinketTwo].getItem().namespaceID.equals(AetherItems.ARMOR_TALISMAN_DIAMOND.namespaceID)) {
            destroyDelay = destroyDelay / 2;
        }
    }
}
