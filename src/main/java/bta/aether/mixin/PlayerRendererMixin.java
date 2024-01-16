package bta.aether.mixin;


import bta.aether.entity.IAetherAccessories;
import bta.aether.item.Accessories.base.ItemAccessoryCape;
import bta.aether.item.Accessories.base.ItemAccessoryGloves;
import bta.aether.item.Accessories.base.ItemAccessoryPendant;
import com.llamalad7.mixinextras.sugar.Local;
import csweetla.accessoryapi.API.Accessory;
import net.minecraft.client.render.ImageParser;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(value = PlayerRenderer.class, remap = false)
public class PlayerRendererMixin extends LivingRenderer<EntityPlayer> {
    @Shadow private ModelBiped modelBipedMain;
    @Unique
    private final ModelBiped modelAccessories = new ModelBiped(1.0F);

    public PlayerRendererMixin(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    @Inject(method = "drawFirstPersonHand", at = @At("HEAD"), cancellable = true)
    public void callDrawFirstPersonHandBefore(EntityPlayer player, CallbackInfo info) {
        if (((IAetherAccessories)player).aether$getInvisible()) info.cancel();
    }

    @Inject(method = "drawFirstPersonHand", at = @At("TAIL"))
    public void callDrawFirstPersonHandAfter(EntityPlayer player, CallbackInfo info) {
        for (ItemStack itemStack : player.inventory.armorInventory) {
            if (itemStack == null) continue;
            Item item = itemStack.getItem();
            if (item instanceof ItemAccessoryGloves) {
                String path = ((ItemAccessoryGloves)item).getTexturePath();
                if (renderDispatcher.renderEngine == null) continue;
                this.loadTexture(path);
                this.modelAccessories.onGround = 0.0f;
                this.modelAccessories.isRiding = false;
                this.modelAccessories.bipedRightArm.showModel = true;
                this.modelAccessories.setRotationAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.065f);
                this.modelAccessories.bipedRightArm.render(0.065f);
                this.modelAccessories.bipedRightArm.showModel = false;
            }
        }
    }

    @Inject(method = "renderPlayer", at = @At("HEAD"), cancellable = true)
    public void renderPlayer(EntityPlayer player, double d, double d1, double d2, float yaw, float partialTick, CallbackInfo info) {
        if (((IAetherAccessories)player).aether$getInvisible()) info.cancel();
    }

    @ModifyArg(method = "setArmorModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/InventoryPlayer;armorItemInSlot(I)Lnet/minecraft/core/item/ItemStack;"))
    public int getArmorItemNotNegative(int i, @Local int renderPass) {
        return (renderPass > 3) ? renderPass : 3-renderPass;
    }

    @Inject(method = "setArmorModel", at = @At("HEAD"), cancellable = true)
    public void setArmorModel(EntityPlayer player, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> info) {
        this.modelAccessories.field_1278_i = player.inventory.getCurrentItem() != null;
        this.modelAccessories.isSneak = player.isSneaking();
        this.modelAccessories.isRiding = player.isPassenger();
        this.modelAccessories.bipedBody.showModel = renderPass == 4;
        this.modelAccessories.bipedLeftArm.showModel = renderPass == 10;
        this.modelAccessories.bipedRightArm.showModel = renderPass == 10;
        this.modelBipedMain.bipedCloak.showModel = renderPass == 5;
        String path = null;
        ItemStack itemStack = player.inventory.armorInventory[renderPass];
        if (itemStack == null) return;
        Item item = itemStack.getItem();
        if (!(item instanceof Accessory)) return;
        if (item instanceof ItemAccessoryGloves) {
            path = ((ItemAccessoryGloves)item).getTexturePath();
        } else if (item instanceof ItemAccessoryPendant) {
            path = ((ItemAccessoryPendant)item).getTexturePath();
        } else if (item instanceof ItemAccessoryCape) {
            path = ((ItemAccessoryCape)item).getTexturePath();
        }
        this.loadTexture(path);
        this.setRenderPassModel(this.modelAccessories);
        info.setReturnValue(true);
    }

    @Redirect(method = "renderSpecials(Lnet/minecraft/core/entity/player/EntityPlayer;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/PlayerRenderer;loadDownloadableTexture(Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/client/render/ImageParser;)Z"))
    public boolean renderSpecials(PlayerRenderer instance, String s1, String s2, ImageParser imageParser)  {
        if (!modelBipedMain.bipedCloak.showModel) return instance.loadDownloadableTexture(s1, s2, imageParser);
        return true;
    }

}