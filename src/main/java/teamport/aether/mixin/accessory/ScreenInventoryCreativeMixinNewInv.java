package teamport.aether.mixin.accessory;

import net.minecraft.client.gui.container.ScreenInventoryCreative;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.texture.Texture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(value = ScreenInventoryCreative.class, remap = false)
public class ScreenInventoryCreativeMixinNewInv {

    // binds new texture
    @Redirect(method = "drawGuiContainerBackgroundLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextureManager;loadTexture(Ljava/lang/String;)Lnet/minecraft/client/render/texture/Texture;"))
    public Texture bindNewInventory(TextureManager instance, String name) {
        return instance.loadTexture("/assets/aether/textures/gui/container/creative.png");
    }
}
