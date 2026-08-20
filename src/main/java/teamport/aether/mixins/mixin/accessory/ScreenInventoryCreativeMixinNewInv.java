package teamport.aether.mixins.mixin.accessory;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.container.ScreenInventoryCreative;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.texture.Texture;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(ScreenInventoryCreative.class)
public abstract class ScreenInventoryCreativeMixinNewInv {
    // binds new texture
    @WrapOperation(method = "drawGuiContainerBackgroundLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextureManager;loadTexture(Ljava/lang/String;)Lnet/minecraft/client/render/texture/Texture;"))
    private Texture bindNewInventory(TextureManager instance, String name, @NonNull Operation<Texture> original) {
        return original.call(instance, "/assets/aether/textures/gui/container/creative.png");
    }
}
