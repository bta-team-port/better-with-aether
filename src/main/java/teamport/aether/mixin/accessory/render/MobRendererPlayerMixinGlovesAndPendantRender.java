package teamport.aether.mixin.accessory.render;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
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
import teamport.aether.items.accessory.ItemAccessoryPendant;

import static teamport.aether.items.accessory.SlotAccessory.GLOVES_SLOT;

@Mixin(value = MobRendererPlayer.class, remap = false)
abstract public class MobRendererPlayerMixinGlovesAndPendantRender extends MobRenderer<Player> {

    @Shadow private ModelBiped modelBipedMain;
    @Shadow @Final private ModelBiped modelArmorChestplate;
    @Unique
    public final ModelBiped modelAccessories = new ModelBiped(1.0F);

    public MobRendererPlayerMixinGlovesAndPendantRender(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    // TODO fix the gloves in the first person
    // TODO fix armor not shaking with the player when punching in third person
    @Inject(method = "drawFirstPersonHand", at = @At("TAIL"), cancellable = true)
    public void callDrawFirstPersonHandAfter(Player player, boolean isLeft, CallbackInfo ci) {
        ItemStack itemStack = player.inventory.armorInventory[GLOVES_SLOT];
        if (itemStack != null && itemStack.getItem() instanceof ItemAccessoryGloves) {
            Item item = itemStack.getItem();
            String path = String.format("/assets/%s/textures/armor/%s_pendant_and_gloves.png", item.namespaceID.namespace(), ((ItemAccessory) item).name());
            if (renderDispatcher.textureManager == null) return;
            renderDispatcher.textureManager.loadTexture(path).bind();
            this.modelAccessories.onGround = 0.0f;
            this.modelAccessories.isRiding = false;
            this.modelAccessories.setupAnimation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
            if (isLeft) {
                this.modelAccessories.armLeft.visible = true;
                this.modelAccessories.armLeft.render(0.0625F);
            } else {
                this.modelAccessories.armRight.visible = true;
                this.modelAccessories.armRight.render(0.0625F);
            }
            this.modelAccessories.armLeft.visible = false;
            this.modelAccessories.armRight.visible = false;
        }
        ci.cancel();
    }

    @ModifyArg(method = "prepareArmor*", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;armorItemInSlot(I)Lnet/minecraft/core/item/ItemStack;"))
    public int getArmorItemNotNegative(int i, @Local(argsOnly = true) int renderPass) {
        return (renderPass > 3) ? renderPass : 3 - renderPass;
    }

    @Inject(method = "prepareArmor*", at = @At("HEAD"), cancellable = true)
    public void setArmorModel(Player player, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> info) {
        this.modelAccessories.holdingRightHand = player.inventory.getCurrentItem() != null;
        this.modelAccessories.sneaking = player.isSneaking();
        this.modelAccessories.isRiding = player.isPassenger();
        this.modelAccessories.body.visible = renderPass == 6 || renderPass == 7;
        this.modelAccessories.armLeft.visible = renderPass == 4;
        this.modelAccessories.armRight.visible = renderPass == 4;

        ItemStack itemStack = player.inventory.armorInventory[renderPass];
        if (itemStack == null) {
            return;
        }

        Item item = itemStack.getItem();
        if (!(item instanceof ItemAccessoryGloves) && !(item instanceof ItemAccessoryPendant)) {
            return;
        }

        String path = String.format("/assets/%s/textures/armor/%s_pendant_and_gloves.png", item.namespaceID.namespace(), ((ItemAccessory) item).name());
        renderDispatcher.textureManager.loadTexture(path).bind();
        this.setArmorModel(this.modelAccessories);
        info.setReturnValue(true);
    }
}
