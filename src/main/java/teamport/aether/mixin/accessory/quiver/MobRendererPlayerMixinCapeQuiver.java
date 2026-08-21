package teamport.aether.mixin.accessory.quiver;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemQuiver;
import net.minecraft.core.item.ItemQuiverEndless;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.item.accessory.SlotAccessory;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRendererPlayer.class, priority = 900)
public abstract class MobRendererPlayerMixinCapeQuiver extends MobRenderer<Player> {
    protected MobRendererPlayerMixinCapeQuiver(float shadowSize) {
        super(shadowSize);
    }

    @Shadow
    protected abstract StaticEntityModel setupAnimations(Player player, StaticEntityModel model, float partialTick, int layer);

    @Unique
    protected StaticEntityModel getQuiverModel(Player player, float partialTick) {
        StaticEntityModel quiver = this.setupAnimations(player, this.getModel("aether.accessory.quiver"), partialTick, 6);
        quiver.getTransform("head").visible = false;
        quiver.getTransform("chest").visible = true;
        quiver.getTransform("rightArm").visible = false;
        quiver.getTransform("leftArm").visible = false;
        quiver.getTransform("rightLeg").visible = false;
        quiver.getTransform("leftLeg").visible = false;
        return quiver;
    }

    @WrapMethod(method = "getAndSetupModelForLayer(Lnet/minecraft/core/entity/player/Player;FFI)Lorg/useless/dragonfly/models/entity/StaticEntityModel;")
    private StaticEntityModel setQuiverModel(Player player, float brightness, float partialTick, int layer, Operation<StaticEntityModel> original) {
        if (layer == 6) {
            ItemStack armorStack = ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory()[SlotAccessory.CAPE_SLOT - SlotAccessory.GLOVES_SLOT];
            if (armorStack == null) {
                return null;
            }
            Item item = armorStack.getItem();
            ItemStack chestplate = player.inventory.armorInventory[1];
            if (item instanceof ItemQuiver || item instanceof ItemQuiverEndless) {
                StaticEntityModel quiver = this.getQuiverModel(player, partialTick);
                boolean isEndless = item instanceof ItemQuiverEndless;
                boolean isFlipped = chestplate != null && (chestplate.getItem() instanceof ItemQuiver || chestplate.getItem() instanceof ItemQuiverEndless);
                String path = String.format("/assets/%s/textures/armor/%s%s.png",
                    isFlipped ? "aether" : "minecraft",
                    isEndless ? "quiver_golden" : "quiver",
                    isFlipped ? "_flipped" : ""
                );
                this.renderDispatcher.textureManager.loadTexture(path).bind();
                return quiver;
            }
        }
        return original.call(player, brightness, partialTick, layer);
    }
}
