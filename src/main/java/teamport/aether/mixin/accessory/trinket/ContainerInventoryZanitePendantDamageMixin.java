package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherItems;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(Player.class)
public abstract class ContainerInventoryZanitePendantDamageMixin {
    @ModifyExpressionValue(
        method = "attackTargetEntityWithCurrentItem(Lnet/minecraft/core/entity/Entity;)V",
        at = @At(value = "CONSTANT", args = "intValue=1", ordinal = 0)
    )
    private int getZanitePendantDamage(int damage) {
        Player player = (Player) (Object) this;
        ItemStack trinketOne = PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_1_SLOT);
        ItemStack trinketTwo = PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_2_SLOT);
        if (trinketOne != null && trinketOne.itemID == AetherItems.ARMOR_TALISMAN_ZANITE.id) {
            damage = addDamage(player, damage, trinketOne, TRINKET_1_SLOT);
        }
        if (trinketTwo != null && trinketTwo.itemID == AetherItems.ARMOR_TALISMAN_ZANITE.id) {
            damage = addDamage(player, damage, trinketTwo, TRINKET_2_SLOT);
        }
        return damage;
    }
    @Unique
    private int addDamage(Player player, int damage, ItemStack trinket, int slotID) {
        float damagePercent = (float) trinket.getMetadata() / trinket.getMaxDamage();
        float speed = MathHelper.lerp(0.0F, 3.0F, damagePercent);
        PlayerUtil.damageItemArmor(player, trinket, slotID);
        damage += (int) Math.floor(speed);
        return damage;
    }
}
