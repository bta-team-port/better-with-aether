package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.player.PlayerUntil;
import teamport.aether.item.AetherItems;

import static teamport.aether.item.accessory.SlotAccessory.*;

@Mixin(value = ContainerInventory.class, remap = false)
public abstract class ContainerInventoryZanitePendantDamageMixin {
    @Shadow
    public ItemStack[] mainInventory;
    @Shadow
    public Player player;
    @ModifyReturnValue(method = "getDamageVsEntity", at = @At("RETURN"))
    private int getGloveDamage(int damage) {
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        if (trinketOne != null && trinketOne.itemID == AetherItems.ARMOR_TALISMAN_ZANITE.id) {
            damage = addDamage(damage, trinketOne, TRINKET_1_SLOT);
        }
        if (trinketTwo != null && trinketTwo.itemID == AetherItems.ARMOR_TALISMAN_ZANITE.id) {
            damage = addDamage(damage, trinketTwo, TRINKET_2_SLOT);
        }
        return damage;
    }
    @Unique
    private int addDamage(int damage, ItemStack trinket, int slotID) {
        float damagePercent = (float) trinket.getMetadata() / trinket.getMaxDamage();
        float speed = MathHelper.lerp(0.0F, 3.0F, damagePercent);
        PlayerUntil.damageItemArmor(player, trinket, slotID);
        damage += (int) Math.floor(speed);
        return damage;
    }
}
