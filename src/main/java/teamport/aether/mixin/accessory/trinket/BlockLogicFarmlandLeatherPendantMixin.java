package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.block.BlockLogicFarmland;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.item.AetherItems;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(value = BlockLogicFarmland.class)
public abstract class BlockLogicFarmlandLeatherPendantMixin {
    @Expression("? instanceof ?")
    @ModifyExpressionValue(method = "onEntityWalking", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private boolean onEntityWalking(boolean original, World world, int x, int y, int z, Entity entity) {
        if (!original) return false;
        ItemStack[] armor = ((Player) entity).inventory.armorInventory;
        return (armor[TRINKET_1_SLOT] == null || !armor[TRINKET_1_SLOT].getItem().namespaceID.equals(AetherItems.ARMOR_TALISMAN_LEATHER.namespaceID))
            && (armor[TRINKET_2_SLOT] == null || !armor[TRINKET_2_SLOT].getItem().namespaceID.equals(AetherItems.ARMOR_TALISMAN_LEATHER.namespaceID));
    }
}
