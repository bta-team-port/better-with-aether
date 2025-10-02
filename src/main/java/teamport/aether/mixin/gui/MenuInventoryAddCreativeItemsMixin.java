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
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.monster.mimic.MimicVariant;
import teamport.aether.items.AetherItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.minecraft.core.player.inventory.menu.MenuInventoryCreative.creativeItems;
import static net.minecraft.core.player.inventory.menu.MenuInventoryCreative.creativeItemsCount;

@Mixin(value = MenuInventoryCreative.class, remap = false)
public class MenuInventoryAddCreativeItemsMixin extends MenuInventory {

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void injected(CallbackInfo ci) {
        List<ItemStack> newCreativeItems = new ArrayList<>();

        for (ItemStack item : creativeItems) {
            if (item.itemID == AetherBlocks.CHEST_MIMIC.id() && item.getMetadata() == 0) {
                for (MimicVariant variant : MimicVariant.values()) {
                    newCreativeItems.add(new ItemStack(AetherBlocks.CHEST_MIMIC, 1, variant.getId() << 3));
                }
            }

            else if (item.itemID == AetherBlocks.PLANKS_SKYROOT_PAINTED.id() && item.getMetadata() == 0) {
                for (DyeColor dyeColor : DyeColor.values()) {
                    newCreativeItems.add(new ItemStack(AetherBlocks.PLANKS_SKYROOT_PAINTED, 1, dyeColor.blockMeta));
                }
            }

            else if (item.itemID == AetherItems.SIGN_SKYROOT_PAINTED.id && item.getMetadata() == 0) {
                for (DyeColor dyeColor : DyeColor.values()) {
                    ItemStack sign = new ItemStack(AetherItems.SIGN_SKYROOT_PAINTED.getDefaultStack());
                    sign.setMetadata(dyeColor.itemMeta);

                    newCreativeItems.add(sign);
                }
            }

            else if (item.itemID == AetherItems.DOOR_SKYROOT_PAINTED.id && item.getMetadata() == 0) {
                for (DyeColor dyeColor : DyeColor.values()) {
                    ItemStack sign = new ItemStack(AetherItems.DOOR_SKYROOT_PAINTED.getDefaultStack());
                    sign.setMetadata(dyeColor.itemMeta);
                    newCreativeItems.add(sign);
                }
            }

            else newCreativeItems.add(item);
        }

        creativeItems = newCreativeItems;
        creativeItemsCount = creativeItems.size();
    }

    public MenuInventoryAddCreativeItemsMixin(ContainerInventory inventory) {
        super(inventory);
    }
}
