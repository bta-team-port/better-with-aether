package teamport.aether.mixin.accessory;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.gui.container.ScreenInventoryCreative;
import net.minecraft.client.render.EntityRendererDispatcher;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;
import teamport.aether.entity.player.PlayerUtil;
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

import static teamport.aether.item.accessory.SlotAccessory.*;

@Environment(EnvType.CLIENT)
@Mixin(MobRendererPlayer.class)
public abstract class MobRendererPlayerMixinAccessoryRender extends MobRenderer<Player> {


    @Shadow
    protected abstract @Nullable StaticEntityModel setupAnimations(@NonNull Player player, @Nullable StaticEntityModel model, float partialTick, int layer);

    @Unique
    private boolean shield6 = false;
    @Unique
    private float better_with_aether$partialTick;

    protected MobRendererPlayerMixinAccessoryRender(float shadowSize) {
        super(shadowSize);
    }

    @Inject(method = "drawFirstPersonHand(Lnet/minecraft/client/render/tessellator/TessellatorGeneral;Lnet/minecraft/core/entity/player/Player;Z)V", at = @At("TAIL"))
    private void callDrawFirstPersonHandAfter(TessellatorGeneral tessellator, @NonNull Player player, boolean isLeft, CallbackInfo ci) {
        ItemStack held = player.inventory.getCurrentItem();

        if ((held == null || !held.getItem().equals(Items.MAP)) && isLeft) {
            return;
        }

        ItemStack glovesStack = this.better_with_aether$getAccessory(player, GLOVES_SLOT);
        if (glovesStack == null || !(glovesStack.getItem() instanceof ItemGloves)) {
            return;
        }

        Item item = glovesStack.getItem();
        String path = String.format("/assets/%s/textures/armor/gloves/%s_gloves.png", item.namespaceID.namespace(), ((IAccessory) item).name()
        );

        TextureManager textureManager = this.renderDispatcher.textureManager;

        int previousTexture = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
        GLRenderer.pushFrame();
        try {
            textureManager.loadTexture(path).bind();
            GLRenderer.disableState(State.CULL_FACE);

            StaticEntityModel modelArmorChestplate = this.getModel("aether.accessory.gloves");
            modelArmorChestplate.resetBones();
            this.better_with_aether$setVisible(modelArmorChestplate, false, false, false, false, false);

            if (isLeft) {
                modelArmorChestplate.getTransform("leftArm").visible = true;
            } else {
                modelArmorChestplate.getTransform("rightArm").visible = true;
            }
            modelArmorChestplate.render();
        } finally {
            GLRenderer.popFrame();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, previousTexture);
        }
    }

    @Inject(method = "getAndSetupModelForLayer(Lnet/minecraft/core/entity/player/Player;FFI)Lorg/useless/dragonfly/models/entity/StaticEntityModel;", at = @At("HEAD"))
    private void capturePartialTick(Player entity, float brightness, float partialTick, int layer, CallbackInfoReturnable<StaticEntityModel> cir) {
        if (layer == 0) {
            this.better_with_aether$partialTick = partialTick;
        }
    }

    @Inject(method = "renderSpecials(Lnet/minecraft/client/render/tessellator/TessellatorGeneral;Lnet/minecraft/core/entity/player/Player;DDD)V", at = @At("TAIL"))
    @SuppressWarnings("java:S107")
    public void renderBunny(TessellatorGeneral tessellator, Player entity, double x, double y, double z, CallbackInfo ci) {
        if (entity == Minecraft.getMinecraft().thePlayer) {
            Screen currScreen = Minecraft.getMinecraft().currentScreen;
            final boolean isInInventory = currScreen instanceof ScreenInventory || currScreen instanceof ScreenInventoryCreative;

            if (entity.passenger instanceof MobAerbunny bunny && isInInventory) {
                boolean hasHelmet = entity.inventory.armorInventory[3] != null;

                GLRenderer.pushFrame();
                try {
                    GLRenderer.setColor4f(1F, 1F, 1F, 1F);
                    GLRenderer.modelM4f().scale(0.80F, 0.80F, 0.80F);
                    if (hasHelmet) {
                        GLRenderer.modelM4f().translate(0, 0.1875F, 0);
                    } else {
                        GLRenderer.modelM4f().translate(0, 0.0625F, 0);
                    }
                    float renderYaw = entity.yRotO + (entity.yRot - entity.yRotO) * this.better_with_aether$partialTick;
                    EntityRendererDispatcher.instance.renderEntityWithPosYaw(tessellator, bunny, x, y + 0.25F, z, renderYaw, this.better_with_aether$partialTick);
                } finally {
                    GLRenderer.popFrame();
                }
            }
        }
    }


    @Inject(method = "getAndSetupModelForLayer(Lnet/minecraft/core/entity/player/Player;FFI)Lorg/useless/dragonfly/models/entity/StaticEntityModel;", at = @At("HEAD"), cancellable = true)
    private void getAccessoryModel(Player entity, float brightness, float partialTick, int layer, CallbackInfoReturnable<StaticEntityModel> cir) {
        if (layer <= 4) {
            return;
        }

        int slot = layer - 1;
        ItemStack armorStack = this.better_with_aether$getAccessory(entity, slot);
        if (armorStack == null) {
            cir.setReturnValue(null);
            return;
        }

        if (armorStack.getItem() instanceof IAccessory || armorStack.getItem().hasTag(AetherItemTags.TRINKET)) {
            Item item = armorStack.getItem();

            if (item instanceof ItemGloves) {
                StaticEntityModel modelArmorChestplate = this.better_with_aether$setupAccessoryModel("aether.accessory.gloves", entity, partialTick, layer);
                String path = String.format("/assets/%s/textures/armor/gloves/%s_gloves.png", item.namespaceID.namespace(), ((IAccessory) item).name());
                this.better_with_aether$setVisible(modelArmorChestplate, false, false, slot == GLOVES_SLOT, false, false);
                renderDispatcher.textureManager.loadTexture(path).bind();
                cir.setReturnValue(modelArmorChestplate);
                return;
            }
            if ((item instanceof ItemRepulsionShield && (slot == TRINKET_2_SLOT || this.better_with_aether$getAccessory(entity, TRINKET_2_SLOT) == null)) || this.shield6) {
                StaticEntityModel shield = this.better_with_aether$setupAccessoryModel("aether.accessory.shield", entity, partialTick, layer);
                this.shield6 = false;
                String path;
                if (((AetherRepulsion) entity).aether$isRepulse()) {
                    path = "/assets/aether/textures/armor/energyGlow.png";
                } else {
                    path = "/assets/aether/textures/armor/energyNotGlow.png";
                }

                renderDispatcher.textureManager.loadTexture(path).bind();
                GLRenderer.enableState(State.CULL_FACE);
                GLRenderer.enableState(State.BLEND);
                if (PlayerUtil.isInvisible(entity)) {
                    GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 0.25F);
                    GLRenderer.enableState(State.BLEND);
                } else {
                    GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
                }
                cir.setReturnValue(shield);
                return;
            }

            ///  redirect the render to next item
            if (item instanceof ItemRepulsionShield && slot == TRINKET_1_SLOT) {
                ItemStack nextSlot = this.better_with_aether$getAccessory(entity, slot + 1);
                if (nextSlot == null) {
                    cir.setReturnValue(null);
                    return;
                }
                this.shield6 = true;
                item = nextSlot.getItem();
                slot += 1;
            }

            ItemStack itemTrinketSlot1 = this.better_with_aether$getAccessory(entity, TRINKET_1_SLOT);
            ItemStack itemTrinketSlot2 = this.better_with_aether$getAccessory(entity, TRINKET_2_SLOT);

            if (item instanceof ItemGoldenFeather) {
                StaticEntityModel modelFeather = this.better_with_aether$setupAccessoryModel("aether.accessory.feather", entity, partialTick, layer);
                String path;

                if (slot == TRINKET_1_SLOT) {
                    path = "/assets/aether/textures/armor/trinkets/feather_gold_trinket_helmet.png";
                    setUpFeatherOnHelmet();
                } else {
                    path = "/assets/aether/textures/armor/trinkets/feather_gold_trinket_boots.png";
                    setUpFeathersOnBoots();
                }

                modelFeather.getTransform("chest").visible = false;
                modelFeather.getTransform("leftArm").visible = false;
                modelFeather.getTransform("rightArm").visible = false;
                renderDispatcher.textureManager.loadTexture(path).bind();
                cir.setReturnValue(modelFeather);
                return;
            }

            String textureKey = MixinHelper.TRINKET_TEXTURES.get(item);
            if (textureKey != null) {
                if (slot != TRINKET_1_SLOT && slot != TRINKET_2_SLOT) {
                    cir.setReturnValue(null);
                    return;
                }

                boolean leftSlot = (slot == TRINKET_1_SLOT);
                StaticEntityModel modelAccessories = this.better_with_aether$setupAccessoryModel("aether.accessory.base", entity, partialTick, layer);

                String path = "/assets/aether/textures/armor/trinkets/" + textureKey + ".png";

                this.better_with_aether$setVisible(modelAccessories, false, false, false, !leftSlot, leftSlot);

                renderDispatcher.textureManager.loadTexture(path).bind();
                cir.setReturnValue(modelAccessories);
                return;
            }

            if (item instanceof ItemPendant) {
                StaticEntityModel modelAccessories = this.better_with_aether$setupAccessoryModel("aether.accessory.base", entity, partialTick, layer);
                int variant = 0;
                if (slot == TRINKET_2_SLOT && itemTrinketSlot1 != null && itemTrinketSlot1.getItem() instanceof ItemPendant) {
                    variant = 1;
                }
                String path = String.format("/assets/%s/textures/armor/pendants/%s_pendant_%d.png", item.namespaceID.namespace(), ((IAccessory) item).name(), variant);
                this.better_with_aether$setVisible(modelAccessories, false, true, false, false, false);
                renderDispatcher.textureManager.loadTexture(path).bind();
                cir.setReturnValue(modelAccessories);
                return;
            }

            if (item instanceof ItemRegenStone) {
                StaticEntityModel modelHeart = this.better_with_aether$setupAccessoryModel("aether.accessory.heart", entity, partialTick, layer);
                String path;
                if (slot == TRINKET_1_SLOT) {
                    path = "/assets/aether/textures/armor/trinkets/regen_trinket_right.png";
                } else {
                    path = "/assets/aether/textures/armor/trinkets/regen_trinket_left.png";
                }
                this.better_with_aether$setVisible(modelHeart, true, false, false, false, false);
                renderDispatcher.textureManager.loadTexture(path).bind();
                cir.setReturnValue(modelHeart);
                return;
            }

            if (item instanceof ItemIronBubble) {
                boolean isInTrinketSlot1 = itemTrinketSlot1 != null && itemTrinketSlot1.getItem() instanceof ItemIronBubble;
                boolean isInTrinketSlot2 = itemTrinketSlot2 != null && itemTrinketSlot2.getItem() instanceof ItemIronBubble;
                if ((isInTrinketSlot1 && slot == TRINKET_1_SLOT) || (isInTrinketSlot2 && !isInTrinketSlot1 && slot == TRINKET_2_SLOT)) {
                    StaticEntityModel modelBubble = this.better_with_aether$setupAccessoryModel("aether.accessory.bubble", entity, partialTick, layer);

                    String path = "/assets/aether/textures/armor/trinkets/bubble_trinket.png";

                    this.better_with_aether$setVisible(modelBubble, true, false, false, false, false);

                    renderDispatcher.textureManager.loadTexture(path).bind();
                    GLRenderer.enableState(State.CULL_FACE);
                    GLRenderer.enableState(State.BLEND);
                    if (PlayerUtil.isInvisible(entity)) {
                        GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 0.25F);
                        GLRenderer.enableState(State.BLEND);
                    } else {
                        GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 0.5F);
                        GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
                    }
                    cir.setReturnValue(modelBubble);
                    return;
                }
            }
        }
        cir.setReturnValue(null);
    }

    @Unique
    private ItemStack better_with_aether$getAccessory(@NonNull Player player, int logicalSlot) {
        return ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory()[logicalSlot - GLOVES_SLOT];
    }

    @Unique
    private StaticEntityModel better_with_aether$setupAccessoryModel(String name, Player entity, float partialTick, int layer) {
        return this.setupAnimations(entity, this.getModel(name), partialTick, layer);
    }

    @Unique
    private void better_with_aether$setVisible(@NonNull StaticEntityModel model, boolean head, boolean chest, boolean arms, boolean rightLeg, boolean leftLeg) {
        model.getTransform("head").visible = head;
        model.getTransform("chest").visible = chest;
        model.getTransform("rightArm").visible = arms;
        model.getTransform("leftArm").visible = arms;
        model.getTransform("rightLeg").visible = rightLeg;
        model.getTransform("leftLeg").visible = leftLeg;
    }

    @Unique
    private void setUpFeatherOnHelmet() {
        StaticEntityModel modelFeather = this.getModel("aether.accessory.feather");
        modelFeather.getTransform("head").visible = true;
        modelFeather.getTransform("leftLeg").visible = false;
        modelFeather.getTransform("rightLeg").visible = false;
    }

    @Unique
    private void setUpFeathersOnBoots() {
        StaticEntityModel modelFeather = this.getModel("aether.accessory.feather");
        modelFeather.getTransform("head").visible = false;
        modelFeather.getTransform("leftLeg").visible = true;
        modelFeather.getTransform("rightLeg").visible = true;
    }
}
