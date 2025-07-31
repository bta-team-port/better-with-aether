package teamport.aether.mixin.accessory.gloves;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.client.render.model.ModelPlayer;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.items.accessory.ItemAccessory;
import teamport.aether.items.accessory.ItemAccessoryGloves;
import teamport.aether.items.accessory.ItemAccessoryTrinket;
import teamport.aether.items.accessory.ItemRepulsionShield;

import static teamport.aether.items.accessory.SlotAccessory.*;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRendererPlayer.class, remap = false)
abstract public class MobRendererPlayerMixinGlovesAndPendantRender extends MobRenderer<Player> {

    @Shadow @Final private ModelBiped modelArmorChestplate;
    @Shadow private ModelBiped modelBipedMain;

    @Unique
    public final ModelBiped modelAccessories = new ModelBiped(1.0F);

    public MobRendererPlayerMixinGlovesAndPendantRender(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    // TODO fix armor not shaking with the player when punching in third person
    @Inject(method = "drawFirstPersonHand", at = @At("TAIL"), cancellable = true)
    public void callDrawFirstPersonHandAfter(@NotNull Player player, boolean isLeft, CallbackInfo ci) {
        ItemStack itemStack = player.inventory.armorInventory[GLOVES_SLOT];
        if (itemStack != null && itemStack.getItem() instanceof ItemAccessoryGloves) {
            Item item = itemStack.getItem();
            String path = String.format("/assets/%s/textures/armor/%s_pendant_and_gloves.png", item.namespaceID.namespace(), ((ItemAccessory) item).name());
            if (renderDispatcher.textureManager == null) return;
            renderDispatcher.textureManager.loadTexture(path).bind();

            modelAccessories.onGround = 0.0f;
            modelAccessories.isRiding = false;
            modelAccessories.setupAnimation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);

            if (modelBipedMain instanceof ModelPlayer) {
                if (isLeft) {
                    GL11.glDisable(GL11.GL_CULL_FACE);
                    modelAccessories.armLeft.visible = true;
                    modelAccessories.armLeft.render(0.0625F);
                } else {
                    GL11.glDisable(GL11.GL_CULL_FACE);
                    modelAccessories.armRight.visible = true;
                    modelAccessories.armRight.render(0.0625F);
                }
            }

            modelAccessories.armLeft.visible = false;
            modelAccessories.armRight.visible = false;
        }

        ci.cancel();
    }

    @ModifyArg(method = "prepareArmor*", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;armorItemInSlot(I)Lnet/minecraft/core/item/ItemStack;"))
    public int getArmorItemNotNegative(int i, @Local(argsOnly = true) int renderPass) {
        return (renderPass > 3) ? renderPass : 3 - renderPass;
    }

    @Inject(method = "prepareArmor*", at = @At("HEAD"), cancellable = true)
    public void setArmorModel(@NotNull Player player, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> info) {
        modelAccessories.holdingRightHand = player.inventory.getCurrentItem() != null;
        modelAccessories.sneaking = player.isSneaking();
        modelAccessories.isRiding = player.isPassenger();
        modelAccessories.body.visible = renderPass == TRINKET_1_SLOT || renderPass == TRINKET_2_SLOT;
        modelAccessories.armLeft.visible = renderPass == GLOVES_SLOT;
        modelAccessories.armRight.visible = renderPass == GLOVES_SLOT;

        ItemStack armorStack = player.inventory.armorInventory[renderPass];
        if (armorStack == null) return;

        Item item = armorStack.getItem();
        if (!(item instanceof ItemAccessoryGloves) && !(item instanceof ItemAccessoryTrinket)) return;

        String path = String.format("/assets/%s/textures/armor/%s_pendant_and_gloves.png", item.namespaceID.namespace(), ((ItemAccessory) item).name());

        if (item instanceof ItemRepulsionShield && (renderPass == TRINKET_1_SLOT || renderPass == TRINKET_2_SLOT)) {
            path = String.format("/assets/%s/textures/armor/energyNotGlow.png", item.namespaceID.namespace());
        }

        renderDispatcher.textureManager.loadTexture(path).bind();
        setArmorModel(modelAccessories);

        info.setReturnValue(true);
    }
}
