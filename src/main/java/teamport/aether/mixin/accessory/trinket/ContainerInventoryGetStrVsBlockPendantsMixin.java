package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherItems;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(Player.class)
public abstract class ContainerInventoryGetStrVsBlockPendantsMixin {
    @ModifyExpressionValue(
        method = "getCurrentPlayerStrVsBlock(Lnet/minecraft/core/block/Block;)F",
        at = {
            @At(
                value = "INVOKE",
                target = "Lnet/minecraft/core/item/ItemStack;getStrVsBlock(Lnet/minecraft/core/block/Block;)F"
            ),
            @At(value = "CONSTANT", args = "floatValue=1.0F")
        }
    )
    private float aether_getStrVsBlock(float strVsBlock) {
        Player player = (Player) (Object) this;
        ItemStack trinketOne = PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_1_SLOT);
        ItemStack trinketTwo = PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_2_SLOT);
        float refStrVsBlock = strVsBlock;
        if (trinketOne != null && trinketOne.itemID == AetherItems.ARMOR_TALISMAN_ZANITE.id) {
            float damagePercent = (float) trinketOne.getMetadata() / trinketOne.getMaxDamage();
            float speed = MathHelper.lerp(0.0F, refStrVsBlock, damagePercent);
            strVsBlock += speed;
        }
        if (trinketTwo != null && trinketTwo.itemID == AetherItems.ARMOR_TALISMAN_ZANITE.id) {
            float damagePercent = (float) trinketTwo.getMetadata() / trinketTwo.getMaxDamage();
            float speed = MathHelper.lerp(0.0F, refStrVsBlock, damagePercent);
            strVsBlock += speed;
        }
        return strVsBlock;
    }
}
