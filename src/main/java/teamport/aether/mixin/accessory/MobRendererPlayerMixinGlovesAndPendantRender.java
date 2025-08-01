package teamport.aether.mixin.accessory;

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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.items.accessory.IAccessory;
import teamport.aether.items.accessory.ItemAccessoryArmor;
import teamport.aether.items.accessory.ItemGloves;
import teamport.aether.items.accessory.pendant.ItemPendant;
import teamport.aether.items.accessory.trinket.ItemRepulsionShield;

import static teamport.aether.items.accessory.SlotAccessory.*;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRendererPlayer.class, remap = false)
abstract public class MobRendererPlayerMixinGlovesAndPendantRender extends MobRenderer<Player> {

    @Shadow private ModelBiped modelBipedMain;

    @Unique
    public final ModelBiped modelAccessories = new ModelBiped(1.0F);
    @Unique
    public final ModelBiped shield = new ModelBiped(1.5F);

    public MobRendererPlayerMixinGlovesAndPendantRender(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    // TODO fix armor not shaking with the player when punching in third person
    @Inject(method = "drawFirstPersonHand", at = @At("TAIL"), cancellable = true)
    public void callDrawFirstPersonHandAfter(@NotNull Player player, boolean isLeft, CallbackInfo ci) {
        ItemStack itemStack = player.inventory.armorInventory[GLOVES_SLOT];
        if (itemStack != null && itemStack.getItem() instanceof ItemGloves) {
            Item item = itemStack.getItem();
            String path = String.format("/assets/%s/textures/armor/%s_pendant_and_gloves.png", item.namespaceID.namespace(), ((ItemAccessoryArmor) item).name());
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

    @Inject(method = "prepareArmor*", at = @At("TAIL"), cancellable = true)
    public void setArmorModel(@NotNull Player player, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> info) {
        ItemStack armorStack = player.inventory.armorInventory[renderPass];
        if (armorStack != null && armorStack.getItem() instanceof IAccessory) {
            Item item = armorStack.getItem();
            if ((item instanceof ItemGloves) || (item instanceof ItemPendant)) {
                String path = String.format("/assets/%s/textures/armor/%s_pendant_and_gloves.png", item.namespaceID.namespace(), ((IAccessory) item).name());
                modelAccessories.holdingRightHand = player.inventory.getCurrentItem() != null;
                modelAccessories.sneaking = player.isSneaking();
                modelAccessories.isRiding = player.isPassenger();
                modelAccessories.body.visible = renderPass == TRINKET_1_SLOT || (renderPass == TRINKET_2_SLOT && player.inventory.armorInventory[TRINKET_1_SLOT] == null);
                modelAccessories.armLeft.visible = renderPass == GLOVES_SLOT;
                modelAccessories.armRight.visible = renderPass == GLOVES_SLOT;
                renderDispatcher.textureManager.loadTexture(path).bind();
                setArmorModel(modelAccessories);
                info.setReturnValue(true);
                return;
            }
        }
        info.setReturnValue(false);
    }


    @Inject(method = "prepareArmor*", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;getItem()Lnet/minecraft/core/item/Item;"), cancellable = true)
    public void setShield(@NotNull Player player, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> info) {
        ItemStack armorStack = player.inventory.armorInventory[renderPass];
        if (armorStack == null || !(armorStack.getItem() instanceof IAccessory)) {
            return;
        }
        Item item = armorStack.getItem();
        if (item instanceof ItemRepulsionShield) {
            String path = String.format("/assets/%s/textures/armor/energyGlow.png", item.namespaceID.namespace());
            renderDispatcher.textureManager.loadTexture(path).bind();
            setArmorModel(shield);
            info.setReturnValue(true);
        }
    }
    
}
