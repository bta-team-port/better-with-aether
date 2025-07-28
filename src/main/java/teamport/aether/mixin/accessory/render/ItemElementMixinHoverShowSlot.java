package teamport.aether.mixin.accessory.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ItemElement;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.gui.container.ScreenInventoryCreative;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.AetherItemTags;
import teamport.aether.items.accessory.*;

import javax.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static teamport.aether.AetherMod.ARMOR_START_INDEX;
import static teamport.aether.items.accessory.SlotAccessory.*;

@Mixin(value = ItemElement.class, remap = false)
public class ItemElementMixinHoverShowSlot {

    @Unique private Slot slot;
    @Unique private byte tick = 0;
    @Unique private Random random = new Random();

    @Unique private static final Map<Class<? extends Item>, String> CLASS_OUTLINE_TEXTURES = new HashMap<>();
    @Unique private static final Map<Integer, String> ID_OUTLINE_TEXTURES = new HashMap<>();

    static {
        CLASS_OUTLINE_TEXTURES.put(ItemAccessoryPendant.class, "armor_pendant_outline");
        CLASS_OUTLINE_TEXTURES.put(ItemRegenStone.class, "armor_stone_outline");
        CLASS_OUTLINE_TEXTURES.put(ItemGoldenFeather.class, "armor_feather_outline");
        CLASS_OUTLINE_TEXTURES.put(ItemIronBubble.class, "armor_bubble_outline");
        CLASS_OUTLINE_TEXTURES.put(ItemShield.class, "armor_shield_outline");

        ID_OUTLINE_TEXTURES.put(Items.TOOL_CLOCK.id, "armor_clock_outline");
        ID_OUTLINE_TEXTURES.put(Items.TOOL_COMPASS.id, "armor_compass_outline");
        ID_OUTLINE_TEXTURES.put(Items.TOOL_CALENDAR.id, "armor_calendar_outline");
    }

    // capture slot for later
    @Inject(method = "render*", at = @At("HEAD"))
    private void captureRenderArgs(ItemStack itemStack, int x, int y, boolean isSelected, Slot slot, CallbackInfo ci) {
        this.slot = slot;
    }

    @Redirect(
            method = "Lnet/minecraft/client/gui/ItemElement;render(Lnet/minecraft/core/item/ItemStack;IIZLnet/minecraft/core/player/inventory/slot/Slot;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/ItemElement;drawTexturedIcon(IIIILnet/minecraft/client/render/texture/stitcher/IconCoordinate;)V",
                    ordinal = 0
            )
    )
    public void changeWildcardIconOnHoverAndClick(ItemElement instance, int x, int y, int slotWidth, int slotHeight, IconCoordinate defaultIcon) {
        Minecraft mc = Minecraft.getMinecraft();
        if (
                this.slot.index >= ARMOR_START_INDEX + WILDCARD_1_SLOT
                        && (mc.currentScreen instanceof ScreenInventory || mc.currentScreen instanceof ScreenInventoryCreative)
        ) {
            String root = "aether:item/wildcard/";
            // got this from WoldRender, works like a charm
            int screenWidth = mc.resolution.getScaledWidthScreenCoords();
            int screenHeight = mc.resolution.getScaledHeightScreenCoords();
            int mouseX = Mouse.getX() * screenWidth / mc.resolution.getWidthScreenCoords();
            int mouseY = screenHeight - Mouse.getY() * screenHeight / mc.resolution.getHeightScreenCoords() - 1;

            // TODO better way of checking if an item is dragged or not
            if(((MenuInventory)((ScreenInventory) mc.currentScreen).inventorySlots).inventory.getHeldItemStack() != null){
                ItemStack hoverStack = ((MenuInventory)((ScreenInventory) mc.currentScreen).inventorySlots).inventory.getHeldItemStack();
                if (hoverStack != null) {
                    Item item = hoverStack.getItem();
                    if (item instanceof Accessory || item.hasTag(AetherItemTags.ACCESSORY)) {
                        IconCoordinate displayIcon = getIconCoordinate(item, root);
                        instance.drawTexturedIcon(x, y, 16, 16, displayIcon != null ? displayIcon : defaultIcon);
                        return;
                    }
                }
                instance.drawTexturedIcon(x, y, 16, 16, defaultIcon);
                return;
            }

            Slot slot = ((ScreenInventory) mc.currentScreen).getSlotAtPosition(mouseX, mouseY);
            if (slot != null) {
                ItemStack hoverStack = slot.getItemStack();
                if (hoverStack != null) {
                    Item item = hoverStack.getItem();
                    if (item instanceof Accessory || item.hasTag(AetherItemTags.ACCESSORY)) {
                        IconCoordinate displayIcon = getIconCoordinate(item, root);
                        instance.drawTexturedIcon(x, y, 16, 16, displayIcon != null ? displayIcon : defaultIcon);
                        return;
                    }
                }
            }
        }
        instance.drawTexturedIcon(x, y, 16, 16, defaultIcon);
    }

    @Unique
    private static @Nullable IconCoordinate getIconCoordinate(Item item, String root) {
        for (Map.Entry<Class<? extends Item>, String> entry : CLASS_OUTLINE_TEXTURES.entrySet()) {
            if (entry.getKey().isInstance(item)) {
                return TextureRegistry.getTexture(root + entry.getValue());
            }
        }

        if (item.hasTag(AetherItemTags.ACCESSORY)) {
            String textureName = ID_OUTLINE_TEXTURES.get(item.id);
            if (textureName != null) {
                return TextureRegistry.getTexture(root + textureName);
            }
        }
        return null;
    }
}
