package teamport.aether.mixin.accessory.cape;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.gamemode.Gamemode;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.item.accessory.AetherStatus;
import teamport.aether.item.accessory.ItemAccessoryArmor;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRendererPlayer.class, remap = false)
public abstract class MobRendererPlayerMixinCapeRender extends MobRenderer<Player> {
    protected MobRendererPlayerMixinCapeRender(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }
    @WrapOperation(method = "renderSpecials(Lnet/minecraft/core/entity/player/Player;F)V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V", ordinal = 4))
    private void renderPlayerInvisSpecialOne(float red, float green, float blue, float alpha, Operation<Void> original, Player player, float partialTick) {
        if (!((AetherStatus) player).aether$isInvisible()) {
            original.call(red, blue, green, alpha);
            return;
        }
        original.call(red, blue, green, 0.0F);
        GL11.glEnable(GL11.GL_BLEND);
    }
    @Inject(method = "renderSpecials(Lnet/minecraft/core/entity/player/Player;F)V", at = @At("HEAD"))
    private void renderPlayerInvisSpecialTwo(Player player, float partialTick, CallbackInfo ci, @Share("alphaTest") LocalFloatRef alphaTest) {
        alphaTest.set(GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF));
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
    }
    @Inject(method = "renderSpecials(Lnet/minecraft/core/entity/player/Player;F)V", at = @At("RETURN"))
    private void renderPlayerInvisSpecialThree(Player player, float partialTick, CallbackInfo ci, @Share("alphaTest")LocalFloatRef alphaTest) {
        GL11.glAlphaFunc(GL11.GL_GREATER, alphaTest.get());
    }
    @Definition(id = "spectator", field = "Lnet/minecraft/core/player/gamemode/Gamemode;spectator:Lnet/minecraft/core/player/gamemode/Gamemode;")
    @Expression("spectator")
    @ModifyExpressionValue(method = "renderSpecials(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/player/Player;DDD)V", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 1))
    private Gamemode renderPlayerInvisNametag(Gamemode original, Tessellator tessellator, Player entity, double d, double d1, double d2) {
        if (((AetherStatus) entity).aether$isInvisible()) return entity.getGamemode();
        return original;
    }
    @Definition(id = "bindDownloadableTexture", method = "Lnet/minecraft/client/render/entity/MobRendererPlayer;bindDownloadableTexture(Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/client/render/ImageParser;)Z")
    @Expression("? = ?.bindDownloadableTexture(?, ?, ?)")
    @Inject(method = "renderSpecials*", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER))
    private void renderAetherCape(Player player, float partialTick, CallbackInfo ci, @Local(name = "renderCape") LocalBooleanRef renderCape) {
        ItemStack itemStack = player.inventory.armorItemInSlot(5);
        if (itemStack == null) return;
        if (!(itemStack.getItem() instanceof ItemAccessoryArmor)) return;
        Item item = itemStack.getItem();
        String path = String.format("/assets/%s/textures/armor/cape/%s.png", item.namespaceID.namespace(), ((ItemAccessoryArmor) item).name());
        this.renderDispatcher.textureManager.loadTexture(path).bind();
        renderCape.set(true);
    }
}
