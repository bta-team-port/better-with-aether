package teamport.aether.mixin.accessory.trinket;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.AetherItems;

import java.util.Random;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.items.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(value = ContainerInventory.class, remap = false)
public abstract class ContainerInventoryDamageArmorSteelPendantMixin {
    @Unique
    private final Random aether_steelMixinRandom = new Random();

    @Shadow public ItemStack[] armorInventory;

    @Shadow public Player player;

    @Inject(method = "damageArmor(I)V", at = @At("HEAD"), cancellable = true)
    private void aether_damageArmorOne(int amount, CallbackInfo ci) {
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        boolean hasSteelOne = trinketOne != null && trinketOne.getItem().equals(AetherItems.ARMOR_TALISMAN_STEEL);
        boolean hasSteelTwo = trinketTwo != null && trinketTwo.getItem().equals(AetherItems.ARMOR_TALISMAN_STEEL);

        int random = aether_steelMixinRandom.nextInt(4);
        boolean toolProtected = hasSteelOne && hasSteelTwo ? random < 2 : random == 0;

        if (toolProtected) {
            System.out.println("Armor protected");
            if (hasSteelOne) trinketOne.damageItem(1, player);
            if (hasSteelTwo) trinketTwo.damageItem(1, player);
            ci.cancel();
        }
    }

    @Inject(method = "damageArmor(II)V", at = @At("HEAD"), cancellable = true)
    private void aether_DamageArmorTwo(int damage, int armorSlot, CallbackInfo ci) {
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        boolean hasSteelOne = trinketOne != null && trinketOne.getItem().equals(AetherItems.ARMOR_TALISMAN_STEEL);
        boolean hasSteelTwo = trinketTwo != null && trinketTwo.getItem().equals(AetherItems.ARMOR_TALISMAN_STEEL);

        int random = aether_steelMixinRandom.nextInt(4);
        boolean toolProtected = hasSteelOne && hasSteelTwo ? random < 2 : random == 0;

        if (toolProtected) {
            System.out.println("Armor protected");
            if (hasSteelOne) trinketOne.damageItem(1, player);
            if (hasSteelTwo) trinketTwo.damageItem(1, player);
            ci.cancel();
        }
    }
}
