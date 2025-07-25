package teamport.aether.mixin.accessory;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.accessory.ItemAccessory;

@Mixin(value = MobRendererPlayer.class, remap = false)
abstract public class MobRendererPlayerMixinCapeRender extends MobRenderer<Player> {

    public MobRendererPlayerMixinCapeRender(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    @Inject(
            method = "renderSpecials",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/client/render/entity/MobRendererPlayer;bindDownloadableTexture(Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/client/render/ImageParser;)Z",
                    ordinal = 0
            )
    )
    private void renderAetherCape(Player player, float partialTick, CallbackInfo ci, @Local LocalBooleanRef renderCape) {
        ItemStack itemStack = player.inventory.armorItemInSlot(5);
        if (itemStack == null) return;
        if (!(itemStack.getItem() instanceof ItemAccessory)) return;
        Item item = itemStack.getItem();
        String path = String.format("/assets/%s/textures/armor/armor_cape_%s.png", item.namespaceID.namespace(), ((ItemAccessory)item).name());
        this.renderDispatcher.textureManager.loadTexture(path).bind();
        renderCape.set(true);
    }
}
