package teamport.aether.mixins.mixin.accessory;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.font.FontRenderer;
import net.minecraft.client.render.texture.Texture;
import net.minecraft.core.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.ducks.IContainerInventoryAether;

@Environment(EnvType.CLIENT)
@Mixin(ScreenInventory.class)
public abstract class ScreenInventoryMixinNewInv {
    @Shadow
    private ButtonElement armorButton;

    // binds new texture
    @WrapOperation(method = "drawGuiContainerBackgroundLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextureManager;loadTexture(Ljava/lang/String;)Lnet/minecraft/client/render/texture/Texture;"))
    private Texture bindNewInventory(TextureManager instance, String name, @NonNull Operation<Texture> original) {
        return original.call(instance, "/assets/aether/textures/gui/container/inventory.png");
    }

    // adjust text position
    @WrapOperation(method = "drawGuiContainerForegroundLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/container/ScreenInventory;drawStringNoShadow(Lnet/minecraft/client/render/font/FontRenderer;Ljava/lang/CharSequence;III)V"))
    private void fixLabelPlacement(ScreenInventory instance, FontRenderer fontRenderer, CharSequence text, int x, int y, int color, @NonNull Operation<Void> original) {
        original.call(instance, fontRenderer, text, 98, y, color);
    }

    @Inject(method = "checkForArmor", at = @At("TAIL"))
    private void checkAccessoriesForArmorButton(CallbackInfo ci) {
        ScreenInventory screen = (ScreenInventory) (Object) this;

        if (this.armorButton.enabled || screen.mc.thePlayer == null) {
            return;
        }

        ItemStack[] accessories = ((IContainerInventoryAether) screen.mc.thePlayer.inventory).aether$getAccessoryInventory();
        if (accessories != null) {
            for (ItemStack stack : accessories) {
                if (stack != null) {
                    this.armorButton.enabled = true;
                    break;
                }
            }
        }
    }
}
