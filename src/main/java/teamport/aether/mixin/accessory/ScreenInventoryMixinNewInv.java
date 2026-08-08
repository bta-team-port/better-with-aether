package teamport.aether.mixin.accessory;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.render.font.FontRenderer;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.texture.Texture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(value = ScreenInventory.class, remap = false)
public abstract class ScreenInventoryMixinNewInv {
    // binds new texture
    @WrapOperation(method = "drawGuiContainerBackgroundLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextureManager;loadTexture(Ljava/lang/String;)Lnet/minecraft/client/render/texture/Texture;"))
    private Texture bindNewInventory(TextureManager instance, String name, Operation<Texture> original) {
        return original.call(instance, "/assets/aether/textures/gui/container/inventory.png");
    }
    // adjust text position
    @WrapOperation(method = "drawGuiContainerForegroundLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/container/ScreenInventory;drawStringNoShadow(Lnet/minecraft/client/render/font/FontRenderer;Ljava/lang/CharSequence;III)V"))
    private void fixLabelPlacement(ScreenInventory instance, FontRenderer fontRenderer, CharSequence text, int x, int y, int color, Operation<Void> original) {
        original.call(instance, fontRenderer, text, 98, y, color);
    }
}
