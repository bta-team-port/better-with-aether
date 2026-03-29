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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.gui.container.ScreenInventoryCreative;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
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
import teamport.aether.AetherMod;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.helper.GLManager;
import teamport.aether.helper.MixinHelper;
import teamport.aether.item.AetherItemTags;
import teamport.aether.item.AetherRepulsion;
import teamport.aether.item.accessory.IAccessory;
import teamport.aether.item.accessory.ItemGloves;
import teamport.aether.item.accessory.pendant.ItemPendant;
import teamport.aether.item.accessory.trinket.ItemGoldenFeather;
import teamport.aether.item.accessory.trinket.ItemIronBubble;
import teamport.aether.item.accessory.trinket.ItemRegenStone;
import teamport.aether.item.accessory.trinket.ItemRepulsionShield;

import static teamport.aether.AetherMod.MOD_ID;
import static teamport.aether.item.accessory.SlotAccessory.*;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRendererPlayer.class)
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
        ItemStack held = player.inventory.getCurrentItem();

        if ((held == null || !held.getItem().equals(Items.MAP)) && isLeft) {
            return;
        }

        ItemStack glovesStack = player.inventory.armorInventory[GLOVES_SLOT];
        if (glovesStack == null || !(glovesStack.getItem() instanceof ItemGloves)) {
            return;
        }

        Item item = glovesStack.getItem();
        String path = String.format("/assets/%s/textures/armor/gloves/%s_gloves.png", item.namespaceID.namespace(), ((IAccessory) item).name()
        );

        TextureManager textureManager = this.renderDispatcher.textureManager;
        if (textureManager == null) {
            return;
        }

        int previousTexture = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);

        textureManager.loadTexture(path).bind();

        GL11.glDisable(GL11.GL_CULL_FACE);

        modelArmorChestplate.onGround = 0.0F;
        modelArmorChestplate.isRiding = false;
        modelArmorChestplate.sneaking = false;

        modelArmorChestplate.armLeft.visible = false;
        modelArmorChestplate.armRight.visible = false;

        modelArmorChestplate.setupAnimation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        if (isLeft) {
            modelArmorChestplate.armLeft.visible = true;
            modelArmorChestplate.armLeft.render(0.0625F);
        } else {
            modelArmorChestplate.armRight.visible = true;
            modelArmorChestplate.armRight.render(0.0625F);
        }

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, previousTexture);
    }

    @Definition(id = "spectator", field = "Lnet/minecraft/core/player/gamemode/Gamemode;spectator:Lnet/minecraft/core/player/gamemode/Gamemode;")
    @Expression("spectator")
    @ModifyExpressionValue(method = "prepareArmor(Lnet/minecraft/core/entity/player/Player;IF)Z", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 4))
    private Gamemode spoofPrepareArmorCheckWhenInvisible(Gamemode original, Player entity, int layer, float partialTick) {
        if (PlayerUtil.isInvisible(entity)) {
            return entity.getGamemode();
        }
        return original;
    }

    @SuppressWarnings("java:S107")
    @WrapOperation(method = "prepareArmor(Lnet/minecraft/core/entity/player/Player;IF)Z", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V", ordinal = 1))
    private void renderPrepareArmorPlayerInvisible(float red, float blue, float green, float alpha, Operation<Void> original, Player entity, int layer, float partialTick) {
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
    private Gamemode spoofScaleCheckWhenInvisible(Gamemode original, Player entity, float partialTick) {
        if (PlayerUtil.isInvisible(entity)) {
            return entity.getGamemode();
        }
        return original;
    }

    @WrapOperation(method = "setupScale(Lnet/minecraft/core/entity/player/Player;F)V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V", ordinal = 1))
    private void renderScalePlayerInvisible(float red, float blue, float green, float alpha, Operation<Void> original, Player entity, float partialTick) {
        if (entity.getGamemode() == Gamemode.spectator) {
            original.call(red, blue, green, alpha);
            return;
        }
        original.call(red, blue, green, 0.0F);
        GL11.glEnable(GL11.GL_BLEND);
    }

    @SuppressWarnings("java:S107")
    @Inject(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/player/Player;DDDFF)V", at = @At("HEAD"))
    private void pushGL11AlphaTestRef(Tessellator tessellator, Player entity, double x, double y, double z, float yaw, float partialTick, CallbackInfo ci, @Share("alphaTest") LocalFloatRef alphaTest) {
        alphaTest.set(GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF));
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
    }

    @SuppressWarnings("java:S107")
    @Inject(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/player/Player;DDDFF)V", at = @At("RETURN"))
    private void popGL11AlphaTestRef(Tessellator tessellator, Player entity, double x, double y, double z, float yaw, float partialTick, CallbackInfo ci, @Share("alphaTest") LocalFloatRef alphaTest) {
        GL11.glAlphaFunc(GL11.GL_GREATER, alphaTest.get());
    }

    @ModifyArg(method = "prepareArmor*", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;armorItemInSlot(I)Lnet/minecraft/core/item/ItemStack;"))
    private int getArmorItemNotNegative(int i, @Local(argsOnly = true) int renderPass) {
        return (renderPass > 3) ? renderPass : 3 - renderPass;
    }

    @SuppressWarnings("java:S107")
    @Inject(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/player/Player;DDDFF)V", at = @At("TAIL"))
    public void renderBunny(Tessellator tessellator, Player entity, double x, double y, double z, float yaw, float partialTick, CallbackInfo ci) {
        if (entity != Minecraft.getMinecraft().thePlayer) return;

        Screen currScreen = Minecraft.getMinecraft().currentScreen;
        final boolean isInInventory = currScreen instanceof ScreenInventory || currScreen instanceof ScreenInventoryCreative;

        if (entity.passenger instanceof MobAerbunny && isInInventory) {
            MobAerbunny bunny = (MobAerbunny) entity.passenger;
            boolean hasHelmet = entity.inventory.armorInventory[3] != null;

            GL11.glPushMatrix();
            GL11.glColor4f(1F, 1F, 1F, 1F);
            GL11.glScalef(0.80F, 0.80F, 0.80F);
            if (hasHelmet) {
                GL11.glTranslatef(0, 0.1875F, 0);
            } else {
                GL11.glTranslatef(0, 0.0625F, 0);
            }
            EntityRenderDispatcher.instance.renderEntityWithPosYaw(tessellator, bunny, x, y + 0.25F, z, yaw, partialTick);
            GL11.glPopMatrix();
        }
    }


    @SuppressWarnings({"java:S6541", "java:S1075", "java:S3776"})
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
        if (armorStack == null) {
            return false;
        }

        if ((armorStack.getItem() instanceof IAccessory || armorStack.getItem().hasTag(AetherItemTags.TRINKET)) && layer >= GLOVES_SLOT) {
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
                    path = "/assets/aether/textures/armor/energyGlow.png";
                } else {
                    path = "/assets/aether/textures/armor/energyNotGlow.png";
                }

                renderDispatcher.textureManager.loadTexture(path).bind();
                GLManager.glEnable(GL11.GL_CULL_FACE);
                GLManager.glEnable(GL11.GL_BLEND);
                if (PlayerUtil.isInvisible(entity)) {
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
                    setUpFeatherOnHelmet();
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

            String textureKey = MixinHelper.TRINKET_TEXTURES.get(item);
            if (textureKey != null) {
                if (layer != TRINKET_1_SLOT && layer != TRINKET_2_SLOT) {
                    return false;
                }

                boolean leftSlot = (layer == TRINKET_1_SLOT);
                String path = String.format("/assets/%s/textures/armor/trinkets/%s.png", AetherMod.MOD_ID, textureKey);

                modelAccessories.head.visible = false;
                modelAccessories.body.visible = false;
                modelAccessories.armLeft.visible = false;
                modelAccessories.armRight.visible = false;

                modelAccessories.legLeft.visible  = leftSlot;
                modelAccessories.legRight.visible = !leftSlot;

                renderDispatcher.textureManager.loadTexture(path).bind();
                setArmorModel(modelAccessories);
                return true;
            }

            if (item instanceof ItemPendant) {
                int variant = 0;
                if (layer == TRINKET_2_SLOT && itemTrinketSlot1 != null && itemTrinketSlot1.getItem() instanceof ItemPendant) {
                    variant = 1;
                }
                String path = String.format("/assets/%s/textures/armor/pendants/%s_pendant_%d.png", item.namespaceID.namespace(), ((IAccessory) item).name(), variant);
                modelAccessories.body.visible = true;
                renderDispatcher.textureManager.loadTexture(path).bind();
                setArmorModel(modelAccessories);
                return true;
            }

            if (item instanceof ItemRegenStone) {
                String variant = "right";
                if (layer == TRINKET_2_SLOT) {
                    variant = "left";
                }
                String path = String.format("/assets/%s/textures/armor/trinkets/%s_%s.png", item.namespaceID.namespace(), ((IAccessory) item).name(), variant);
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
                    if (PlayerUtil.isInvisible(entity)) {
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.25F);
                        GL11.glEnable(GL11.GL_BLEND);
                    } else {
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    }
                    setArmorModel(modelBubble);

                    return true;
                }
            }
        }
        return false;
    }

    @Unique
    private void setUpFeatherOnHelmet() {
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
