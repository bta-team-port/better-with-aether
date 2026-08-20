package teamport.aether.mixins.mixin.accessory.cape;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.ImageParser;
import net.minecraft.client.render.entity.MobRendererBipedArmored;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.gamemode.Gamemode;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.accessory.cape.ItemCape;

@Environment(EnvType.CLIENT)
@Mixin(MobRendererPlayer.class)
public abstract class MobRendererPlayerMixinCapeRender extends MobRendererBipedArmored<Player> {
    protected MobRendererPlayerMixinCapeRender(float shadowSize) {
        super(shadowSize);
    }

    @WrapOperation(method = "renderAdditional(Lnet/minecraft/client/render/tessellator/TessellatorGeneral;Lnet/minecraft/core/entity/player/Player;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/renderer/GLRenderer;setColor4f(FFFF)V"))
    private void renderCapeInvisible(float red, float green, float blue, float alpha, Operation<Void> original, TessellatorGeneral tessellator, Player player, float partialTick) {
        if (!PlayerUtil.isInvisible(player)) {
            original.call(red, green, blue, alpha);
            return;
        }
        original.call(red, green, blue, 0.0F);
        GLRenderer.enableState(State.BLEND);
    }

    @ModifyExpressionValue(method = "renderSpecials(Lnet/minecraft/client/render/tessellator/TessellatorGeneral;Lnet/minecraft/core/entity/player/Player;DDD)V", at = @At(value = "FIELD", target = "Lnet/minecraft/core/player/gamemode/Gamemodes;SPECTATOR:Lnet/minecraft/core/player/gamemode/Gamemode;", ordinal = 1))
    private Gamemode renderPlayerInvisNametag(Gamemode original, TessellatorGeneral tessellator, Player player, double x, double y, double z) {
        if (PlayerUtil.isInvisible(player)) {
            return player.getGamemode();
        }
        return original;
    }

    @WrapOperation(method = "renderAdditional(Lnet/minecraft/client/render/tessellator/TessellatorGeneral;Lnet/minecraft/core/entity/player/Player;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/MobRendererPlayer;bindDownloadableTexture(Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/client/render/ImageParser;)Z", ordinal = 0))
    private boolean bindAetherCape(MobRendererPlayer renderer, String url, String fallback, ImageParser parser, Operation<Boolean> original, TessellatorGeneral tessellator, @NonNull Player player, float partialTick) {
        ItemStack itemStack = ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory()[1];
        if (itemStack != null && itemStack.getItem() instanceof ItemCape) {
            Item item = itemStack.getItem();
            String path = String.format("/assets/%s/textures/armor/cape/%s.png", item.namespaceID.namespace(), ((ItemCape) item).name());
            this.renderDispatcher.textureManager.loadTexture(path).bind();
            return true;
        }
        return original.call(renderer, url, fallback, parser);
    }
}
