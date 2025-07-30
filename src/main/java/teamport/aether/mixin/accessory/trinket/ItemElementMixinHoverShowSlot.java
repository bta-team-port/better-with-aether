package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ItemElement;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.gui.container.ScreenInventoryCreative;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import teamport.aether.items.AetherItemTags;
import teamport.aether.items.accessory.*;
import teamport.aether.lookup.LookupTrinketIcons;

import static teamport.aether.AetherMod.ARMOR_START_INDEX;
import static teamport.aether.items.accessory.SlotAccessory.*;

@Mixin(value = ItemElement.class, remap = false)
abstract public class ItemElementMixinHoverShowSlot extends Gui {

    @Unique int tick = 0;
    @Unique String currentIconPath_TRINKET_1 = LookupTrinketIcons.instance.getRandomEntry();
    @Unique String currentIconPath_TRINKET_2 = LookupTrinketIcons.instance.getRandomEntry();

    @Shadow
    Minecraft mc;

    @Redirect(
            method = "Lnet/minecraft/client/gui/ItemElement;render(Lnet/minecraft/core/item/ItemStack;IIZLnet/minecraft/core/player/inventory/slot/Slot;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/ItemElement;drawTexturedIcon(IIIILnet/minecraft/client/render/texture/stitcher/IconCoordinate;)V",
                    ordinal = 0
            )
    )
    public void changeWildcardIconOnHoverAndClick(ItemElement instance, int x, int y, int slotWidth, int slotHeight, IconCoordinate defaultIcon, @Local Slot currectSlot, @Local ItemStack itemStack) {
        if (
                currectSlot.index >= ARMOR_START_INDEX + TRINKET_1_SLOT
                && (this.mc.currentScreen instanceof ScreenInventory || this.mc.currentScreen instanceof ScreenInventoryCreative)
        ) {
            tick++;
            if(tick > 600){ // 3000
                tick = 0;
                currentIconPath_TRINKET_1 = LookupTrinketIcons.instance.getRandomEntry();
                currentIconPath_TRINKET_2 = LookupTrinketIcons.instance.getRandomEntry();
            }
            //TODO make it possible to load other mods outlines as well
            String root = String.format("%s:item/wildcard/", "aether");
            defaultIcon = TextureRegistry.getTexture(root + (currectSlot.index > ARMOR_START_INDEX + TRINKET_1_SLOT ? currentIconPath_TRINKET_2 : currentIconPath_TRINKET_1));
            // got this from WoldRender, works like a charm
            int screenWidth = this.mc.resolution.getScaledWidthScreenCoords();
            int screenHeight = this.mc.resolution.getScaledHeightScreenCoords();
            int mouseX = Mouse.getX() * screenWidth / this.mc.resolution.getWidthScreenCoords();
            int mouseY = screenHeight - Mouse.getY() * screenHeight / this.mc.resolution.getHeightScreenCoords() - 1;

            // TODO better way of checking if an item is dragged or not
            if(((MenuInventory)((ScreenInventory) this.mc.currentScreen).inventorySlots).inventory.getHeldItemStack() != null){
                ItemStack hoverStack = ((MenuInventory)((ScreenInventory) this.mc.currentScreen).inventorySlots).inventory.getHeldItemStack();
                if (hoverStack != null) {
                    Item item = hoverStack.getItem();
                    if (item instanceof Accessory || item.hasTag(AetherItemTags.TRINKET)) {
                        String iconPath = LookupTrinketIcons.instance.getEntry(item);
                        IconCoordinate displayIcon = iconPath != null ? TextureRegistry.getTexture(root + iconPath) : defaultIcon;
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
                    if (item instanceof Accessory || item.hasTag(AetherItemTags.TRINKET)) {
                        String iconPath = LookupTrinketIcons.instance.getEntry(item);
                        IconCoordinate displayIcon = iconPath != null ? TextureRegistry.getTexture(root + iconPath) : defaultIcon;
                        instance.drawTexturedIcon(x, y, 16, 16, displayIcon);
                        return;
                    }
                }
            }
        }
        instance.drawTexturedIcon(x, y, 16, 16, defaultIcon);
    }
}
