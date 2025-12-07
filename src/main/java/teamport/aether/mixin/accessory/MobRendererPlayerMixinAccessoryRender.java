package teamport.aether.mixin.accessory;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.gamemode.Gamemode;
import org.jspecify.annotations.NonNull;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.helper.GLManager;
import teamport.aether.item.AetherRepulsion;
import teamport.aether.item.accessory.AetherInvisibility;
import teamport.aether.item.accessory.IAccessory;
import teamport.aether.item.accessory.ItemGloves;
import teamport.aether.item.accessory.pendant.ItemPendant;
import teamport.aether.item.accessory.trinket.ItemGoldenFeather;
import teamport.aether.item.accessory.trinket.ItemIronBubble;
import teamport.aether.item.accessory.trinket.ItemRegenStone;
import teamport.aether.item.accessory.trinket.ItemRepulsionShield;

import static teamport.aether.item.accessory.SlotAccessory.*;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRendererPlayer.class, remap = false)
public abstract class MobRendererPlayerMixinAccessoryRender extends MobRenderer<Player> {
    @Shadow
    private ModelBiped modelBipedMain;
    @Shadow
    @Final
    private ModelBiped modelArmor;
    @Shadow
    @Final
    private ModelBiped modelArmorChestplate;
    @Unique
    private final ModelBiped modelAccessories = new ModelBiped(1.1F);
    @Unique
    private final ModelBiped modelHeart = new ModelBiped(1.0F);
    @Unique
    private final ModelBiped modelBubble = new ModelBiped(1.0F);
    @Unique
    private final ModelBiped modelFeather = new ModelBiped(1.0F);
    @Unique
    private final ModelBiped shield = new ModelBiped(1.5F);
    @Unique
    private boolean shield6 = false;

    protected MobRendererPlayerMixinAccessoryRender(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    @Inject(method = "drawFirstPersonHand", at = @At("TAIL"))
    private void callDrawFirstPersonHandAfter(@NonNull Player player, boolean isLeft, CallbackInfo ci) {
        ItemStack itemStack = player.inventory.armorInventory[GLOVES_SLOT];
        if (itemStack != null && itemStack.getItem() instanceof ItemGloves) {
            Item item = itemStack.getItem();
            String path = String.format("/assets/%s/textures/armor/gloves/%s_gloves.png", item.namespaceID.namespace(), ((IAccessory) item).name());
            if (renderDispatcher.textureManager == null) return;
            renderDispatcher.textureManager.loadTexture(path).bind();

            TextureManager textureManager = renderDispatcher.textureManager;
            if (textureManager == null) return;

            int currentTexture = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);

            textureManager.loadTexture(path).bind();

            GL11.glDisable(GL11.GL_CULL_FACE);

            modelArmorChestplate.onGround = 0.0F;
            modelArmorChestplate.isRiding = false;
            modelArmorChestplate.setupAnimation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

            if (isLeft) {
                modelArmorChestplate.armLeft.visible = true;
                modelArmorChestplate.armLeft.render(0.0625F);
            } else {
                modelArmorChestplate.armRight.visible = true;
                modelArmorChestplate.armRight.render(0.0625F);
            }

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture);
        }
    }

    @Definition(id = "spectator", field = "Lnet/minecraft/core/player/gamemode/Gamemode;spectator:Lnet/minecraft/core/player/gamemode/Gamemode;")
    @Expression("spectator")
    @ModifyExpressionValue(method = "prepareArmor(Lnet/minecraft/core/entity/player/Player;IF)Z", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 4))
    private Gamemode renderPlayerOne(Gamemode original, Player entity, int layer, float partialTick) {
        if (((AetherInvisibility) entity).aether$isInvisible()) return entity.getGamemode();
        return original;
    }

    @WrapOperation(method = "prepareArmor(Lnet/minecraft/core/entity/player/Player;IF)Z", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V", ordinal = 1))
    private void renderPlayerTwo(float red, float blue, float green, float alpha, Operation<Void> original, Player entity, int layer, float partialTick) {
        if (entity.getGamemode() == Gamemode.spectator) {
            original.call(red, blue, green, alpha);
            return;
        }
        original.call(red, blue, green, 0.05F);
        GL11.glEnable(GL11.GL_BLEND);
    }

    @Definition(id = "spectator", field = "Lnet/minecraft/core/player/gamemode/Gamemode;spectator:Lnet/minecraft/core/player/gamemode/Gamemode;")
    @Expression("spectator")
    @ModifyExpressionValue(method = "setupScale(Lnet/minecraft/core/entity/player/Player;F)V", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 2))
    private Gamemode renderPlayerThree(Gamemode original, Player entity, float partialTick) {
        if (((AetherInvisibility) entity).aether$isInvisible()) return entity.getGamemode();
        return original;
    }

    @WrapOperation(method = "setupScale(Lnet/minecraft/core/entity/player/Player;F)V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V", ordinal = 1))
    private void renderPlayerFour(float red, float blue, float green, float alpha, Operation<Void> original, Player entity, float partialTick) {
        if (entity.getGamemode() == Gamemode.spectator) {
            original.call(red, blue, green, alpha);
            return;
        }
        original.call(red, blue, green, 0.0F);
        GL11.glEnable(GL11.GL_BLEND);
    }

    @Inject(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/player/Player;DDDFF)V", at = @At("HEAD"))
    private void renderPlayerFive(Tessellator tessellator, Player entity, double x, double y, double z, float yaw, float partialTick, CallbackInfo ci, @Share("alphaTest") LocalFloatRef alphaTest) {
        alphaTest.set(GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF));
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
    }

    @Inject(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/player/Player;DDDFF)V", at = @At("RETURN"))
    private void renderPlayerSix(Tessellator tessellator, Player entity, double x, double y, double z, float yaw, float partialTick, CallbackInfo ci, @Share("alphaTest") LocalFloatRef alphaTest) {
        GL11.glAlphaFunc(GL11.GL_GREATER, alphaTest.get());
    }

    @ModifyArg(method = "prepareArmor*", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;armorItemInSlot(I)Lnet/minecraft/core/item/ItemStack;"))
    private int getArmorItemNotNegative(int i, @Local(argsOnly = true) int renderPass) {
        return (renderPass > 3) ? renderPass : 3 - renderPass;
    }

    @SuppressWarnings({"java:S6541", "java:S1075"})
    @ModifyReturnValue(method = "prepareArmor(Lnet/minecraft/core/entity/player/Player;IF)Z", at = @At("TAIL"))
    private boolean setArmorModel(boolean original, Player entity, int layer, float partialTick) {
        modelAccessories.holdingLarge = shield.holdingLarge = modelFeather.holdingLarge = modelBubble.holdingLarge = modelHeart.holdingLarge = modelBipedMain.holdingLarge;
        modelAccessories.holdingRightHand = shield.holdingRightHand = modelFeather.holdingRightHand = modelBubble.holdingRightHand = modelHeart.holdingRightHand = modelBipedMain.holdingRightHand;
        modelAccessories.holdingLeftHand = shield.holdingLeftHand = modelFeather.holdingLeftHand = modelBubble.holdingLeftHand = modelHeart.holdingLeftHand = modelBipedMain.holdingLeftHand;
        modelAccessories.sneaking = shield.sneaking = modelFeather.sneaking = modelBubble.sneaking = modelHeart.sneaking = modelBipedMain.sneaking;
        modelAccessories.isRiding = shield.isRiding = modelFeather.isRiding = modelBubble.isRiding = modelHeart.isRiding = modelBipedMain.isRiding;
        float swingProgress = this.getSwingProgress(entity, partialTick);
        modelAccessories.onGround = shield.onGround = modelFeather.onGround = modelBubble.onGround = modelHeart.onGround = modelArmor.onGround = modelArmorChestplate.onGround = swingProgress;

        ItemStack armorStack = entity.inventory.armorInventory[layer];
        if (armorStack != null && armorStack.getItem() instanceof IAccessory && layer >= GLOVES_SLOT) {
            Item item = armorStack.getItem();

            if (item instanceof ItemGloves) {
                String path = String.format("/assets/%s/textures/armor/gloves/%s_gloves.png", item.namespaceID.namespace(), ((IAccessory) item).name());
                modelArmorChestplate.holdingRightHand = entity.inventory.getCurrentItem() != null;
                modelArmorChestplate.sneaking = entity.isSneaking();
                modelArmorChestplate.isRiding = entity.isPassenger();
                modelArmorChestplate.armLeft.visible = layer == GLOVES_SLOT;
                modelArmorChestplate.armRight.visible = layer == GLOVES_SLOT;
                renderDispatcher.textureManager.loadTexture(path).bind();
                setArmorModel(modelArmorChestplate);
                return true;
            }
            if ((item instanceof ItemRepulsionShield && (layer == TRINKET_2_SLOT || entity.inventory.armorInventory[TRINKET_2_SLOT] == null)) || this.shield6) {
                this.shield6 = false;
                String path;
                if (((AetherRepulsion) entity).aether$isRepulse()) {
                    path = String.format("/assets/%s/textures/armor/energyGlow.png", item.namespaceID.namespace());
                } else {
                    path = String.format("/assets/%s/textures/armor/energyNotGlow.png", item.namespaceID.namespace());
                }

                renderDispatcher.textureManager.loadTexture(path).bind();
                GLManager.glEnable(GL11.GL_CULL_FACE);
                GLManager.glEnable(GL11.GL_BLEND);
                if (((AetherInvisibility) entity).aether$isInvisible()) {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.25F);
                    GL11.glEnable(GL11.GL_BLEND);
                } else {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                }
                setArmorModel(shield);

                return true;
            }

            ///  redirect the render to next item
            if (item instanceof ItemRepulsionShield && layer == 6) {
                this.shield6 = true;
                ItemStack nextSlot = entity.inventory.armorInventory[layer + 1];
                item = nextSlot.getItem();
                layer += 1;
            }

            ItemStack itemTrinketSlot1 = entity.inventory.armorInventory[TRINKET_1_SLOT];
            ItemStack itemTrinketSlot2 = entity.inventory.armorInventory[TRINKET_2_SLOT];

            if (item instanceof ItemGoldenFeather) {
                String path;

                if (layer == TRINKET_1_SLOT) {
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
                return true;
            }

            if (item instanceof ItemPendant) {
                int variant = 0;
                if (layer == 7 && itemTrinketSlot1 != null && itemTrinketSlot1.getItem() instanceof ItemPendant) {
                    variant = 1;
                }
                String path = String.format("/assets/%s/textures/armor/pendants/%s_pendant_%d.png", item.namespaceID.namespace(), ((IAccessory) item).name(), variant);
                modelAccessories.body.visible = true;
                renderDispatcher.textureManager.loadTexture(path).bind();
                setArmorModel(modelAccessories);
                return true;
            }

            if (item instanceof ItemRegenStone) {
                String path;
                if (layer == TRINKET_1_SLOT) {
                    path = "/assets/aether/textures/armor/trinkets/regen_trinket_right.png";
                } else {
                    path = "/assets/aether/textures/armor/trinkets/regen_trinket_left.png";
                }
                modelHeart.head.visible = true;
                renderDispatcher.textureManager.loadTexture(path).bind();
                setArmorModel(modelHeart);
                return true;
            }

            if (item instanceof ItemIronBubble) {
                boolean isInTrinketSlot1 = itemTrinketSlot1 != null && itemTrinketSlot1.getItem() instanceof ItemIronBubble;
                boolean isInTrinketSlot2 = itemTrinketSlot2 != null && itemTrinketSlot2.getItem() instanceof ItemIronBubble;
                if ((isInTrinketSlot1 && layer == TRINKET_1_SLOT) || (isInTrinketSlot2 && !isInTrinketSlot1 && layer == TRINKET_2_SLOT)) {

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

                    return true;
                }
            }
        }

        return false;
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
