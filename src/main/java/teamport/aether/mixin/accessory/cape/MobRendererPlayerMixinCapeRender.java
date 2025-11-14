package teamport.aether.mixin.accessory.cape;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.accessory.AetherInvisibility;
import teamport.aether.items.accessory.ItemAccessoryArmor;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRendererPlayer.class, remap = false)
abstract public class MobRendererPlayerMixinCapeRender extends MobRenderer<Player> {

    public MobRendererPlayerMixinCapeRender(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    @Inject(method = "renderSpecials*", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", shift = At.Shift.BEFORE))
    public void renderPlayerInvisSpecial(Player entity, float partialTick, CallbackInfo ci) {
        if (((AetherInvisibility) entity).aether$isInvisible() && entity instanceof Player) {
            GL11.glColor4f(0.5F, 0.5F, 0.5F, 0.15F);
        }
    }

    @Inject(
            method = "renderSpecials*",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/render/entity/MobRendererPlayer;bindDownloadableTexture(Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/client/render/ImageParser;)Z", ordinal = 0)
    )
    public void renderAetherCape(Player player, float partialTick, CallbackInfo ci, @Local LocalBooleanRef renderCape) {
        ItemStack itemStack = player.inventory.armorItemInSlot(5);
        if (itemStack == null) return;
        if (!(itemStack.getItem() instanceof ItemAccessoryArmor)) return;
        Item item = itemStack.getItem();
        String path = String.format("/assets/%s/textures/armor/cape/%s.png", item.namespaceID.namespace(), ((ItemAccessoryArmor) item).name());
        this.renderDispatcher.textureManager.loadTexture(path).bind();
        renderCape.set(true);
    }
}
