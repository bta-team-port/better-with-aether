package bta.aether.mixin;

import bta.aether.Aether;
import bta.aether.item.Accessories.base.ItemAccessoryCape;
import bta.aether.item.AetherItems;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerRenderer.class, remap = false)
abstract public class PlayerRendererMixinCapes extends LivingRenderer<EntityPlayer> {
    public PlayerRendererMixinCapes(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    @Inject(
            method = "renderSpecials",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/client/render/entity/PlayerRenderer;loadDownloadableTexture(Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/client/render/ImageParser;)Z",
                    ordinal = 0
            )
    )
    private void renderAetherCape(EntityPlayer entity, float f, CallbackInfo ci, @Local LocalBooleanRef renderCape) {
        ItemStack itemStack = entity.inventory.armorItemInSlot(5);
        if (itemStack == null) {
            return;
        }

        Item item = itemStack.getItem();
        if (!(item instanceof ItemAccessoryCape)) {
            return;
        }

        renderDispatcher.renderEngine.bindTexture(renderDispatcher.renderEngine.getTexture(((ItemAccessoryCape) item).getTexturePath()));
        renderCape.set(true);
    }
}
