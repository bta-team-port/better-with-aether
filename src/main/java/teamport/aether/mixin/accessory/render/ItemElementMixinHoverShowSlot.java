package teamport.aether.mixin.accessory.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ItemElement;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.accessory.Accessory;

import static teamport.aether.AetherMod.ARMOR_START_INDEX;
import static teamport.aether.items.accessory.SlotAccessory.*;

@Mixin(value = ItemElement.class, remap = false)
public class ItemElementMixinHoverShowSlot {

    // Store the arguments temporarily
//    private ItemStack itemStack;
//    private boolean isSelected;
    private Slot slot;

    @Inject(method = "render", at = @At("HEAD"))
    private void captureRenderArgs(ItemStack itemStack, int x, int y, boolean isSelected, Slot slot, CallbackInfo ci) {
//        this.itemStack = itemStack;
//        this.isSelected = isSelected;
        this.slot = slot;
    }


    // TODO make it so when hovering over a slot it shows where to go, currently the coordinates are off, and its inconsistent between screen resolutions
    @Redirect(
            method = "Lnet/minecraft/client/gui/ItemElement;render(Lnet/minecraft/core/item/ItemStack;IIZLnet/minecraft/core/player/inventory/slot/Slot;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/ItemElement;drawTexturedIcon(IIIILnet/minecraft/client/render/texture/stitcher/IconCoordinate;)V",
                    ordinal = 0
            )
    )
    public void fuck(ItemElement instance, int x, int y, int width, int height, IconCoordinate iconCoordinate){
//        if (this.slot.index < ARMOR_START_INDEX + WILDCARD_1_SLOT) {
//            instance.drawTexturedIcon(x,y,16,16,iconCoordinate);
//            return;
//        }
//        Minecraft mc = Minecraft.getMinecraft();
//        int mouseX = Mouse.getX() * mc.resolution.getScaledWidthScreenCoords() / mc.resolution.getWidthScreenCoords();
//        int mouseY = Mouse.getY() * mc.resolution.getScaledHeightScreenCoords() / mc.resolution.getHeightScreenCoords() - 1;
//        if(mc.currentScreen == null){
//            instance.drawTexturedIcon(x,y,16,16,iconCoordinate);
//            return;
//        }
//        x = mc.currentScreen.height +  x;
//        y = mc.currentScreen.width +  y;
//        for(Slot slot: mc.thePlayer.inventorySlots.slots){
//            if(getIsMouseOverSlot(slot, x, y, (int)Math.round(mouseX), (int)Math.round(mouseY))){
//                ItemStack hoverStack = slot.getItemStack();
//                if(hoverStack == null){
//                    continue;
//                }
//                IconCoordinate iconIndex;
//                if(hoverStack.getItem() instanceof Accessory){
//                    iconIndex = TextureRegistry.getTexture("aether:item/amber");
//                    instance.drawTexturedIcon(x,y,16,16,iconIndex);
//                    return;
//                }else{
//                    instance.drawTexturedIcon(x,y,16,16,iconCoordinate);
//                    return;
//                }
//            }
//        }
        instance.drawTexturedIcon(x, y, 16, 16, iconCoordinate);
    }

    private static boolean getIsMouseOverSlot(Slot slot, int x, int y, int mouseX, int mouseY) {
//        return mouseX >= x + slot.x - 1 && mouseX < x + slot.x + 16 + 1 && mouseY >= y + slot.y - 1 && mouseY < y + slot.y + 16 + 1;
        return mouseX >= slot.x - 1 && mouseX < slot.x + 16 + 1 && mouseY >= slot.y - 1 && mouseY < slot.y + 16 + 1;
    }

}
