package teamport.aether.mixin.accessory;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.client.render.model.ModelPlayer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
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
import teamport.aether.helper.GLManager;
import teamport.aether.items.accessory.IAccessory;
import teamport.aether.items.accessory.ItemGloves;
import teamport.aether.items.accessory.pendant.ItemPendant;
import teamport.aether.items.accessory.trinket.ItemGoldenFeather;
import teamport.aether.items.accessory.trinket.ItemIronBubble;
import teamport.aether.items.accessory.trinket.ItemRegenStone;
import teamport.aether.items.accessory.trinket.ItemRepulsionShield;

import static teamport.aether.items.accessory.SlotAccessory.*;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRendererPlayer.class, remap = false)
abstract public class MobRendererPlayerMixinAccessoryRender extends MobRenderer<Player> {

    @Shadow
    private ModelBiped modelBipedMain;

    @Shadow
    @Final
    private ModelBiped modelArmor;

    @Shadow
    @Final
    private ModelBiped modelArmorChestplate;

    @Shadow
    public abstract void render(Tessellator tessellator, Player entity, double x, double y, double z, float yaw, float partialTick);

    @Unique
    public final ModelBiped modelAccessories = new ModelBiped(1.1F);

    @Unique
    public final ModelBiped modelHeart = new ModelBiped(1.0F);

    @Unique
    public final ModelBiped modelBubble = new ModelBiped(1.0F);

    @Unique
    public final ModelBiped modelFeather = new ModelBiped(1.0F);

    @Unique
    public final ModelBiped shield = new ModelBiped(1.5F);

    @Unique
    public boolean shield_6 = false;

    public MobRendererPlayerMixinAccessoryRender(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    @Inject(method = "drawFirstPersonHand", at = @At("TAIL"), cancellable = true)
    public void callDrawFirstPersonHandAfter(@NotNull Player player, boolean isLeft, CallbackInfo ci) {
        ItemStack itemStack = player.inventory.armorInventory[GLOVES_SLOT];
        if (itemStack != null && itemStack.getItem() instanceof ItemGloves) {
            Item item = itemStack.getItem();
            String path = String.format("/assets/%s/textures/armor/gloves/%s_gloves.png", item.namespaceID.namespace(), ((IAccessory) item).name());
            if (renderDispatcher.textureManager == null) return;
            renderDispatcher.textureManager.loadTexture(path).bind();

            modelArmorChestplate.onGround = 0.0f;
            modelArmorChestplate.isRiding = false;
            modelArmorChestplate.setupAnimation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);

            if (modelBipedMain instanceof ModelPlayer) {
                if (isLeft) {
                    GL11.glDisable(GL11.GL_CULL_FACE);
                    modelArmorChestplate.armLeft.visible = true;
                    modelArmorChestplate.armLeft.render(0.0625F);
                } else {
                    GL11.glDisable(GL11.GL_CULL_FACE);
                    modelArmorChestplate.armRight.visible = true;
                    modelArmorChestplate.armRight.render(0.0625F);
                }
            }
            modelArmorChestplate.armLeft.visible = false;
            modelArmorChestplate.armRight.visible = false;
            modelArmorChestplate.sneaking = modelBipedMain.sneaking;
            modelArmorChestplate.holdingRightHand = modelBipedMain.holdingRightHand;
            modelArmorChestplate.holdingLeftHand = modelBipedMain.holdingLeftHand;
            modelArmorChestplate.holdingLarge = modelBipedMain.holdingLarge;
        }

        ci.cancel();
    }

    @ModifyArg(method = "prepareArmor*", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;armorItemInSlot(I)Lnet/minecraft/core/item/ItemStack;"))
    public int getArmorItemNotNegative(int i, @Local(argsOnly = true) int renderPass) {
        return (renderPass > 3) ? renderPass : 3 - renderPass;
    }

    @Inject(method = "prepareArmor*", at = @At("TAIL"), cancellable = true)
    public void setArmorModel(@NotNull Player player, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> info) {
        modelAccessories.holdingLarge = shield.holdingLarge = modelFeather.holdingLarge = modelBubble.holdingLarge = modelHeart.holdingLarge = modelBipedMain.holdingLarge;
        modelAccessories.holdingRightHand = shield.holdingRightHand = modelFeather.holdingRightHand = modelBubble.holdingRightHand = modelHeart.holdingRightHand = modelBipedMain.holdingRightHand;
        modelAccessories.holdingLeftHand = shield.holdingLeftHand = modelFeather.holdingLeftHand = modelBubble.holdingLeftHand = modelHeart.holdingLeftHand = modelBipedMain.holdingLeftHand;
        modelAccessories.sneaking = shield.sneaking = modelFeather.sneaking = modelBubble.sneaking = modelHeart.sneaking = modelBipedMain.sneaking;
        modelAccessories.isRiding = shield.isRiding = modelFeather.isRiding = modelBubble.isRiding = modelHeart.isRiding = modelBipedMain.isRiding;
        float swingProgress = this.getSwingProgress(player, partialTick);
        modelAccessories.onGround = shield.onGround = modelFeather.onGround = modelBubble.onGround = modelHeart.onGround = modelArmor.onGround = modelArmorChestplate.onGround = swingProgress;

        ItemStack armorStack = player.inventory.armorInventory[renderPass];
        if (armorStack != null && armorStack.getItem() instanceof IAccessory && renderPass >= GLOVES_SLOT) {
            Item item = armorStack.getItem();

            if (item instanceof ItemGloves) {
                String path = String.format("/assets/%s/textures/armor/gloves/%s_gloves.png", item.namespaceID.namespace(), ((IAccessory) item).name());
                modelArmorChestplate.holdingRightHand = player.inventory.getCurrentItem() != null;
                modelArmorChestplate.sneaking = player.isSneaking();
                modelArmorChestplate.isRiding = player.isPassenger();
                modelArmorChestplate.armLeft.visible = renderPass == GLOVES_SLOT;
                modelArmorChestplate.armRight.visible = renderPass == GLOVES_SLOT;
                renderDispatcher.textureManager.loadTexture(path).bind();
                setArmorModel(modelArmorChestplate);
                info.setReturnValue(true);
                return;
            }
            if ((item instanceof ItemRepulsionShield && (renderPass == TRINKET_2_SLOT || player.inventory.armorInventory[TRINKET_2_SLOT] == null)) || this.shield_6) {
                this.shield_6 = false;
                double velocity = MathHelper.sqrt(player.xd * player.xd + player.zd * player.zd);
                String path;
                if (player.isSneaking() || (player.onGround && velocity < 0.075D)) {
                    path = String.format("/assets/%s/textures/armor/energyGlow.png", item.namespaceID.namespace());
                } else {
                    path = String.format("/assets/%s/textures/armor/energyNotGlow.png", item.namespaceID.namespace());
                }

                renderDispatcher.textureManager.loadTexture(path).bind();
                GLManager.glEnable(GL11.GL_CULL_FACE);
                GLManager.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1, 1, 1, 1);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                setArmorModel(shield);

                info.setReturnValue(true);
                return;
            }

            ///  redirect the render to next item
            if (item instanceof ItemRepulsionShield && renderPass == 6) {
                this.shield_6 = true;
                ItemStack nextSlot = player.inventory.armorInventory[renderPass + 1];
                item = nextSlot.getItem();
            }

            ItemStack ItemTrinket_Slot1 = player.inventory.armorInventory[TRINKET_1_SLOT];
            ItemStack ItemTrinket_Slot2 = player.inventory.armorInventory[TRINKET_2_SLOT];

            if (item instanceof ItemGoldenFeather) {
                String path;

                if (renderPass == TRINKET_1_SLOT) {
                    path = "/assets/aether/textures/armor/trinkets/feather_gold_trinket_helmet.png";
                    setUPFeatherOnHelmet();
                } else {
                    path = "/assets/aether/textures/armor/trinkets/feather_gold_trinket_boots.png";
                    setUpFeathersOnBoots();
                }

                modelFeather.body.visible = false;
                modelFeather.armLeft.visible = false;
                modelFeather.armRight.visible = false;
                renderDispatcher.textureManager.loadTexture(path).bind();
                setArmorModel(modelFeather);
                info.setReturnValue(true);
                return;
            }

            if (item instanceof ItemPendant) {
                int variant = 0;
                if (renderPass == 7 && ItemTrinket_Slot1 != null && ItemTrinket_Slot1.getItem() instanceof ItemPendant) {
                    variant = 1;
                }
                String path = String.format("/assets/%s/textures/armor/pendants/%s_pendant_%d.png", item.namespaceID.namespace(), ((IAccessory) item).name(), variant);
                modelAccessories.body.visible = true;
                renderDispatcher.textureManager.loadTexture(path).bind();
                setArmorModel(modelAccessories);
                info.setReturnValue(true);
                return;
            }

            if (item instanceof ItemRegenStone) {
                String path;
                if (renderPass == TRINKET_1_SLOT) {
                    path = "/assets/aether/textures/armor/trinkets/regen_trinket_right.png";
                } else {
                    path = "/assets/aether/textures/armor/trinkets/regen_trinket_left.png";
                }
                modelHeart.head.visible = true;
                renderDispatcher.textureManager.loadTexture(path).bind();
                setArmorModel(modelHeart);
                info.setReturnValue(true);
                return;
            }

            if (item instanceof ItemIronBubble) {
                boolean isInTrinketSlot1 = ItemTrinket_Slot1 != null && ItemTrinket_Slot1.getItem() instanceof ItemIronBubble;
                boolean isInTrinketSlot2 = ItemTrinket_Slot2 != null && ItemTrinket_Slot2.getItem() instanceof ItemIronBubble;
                if ((isInTrinketSlot1 && renderPass == TRINKET_1_SLOT) || (isInTrinketSlot2 && !isInTrinketSlot1 && renderPass == TRINKET_2_SLOT)) {

                    String path = "/assets/aether/textures/armor/trinkets/bubble_trinket.png";

                    modelBubble.head.visible = true;
                    modelBubble.body.visible = false;
                    modelBubble.armLeft.visible = false;
                    modelBubble.armRight.visible = false;
                    modelBubble.legLeft.visible = false;
                    modelBubble.legRight.visible = false;

                    renderDispatcher.textureManager.loadTexture(path).bind();
                    GLManager.glEnable(GL11.GL_CULL_FACE);
                    GLManager.glEnable(GL11.GL_BLEND);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    setArmorModel(modelBubble);

                    info.setReturnValue(true);
                    return;
                }
            }
        }

        info.setReturnValue(false);
    }

    @Unique
    private void setUPFeatherOnHelmet() {
        modelFeather.head.visible = true;
        modelFeather.legLeft.visible = false;
        modelFeather.legRight.visible = false;
        modelFeather.head.addBox(-4.0F, -12.0F, -4.0F, 8, 12, 12, 1.1f);
    }

    @Unique
    private void setUpFeathersOnBoots() {
        modelFeather.head.visible = false;
        modelFeather.legLeft.visible = true;
        modelFeather.legRight.visible = true;
        modelFeather.legLeft.addBox(-2.0F, 2.0F, -3.0F, 4, 12, 8, 1.1f);
        modelFeather.legRight.addBox(-2.0F, 2.0F, -3.0F, 4, 12, 8, 1.1f);
    }
}
