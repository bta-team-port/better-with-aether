package teamport.aether.mixin.accessory.cape;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import teamport.aether.items.accessory.ItemAccessoryArmor;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRendererPlayer.class, remap = false)
public abstract class MobRendererPlayerMixinCapeRender extends MobRenderer<Player> {
    protected MobRendererPlayerMixinCapeRender(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }
    @Definition(id = "bindDownloadableTexture", method = "Lnet/minecraft/client/render/entity/MobRendererPlayer;bindDownloadableTexture(Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/client/render/ImageParser;)Z")
    @Expression("? = ?.bindDownloadableTexture(?, ?, ?)")
    @Inject(method = "renderSpecials*", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER))
    public void renderAetherCape(Player player, float partialTick, CallbackInfo ci, @Local(name = "renderCape") LocalBooleanRef renderCape) {
        ItemStack itemStack = player.inventory.armorItemInSlot(5);
        if (itemStack == null) return;
        if (!(itemStack.getItem() instanceof ItemAccessoryArmor)) return;
        Item item = itemStack.getItem();
        String path = String.format("/assets/%s/textures/armor/cape/%s.png", item.namespaceID.namespace(), ((ItemAccessoryArmor) item).name());
        this.renderDispatcher.textureManager.loadTexture(path).bind();
        renderCape.set(true);
    }
}
