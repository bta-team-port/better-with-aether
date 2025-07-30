package teamport.aether.mixin.accessory.trinket;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.items.AetherItems;

import static teamport.aether.items.accessory.SlotAccessory.*;

@Mixin(value = ContainerInventory.class, remap = false)
public abstract class ContainerInventoryGetStrVsBlockPendantsMixin {

    @Shadow public Player player;

    @Shadow public ItemStack[] mainInventory;

    @Shadow protected int currentItem;

    @Inject(method = "getStrVsBlock", at = @At("HEAD"), cancellable = true)
    private void aether_getStrVsBlock(Block<?> block, CallbackInfoReturnable<Float> cir) {
        if (mainInventory[currentItem] == null) return;
        float baseSpeed = mainInventory[currentItem].getStrVsBlock(block);

        if (baseSpeed <= 1.0F) return;
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        boolean hasDiamondOne = trinketOne != null && trinketOne.getItem().equals(AetherItems.ARMOR_TALISMAN_DIAMOND);
        boolean hasDiamondTwo = trinketTwo != null && trinketTwo.getItem().equals(AetherItems.ARMOR_TALISMAN_DIAMOND);
        boolean hasZaniteOne = trinketOne != null && trinketOne.getItem().equals(AetherItems.ARMOR_TALISMAN_ZANITE);
        boolean hasZaniteTwo = trinketTwo != null && trinketTwo.getItem().equals(AetherItems.ARMOR_TALISMAN_ZANITE);

        if (hasDiamondOne || hasDiamondTwo) {
            float multiplier = 0.0F;
            if (hasDiamondOne) multiplier += 3.5F;
            if (hasDiamondTwo) multiplier += 3.5F;

            float f = 1.0F;
            if (mainInventory[currentItem] != null) f *= mainInventory[currentItem].getStrVsBlock(block) + multiplier;

            cir.setReturnValue(f);
        }

        boolean hasZanite = false;

        if (hasZaniteOne || hasZaniteTwo) {
            ItemStack stack = mainInventory[currentItem];
            if (stack == null) return;

            ItemTool tool = (ItemTool)stack.getItem();
            ToolMaterial toolMaterial = tool.getMaterial();

            if (trinketOne != null) {
                float damagePercent = (float) trinketOne.getMetadata() / trinketOne.getMaxDamage();
                float speed = MathHelper.lerp(0.0F, toolMaterial.getEfficiency(true), damagePercent);

                baseSpeed += speed;
                hasZanite = true;
            }

            if (trinketTwo != null) {
                float damagePercent = (float) trinketTwo.getMetadata() / trinketTwo.getMaxDamage();
                float speed = MathHelper.lerp(0.0F, toolMaterial.getEfficiency(true), damagePercent);

                baseSpeed += speed;
                hasZanite = true;
            }
        }

        if (hasZanite) cir.setReturnValue(baseSpeed);
    }
}
