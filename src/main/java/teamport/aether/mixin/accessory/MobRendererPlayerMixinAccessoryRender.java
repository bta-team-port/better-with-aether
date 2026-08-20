package teamport.aether.mixin.accessory;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
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
import org.jetbrains.annotations.NotNull;
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
        ItemStack glovesStack = this.getAccessory(player, GLOVES_SLOT);
        if (glovesStack == null || !(glovesStack.getItem() instanceof ItemGloves)) {
            return;
        }
        Item item = glovesStack.getItem();
        String path = String.format("/assets/%s/textures/armor/gloves/%s_gloves.png", item.namespaceID.namespace(), ((IAccessory) item).name());
        TextureManager textureManager = this.renderDispatcher.textureManager;
        int previousTexture = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
        GLRenderer.pushFrame();
        try {
            textureManager.loadTexture(path).bind();
            GLRenderer.disableState(State.CULL_FACE);
            StaticEntityModel modelArmorChestplate = this.getModel("aether.accessory.gloves");
            modelArmorChestplate.resetBones();
            this.setVisible(modelArmorChestplate, false, false, false, false, false);
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
            if (!(entity.passenger instanceof MobAerbunny bunny) || !isInInventory) {
                return;
            }
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

    @WrapOperation(method = "getAndSetupModelForLayer(Lnet/minecraft/core/entity/player/Player;FFI)Lorg/useless/dragonfly/models/entity/StaticEntityModel;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/MobRendererPlayer;setupAnimations(Lnet/minecraft/core/entity/player/Player;Lorg/useless/dragonfly/models/entity/StaticEntityModel;FI)Lorg/useless/dragonfly/models/entity/StaticEntityModel;")
    )
    private StaticEntityModel prePrepareAnimations(
        MobRendererPlayer mobRendererPlayer,
        Player player,
        StaticEntityModel model,
        float partialTick, int layer,
        Operation<StaticEntityModel> original
    ){
        if(layer == 0 && PlayerUtil.isInvisible(player)){
            return null;
        }
        return original.call(mobRendererPlayer, player, model, partialTick, layer);
    }


    @WrapMethod(
        method = "getAndSetupModelForLayer(Lnet/minecraft/core/entity/player/Player;FFI)Lorg/useless/dragonfly/models/entity/StaticEntityModel;"
    )
    private StaticEntityModel getAccessoryModel(
        Player entity,
        float brightness,
        float partialTick,
        int layer,
        Operation<StaticEntityModel> original
    ) {
        if (layer <= 4) {
            return original.call(entity, brightness, partialTick, layer);
        }
        if(PlayerUtil.isInvisible(entity)){
            GLRenderer.enableState(State.BLEND);
            GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 0.15F);
        }
        int slot = layer - 1;
        ItemStack armorStack = this.getAccessory(entity, slot);
        if (armorStack == null
            || !(armorStack.getItem() instanceof IAccessory)
            && !armorStack.getItem().hasTag(AetherItemTags.TRINKET)
        ) {
            return null;
        }
        Item item = armorStack.getItem();
        if (item instanceof ItemGloves) {
            return this.setUpGloves(entity, partialTick, layer, item, slot);
        }
        if ((item instanceof ItemRepulsionShield && (slot == TRINKET_2_SLOT || this.getAccessory(entity, TRINKET_2_SLOT) == null)) || this.shield6) {
            return this.setUpShield(entity, partialTick, layer);
        }
        ///  redirect the render to next item
        if (item instanceof ItemRepulsionShield && slot == TRINKET_1_SLOT) {
            ItemStack nextSlot = this.getAccessory(entity, slot + 1);
            if (nextSlot == null) {
                return null;
            }
            this.shield6 = true;
            item = nextSlot.getItem();
            slot += 1;
        }
        ItemStack itemTrinketSlot1 = this.getAccessory(entity, TRINKET_1_SLOT);
        ItemStack itemTrinketSlot2 = this.getAccessory(entity, TRINKET_2_SLOT);
        if (item instanceof ItemGoldenFeather) {
            return this.setUpGoldenFeather(entity, partialTick, layer, slot);
        }
        String textureKey = MixinHelper.TRINKET_TEXTURES.get(item);
        if (textureKey != null) {
            return setUpTrinkets(entity, partialTick, layer, slot, textureKey);
        }
        if (item instanceof ItemPendant) {
            return setUpPendant(entity, partialTick, layer, slot, itemTrinketSlot1, item);
        }
        if (item instanceof ItemRegenStone) {
            return setUpRegenStone(entity, partialTick, layer, slot);
        }
        if (item instanceof ItemIronBubble) {
            return setUpIronBubble(entity, partialTick, layer, itemTrinketSlot1, itemTrinketSlot2, slot);
        }
        return null;
    }

    private @Nullable StaticEntityModel setUpTrinkets(Player entity, float partialTick, int layer, int slot, String textureKey) {
        if (slot != TRINKET_1_SLOT && slot != TRINKET_2_SLOT) {
            return null;
        }
        boolean leftSlot = (slot == TRINKET_1_SLOT);
        StaticEntityModel modelAccessories = this.setupAccessoryModel("aether.accessory.base", entity, partialTick, layer);
        String path = String.format("/assets/aether/textures/armor/trinkets/%s.png", textureKey);
        this.setVisible(modelAccessories, false, false, false, !leftSlot, leftSlot);
        renderDispatcher.textureManager.loadTexture(path).bind();
        return modelAccessories;
    }

    @Unique
    private @Nullable StaticEntityModel setUpGloves(Player entity, float partialTick, int layer, Item item, int slot) {
        StaticEntityModel modelArmorChestplate = this.setupAccessoryModel("aether.accessory.gloves", entity, partialTick, layer);
        String path = String.format("/assets/%s/textures/armor/gloves/%s_gloves.png", item.namespaceID.namespace(), ((IAccessory) item).name());
        this.setVisible(modelArmorChestplate, false, false, slot == GLOVES_SLOT, false, false);
        renderDispatcher.textureManager.loadTexture(path).bind();
        return modelArmorChestplate;
    }

    @Unique
    private @Nullable StaticEntityModel setUpShield(Player entity, float partialTick, int layer) {
        this.shield6 = false;
        StaticEntityModel shield = this.setupAccessoryModel("aether.accessory.shield", entity, partialTick, layer);
        StringBuilder path = new StringBuilder("/assets/aether/textures/armor/");
        if (((AetherRepulsion) entity).aether$isRepulse()) {
            path.append("energyGlow.png");
        } else {
            path.append("energyNotGlow.png");
        }
        this.renderDispatcher.textureManager.loadTexture(path.toString()).bind();
        GLRenderer.enableState(State.CULL_FACE);
        GLRenderer.enableState(State.BLEND);
        if (PlayerUtil.isInvisible(entity)) {
            GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 0.25F);
            GLRenderer.enableState(State.BLEND);
        } else {
            GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
        }
        return shield;
    }

    @Unique
    private @Nullable StaticEntityModel setUpGoldenFeather(Player entity, float partialTick, int layer, int slot) {
        StaticEntityModel modelFeather = this.setupAccessoryModel("aether.accessory.feather", entity, partialTick, layer);
        StaticEntityModel modelFeatherPos = this.getModel("aether.accessory.feather");
        if(modelFeather == null || modelFeatherPos == null){
            return null;
        }
        StringBuilder path = new StringBuilder("/assets/aether/textures/armor/trinkets/");
        modelFeatherPos.getTransform("chest").visible = false;
        modelFeatherPos.getTransform("rightArm").visible = false;
        modelFeatherPos.getTransform("leftArm").visible = false;
        modelFeatherPos.getTransform("rightLeg").visible = false;
        if (slot == TRINKET_1_SLOT) {
            path.append("feather_gold_trinket_helmet.png");
            modelFeatherPos.getTransform("head").visible = true;
            modelFeatherPos.getTransform("leftLeg").visible = false;
        } else {
            path.append("feather_gold_trinket_boots.png");
            modelFeatherPos.getTransform("head").visible = false;
            modelFeatherPos.getTransform("leftLeg").visible = true;
            modelFeatherPos.getTransform("rightLeg").visible = true;
        }
        modelFeather.getTransform("chest").visible = false;
        modelFeather.getTransform("leftArm").visible = false;
        modelFeather.getTransform("rightArm").visible = false;
        renderDispatcher.textureManager.loadTexture(path.toString()).bind();
        return modelFeather;
    }

    @Unique
    private @Nullable StaticEntityModel setUpPendant(Player entity, float partialTick, int layer, int slot, ItemStack itemTrinketSlot1, Item item) {
        StaticEntityModel modelAccessories = this.setupAccessoryModel("aether.accessory.base", entity, partialTick, layer);
        int variant = 0;
        if (slot == TRINKET_2_SLOT && itemTrinketSlot1 != null && itemTrinketSlot1.getItem() instanceof ItemPendant) {
            variant = 1;
        }
        String path = String.format("/assets/%s/textures/armor/pendants/%s_pendant_%d.png", item.namespaceID.namespace(), ((IAccessory) item).name(), variant);
        this.setVisible(modelAccessories, false, true, false, false, false);
        renderDispatcher.textureManager.loadTexture(path).bind();
        return modelAccessories;
    }

    @Unique
    private @Nullable StaticEntityModel setUpRegenStone(Player entity, float partialTick, int layer, int slot) {
        StaticEntityModel modelHeart = this.setupAccessoryModel("aether.accessory.heart", entity, partialTick, layer);
        StringBuilder path = new StringBuilder("/assets/aether/textures/armor/trinkets/");
        if (slot == TRINKET_1_SLOT) {
            path.append("regen_trinket_left.png");
        } else {
            path.append("regen_trinket_right.png");
        }
        this.setVisible(modelHeart, true, false, false, false, false);
        renderDispatcher.textureManager.loadTexture(path.toString()).bind();
        return modelHeart;
    }

    @Unique
    private @Nullable StaticEntityModel setUpIronBubble(Player entity, float partialTick, int layer, ItemStack itemTrinketSlot1, ItemStack itemTrinketSlot2, int slot) {
        boolean isInTrinketSlot1 = itemTrinketSlot1 != null && itemTrinketSlot1.getItem() instanceof ItemIronBubble;
        boolean isInTrinketSlot2 = itemTrinketSlot2 != null && itemTrinketSlot2.getItem() instanceof ItemIronBubble;
        if ((!isInTrinketSlot1 || slot != TRINKET_1_SLOT) && (!isInTrinketSlot2 || isInTrinketSlot1 || slot != TRINKET_2_SLOT)) {
            return null;
        }
        StaticEntityModel modelBubble = this.setupAccessoryModel("aether.accessory.bubble", entity, partialTick, layer);
        String path = "/assets/aether/textures/armor/trinkets/bubble_trinket.png";
        this.setVisible(modelBubble, true, false, false, false, false);
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
        return modelBubble;
    }

    @Unique
    private ItemStack getAccessory(@NonNull Player player, int logicalSlot) {
        return ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory()[logicalSlot - GLOVES_SLOT];
    }

    @Unique
    private StaticEntityModel setupAccessoryModel(String name, Player entity, float partialTick, int layer) {
        return this.setupAnimations(entity, this.getModel(name), partialTick, layer);
    }

    @Unique
    private void setVisible(StaticEntityModel model, boolean head, boolean chest, boolean arms, boolean rightLeg, boolean leftLeg) {
        if(model == null){
            return;
        }
        model.getTransform("head").visible = head;
        model.getTransform("chest").visible = chest;
        model.getTransform("rightArm").visible = arms;
        model.getTransform("leftArm").visible = arms;
        model.getTransform("rightLeg").visible = rightLeg;
        model.getTransform("leftLeg").visible = leftLeg;
    }

}
