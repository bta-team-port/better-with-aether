package teamport.aether.mixin.accessory.quiver;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemQuiver;
import net.minecraft.core.item.ItemQuiverEndless;
import net.minecraft.core.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import teamport.aether.ducks.IContainerInventoryAether;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRendererPlayer.class, priority = 1100)
public abstract class MobRendererPlayerMixinCapeQuiver extends MobRenderer<Player> {
    protected MobRendererPlayerMixinCapeQuiver(float shadowSize) {
        super(shadowSize);
    }

    @Shadow
    protected abstract StaticEntityModel setupAnimations(Player player, StaticEntityModel model, float partialTick, int layer);

    @Unique
    protected StaticEntityModel better_with_aether$getQuiverModel(Player player, float partialTick) {
        StaticEntityModel quiver = this.setupAnimations(player, this.getModel("aether.accessory.quiver"), partialTick, 6);
        quiver.getTransform("head").visible = false;
        quiver.getTransform("chest").visible = true;
        quiver.getTransform("rightArm").visible = false;
        quiver.getTransform("leftArm").visible = false;
        quiver.getTransform("rightLeg").visible = false;
        quiver.getTransform("leftLeg").visible = false;
        return quiver;
    }

    @Inject(method = "getAndSetupModelForLayer(Lnet/minecraft/core/entity/player/Player;FFI)Lorg/useless/dragonfly/models/entity/StaticEntityModel;", at = @At("HEAD"), cancellable = true)
    private void setQuiverModel(@NonNull Player player, float brightness, float partialTick, int renderLayer, CallbackInfoReturnable<StaticEntityModel> info) {
        if (renderLayer != 6) return;

        StaticEntityModel quiver = this.better_with_aether$getQuiverModel(player, partialTick);

        ItemStack armorStack = ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory()[1];
        if (armorStack == null) return;
        Item item = armorStack.getItem();
        ItemStack chestplate = player.inventory.armorInventory[2];
        if (item instanceof ItemQuiver) {
            String path = "/assets/minecraft/textures/armor/quiver.png";
            if (chestplate != null && (chestplate.getItem() instanceof ItemQuiver || chestplate.getItem() instanceof ItemQuiverEndless)) {
                path = "/assets/aether/textures/armor/quiver_flipped.png";
            }
            this.bindTexture(path);
            info.setReturnValue(quiver);
            return;
        }
        if (item instanceof ItemQuiverEndless) {
            String path = "/assets/minecraft/textures/armor/quiver_golden.png";
            if (chestplate != null && (chestplate.getItem() instanceof ItemQuiver || chestplate.getItem() instanceof ItemQuiverEndless)) {
                path = "/assets/aether/textures/armor/quiver_golden_flipped.png";
            }
            this.bindTexture(path);
            info.setReturnValue(quiver);
        }
    }
}
