package teamport.aether.mixin.gui;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.core.player.inventory.menu.MenuInventoryCreative;
import net.minecraft.core.util.helper.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.block.AetherBlocks;
import teamport.aether.item.AetherItems;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.core.player.inventory.menu.MenuInventoryCreative.creativeItems;
import static net.minecraft.core.player.inventory.menu.MenuInventoryCreative.creativeItemsCount;

@Mixin(value = MenuInventoryCreative.class, remap = false)
public abstract class MenuInventoryAddCreativeItemsMixin extends MenuInventory {
    protected MenuInventoryAddCreativeItemsMixin(ContainerInventory inventory) {
        super(inventory);
    }
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void injected(CallbackInfo ci) {
        List<ItemStack> newCreativeItems = new ArrayList<>();

        for (ItemStack item : creativeItems) {
            if (item.getMetadata() == 0
                    && (item.itemID == AetherBlocks.PLANKS_SKYROOT_PAINTED.id() || item.itemID == AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED.id())) {
                for (DyeColor dyeColor : DyeColor.blockOrderedColors()) {
                    newCreativeItems.add(new ItemStack(item.itemID, 1, dyeColor.blockMeta));
                }
            } else if (item.getMetadata() == 0
                && (item.itemID == AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED.id()
                || item.itemID == AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED.id()
                || item.itemID == AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED.id()
                || item.itemID == AetherBlocks.BUTTON_PLANKS_SKYROOT_PAINTED.id()
                || item.itemID == AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT_PAINTED.id()
                || item.itemID == AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED.id()
                || item.itemID == AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id()
                || item.itemID == AetherBlocks.CHEST_MIMIC_OAK_PAINTED.id()
                || item.itemID == AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.id()
            )) {
                for (DyeColor dyeColor : DyeColor.blockOrderedColors()) {
                    newCreativeItems.add(new ItemStack(item.itemID, 1, dyeColor.blockMeta << 4));
                }
            } else if (item.getMetadata() == 0
                && (item.itemID == AetherItems.SIGN_SKYROOT_PAINTED.id || item.itemID == AetherItems.DOOR_SKYROOT_PAINTED.id)
            ) {
                for (DyeColor dyeColor : DyeColor.itemOrderedColors()) {
                    newCreativeItems.add(new ItemStack(item.itemID, 1, dyeColor.itemMeta));
                }
            } else newCreativeItems.add(item);
        }

        creativeItems = newCreativeItems;
        creativeItemsCount = creativeItems.size();
    }
}
