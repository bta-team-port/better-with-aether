package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.items.AetherItems;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.items.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(value = ContainerInventory.class, remap = false)
public abstract class ContainerInventoryGetStrVsBlockPendantsMixin {
    @Shadow
    public Player player;
    @Shadow
    public ItemStack[] mainInventory;
    @ModifyReturnValue(method = "getStrVsBlock", at = @At("RETURN"))
    public float aether_getStrVsBlock(float strVsBlock, Block<?> block) {
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
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
