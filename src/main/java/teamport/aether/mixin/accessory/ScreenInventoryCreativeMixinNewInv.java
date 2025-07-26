package teamport.aether.mixin.accessory;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.gui.container.ScreenInventoryCreative;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.texture.Texture;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(value = ScreenInventoryCreative.class, remap = false)
public class ScreenInventoryCreativeMixinNewInv{

    // binds new texture
    @Redirect(method = "drawGuiContainerBackgroundLayer", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextureManager;loadTexture(Ljava/lang/String;)Lnet/minecraft/client/render/texture/Texture;"))
    public Texture bindNewInventory(TextureManager instance, String name) {
        return instance.loadTexture("/assets/aether/textures/gui/container/creative.png");
    }

    // adjust text position
    @Inject(method = "drawGuiContainerForegroundLayer", at=@At("HEAD"), cancellable = true)
    protected void fixLabelPlacement(CallbackInfo ci) {
        Font font = Minecraft.getMinecraft().font;
        font.drawString(I18n.getInstance().translateKey("gui.inventory.label.crafting"), 98, 16, 4210752);
        ci.cancel();
    }

    // TODO lookup ContainerInventoryMixinIncArmorInv for more info
    // make delete the accessory when shift clicking the delete button
    @WrapOperation(method = "buttonClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;getContainerSize()I", ordinal = 0))
    public int removeAccessoriesToo(ContainerInventory instance, Operation<Integer> original){
        return original.call(instance) + 4;
    }
}
