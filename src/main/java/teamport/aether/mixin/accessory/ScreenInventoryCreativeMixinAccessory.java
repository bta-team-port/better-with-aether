package teamport.aether.mixin.accessory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.gui.container.ScreenInventoryCreative;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.texture.Texture;
import net.minecraft.core.lang.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ScreenInventoryCreative.class, remap = false)
public class ScreenInventoryCreativeMixinAccessory {

    @Redirect(method = "drawGuiContainerBackgroundLayer", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextureManager;loadTexture(Ljava/lang/String;)Lnet/minecraft/client/render/texture/Texture;"))
    public Texture bindNewInventory(TextureManager instance, String name) {
        return instance.loadTexture("/assets/aether/textures/gui/container/creative.png");
    }

    @Inject(method = "drawGuiContainerForegroundLayer", at=@At("HEAD"), cancellable = true)
    protected void fixLabelPlacement(CallbackInfo ci) {
        Font font = Minecraft.getMinecraft().font;
        font.drawString(I18n.getInstance().translateKey("gui.inventory.label.crafting"), 98, 16, 4210752);
        ci.cancel();
    }
}
