package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ItemElement;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.gui.container.ScreenInventoryCreative;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.Global;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.game_settings.AetherGameSettingsOptions;
import teamport.aether.items.AetherItemTags;
import teamport.aether.items.accessory.IAccessory;
import teamport.aether.lookup.LookupTrinketIcons;

import static teamport.aether.AetherMod.ARMOR_START_INDEX;
import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;

@Environment(EnvType.CLIENT)
@Mixin(value = ItemElement.class, remap = false)
public abstract class ItemElementMixinHoverShowSlot {
    @Unique
    private int lastTick = 0;
    @Unique
    private String iconPathTrinket1;
    @Unique
    private String iconPathTrinket2;
    @Shadow
    Minecraft mc;
    @WrapOperation(method = "render(Lnet/minecraft/core/item/ItemStack;IIZLnet/minecraft/core/player/inventory/slot/Slot;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ItemElement;drawTexturedIcon(IIIILnet/minecraft/client/render/texture/stitcher/IconCoordinate;)V", ordinal = 0))
    private void changeWildcardIconOnHoverAndClick(ItemElement instance, int x, int y, int slotWidth, int slotHeight, IconCoordinate defaultIcon, Operation<Void> original, @Local(argsOnly = true) Slot currectSlot) {
        if (currectSlot.index >= ARMOR_START_INDEX + TRINKET_1_SLOT && (this.mc.currentScreen instanceof ScreenInventory || this.mc.currentScreen instanceof ScreenInventoryCreative)) {
            int seconds = ((AetherGameSettingsOptions) mc.gameSettings).aether$getAccessoryFlickSpeed().value;
            if (seconds != 0) {
                if (this.mc.thePlayer.tickCount - lastTick >= seconds * Global.TICKS_PER_SECOND) { // 3000
                    lastTick = this.mc.thePlayer.tickCount;
                    iconPathTrinket1 = LookupTrinketIcons.INSTANCE.getRandomEntry();
                    iconPathTrinket2 = LookupTrinketIcons.INSTANCE.getRandomEntry();
                }
                if (iconPathTrinket1 != null && iconPathTrinket2 != null) {
                    defaultIcon = TextureRegistry.getTexture(currectSlot.index > ARMOR_START_INDEX + TRINKET_1_SLOT ? iconPathTrinket2 : iconPathTrinket1);
                }
            }
            // got this from WoldRender, works like a charm
            int screenWidth = this.mc.resolution.getScaledWidthScreenCoords();
            int screenHeight = this.mc.resolution.getScaledHeightScreenCoords();
            int mouseX = Mouse.getX() * screenWidth / this.mc.resolution.getWidthScreenCoords();
            int mouseY = screenHeight - Mouse.getY() * screenHeight / this.mc.resolution.getHeightScreenCoords() - 1;
            // TODO better way of checking if an item is dragged or not
            if (((MenuInventory) ((ScreenInventory) this.mc.currentScreen).inventorySlots).inventory.getHeldItemStack() != null) {
                ItemStack hoverStack = ((MenuInventory) ((ScreenInventory) this.mc.currentScreen).inventorySlots).inventory.getHeldItemStack();
                if (hoverStack != null) {
                    Item item = hoverStack.getItem();
                    if (item instanceof IAccessory || item.hasTag(AetherItemTags.TRINKET)) {
                        String iconPath = LookupTrinketIcons.INSTANCE.getEntry(item);
                        IconCoordinate displayIcon = iconPath != null ? TextureRegistry.getTexture(iconPath) : defaultIcon;
                        instance.drawTexturedIcon(x, y, 16, 16, displayIcon);
                        return;
                    }
                }
            }
            Slot slot = ((ScreenInventory) this.mc.currentScreen).getSlotAtPosition(mouseX, mouseY);
            if (slot != null) {
                ItemStack hoverStack = slot.getItemStack();
                if (hoverStack != null) {
                    Item item = hoverStack.getItem();
                    if (item instanceof IAccessory || item.hasTag(AetherItemTags.TRINKET)) {
                        String iconPath = LookupTrinketIcons.INSTANCE.getEntry(item);
                        IconCoordinate displayIcon = iconPath != null ? TextureRegistry.getTexture(iconPath) : defaultIcon;
                        instance.drawTexturedIcon(x, y, 16, 16, displayIcon);
                        return;
                    }
                }
            }
        }
        original.call(instance, x, y, slotWidth, slotHeight, defaultIcon);
    }
}
