package teamport.aether.mixin.gui;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.core.player.inventory.menu.MenuInventoryCreative;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.monster.mimic.MimicVariant;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.core.player.inventory.menu.MenuInventoryCreative.creativeItems;
import static net.minecraft.core.player.inventory.menu.MenuInventoryCreative.creativeItemsCount;

@Mixin(value = MenuInventoryCreative.class, remap = false)
public class MenuInventoryAddCreativeItemsMixin extends MenuInventory {

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void injected(CallbackInfo ci) {
        List<ItemStack> mimicVariants = new ArrayList<>();

        for (MimicVariant variant : MimicVariant.values()) {
            if (variant != MimicVariant.SKYROOT) {
                mimicVariants.add(new ItemStack(AetherBlocks.CHEST_MIMIC, 1, variant.getId() << 3));
            }
        }

        List<ItemStack> newCreativeItems = new ArrayList<>();

        for (ItemStack item : creativeItems) {
            newCreativeItems.add(item);

            if (item.itemID == AetherBlocks.CHEST_MIMIC.id() && item.getMetadata() == 0) {
                newCreativeItems.addAll(mimicVariants);
            }
        }

        creativeItems = newCreativeItems;
        creativeItemsCount = creativeItems.size();
    }

    public MenuInventoryAddCreativeItemsMixin(ContainerInventory inventory) {
        super(inventory);
    }
}
