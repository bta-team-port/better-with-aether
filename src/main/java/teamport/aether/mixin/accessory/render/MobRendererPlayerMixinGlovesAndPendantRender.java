package teamport.aether.mixin.accessory.render;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.items.accessory.ItemAccessory;
import teamport.aether.items.accessory.ItemAccessoryGloves;
import teamport.aether.items.accessory.ItemAccessoryPendant;

@Mixin(value = MobRendererPlayer.class, remap = false)
abstract public class MobRendererPlayerMixinGlovesAndPendantRender extends MobRenderer<Player>{

    @Unique
    private final ModelBiped modelAccessories = new ModelBiped(1.0F);

    public MobRendererPlayerMixinGlovesAndPendantRender(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    // TODO fix the gloves in the first person
    // TODO fix pendant also being rendered when gloves are equipped
    @Inject(method = "drawFirstPersonHand", at = @At("TAIL"))
    public void callDrawFirstPersonHandAfter(Player player, boolean isLeft, CallbackInfo ci) {
        for (ItemStack itemStack : player.inventory.armorInventory) {
            if (itemStack == null) continue;
            Item item = itemStack.getItem();
            if (item instanceof ItemAccessoryGloves) {
                String path = String.format("/assets/%s/textures/armor/%s_pendant_and_gloves.png",item.namespaceID.namespace(), ((ItemAccessory) item).name());
                if (renderDispatcher.textureManager == null) continue;
                renderDispatcher.textureManager.loadTexture(path).bind();
                this.modelAccessories.onGround = 0.0f;
                this.modelAccessories.isRiding = false;
                this.modelAccessories.armRight.visible = true;
                this.modelAccessories.setupAnimation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.065f);
                this.modelAccessories.armRight.render(0.065f);
                this.modelAccessories.armRight.visible = false;
            }
        }
    }

    @ModifyArg(method = "prepareArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;armorItemInSlot(I)Lnet/minecraft/core/item/ItemStack;"))
    public int getArmorItemNotNegative(int i, @Local int renderPass) {
        return (renderPass > 3) ? renderPass : 3-renderPass;
    }

    @Inject(method = "prepareArmor", at = @At("HEAD"), cancellable = true)
    public void setArmorModel(Player player, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> info) {
        this.modelAccessories.holdingRightHand = player.inventory.getCurrentItem() != null;
        this.modelAccessories.sneaking = player.isSneaking();
        this.modelAccessories.isRiding = player.isPassenger();
        this.modelAccessories.body.visible = renderPass == 4;
        this.modelAccessories.armLeft.visible = renderPass == 10;
        this.modelAccessories.armRight.visible = renderPass == 10;

        ItemStack itemStack = player.inventory.armorInventory[renderPass];
        if (itemStack == null) {
            return;
        }

        Item item = itemStack.getItem();
        if (!(item instanceof ItemAccessoryGloves) && !(item instanceof ItemAccessoryPendant)) {
            return;
        }
        String path = String.format("/assets/%s/textures/armor/%s_pendant_and_gloves.png",item.namespaceID.namespace(),  ((ItemAccessory)item).name());
        renderDispatcher.textureManager.loadTexture(path).bind();
        this.setArmorModel(this.modelAccessories);
        info.setReturnValue(true);
    }
}
