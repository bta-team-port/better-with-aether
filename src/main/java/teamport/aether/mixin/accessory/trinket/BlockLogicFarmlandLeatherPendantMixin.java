package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.block.BlockLogicFarmland;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.item.AetherItems;
import teamport.aether.entity.player.PlayerUtil;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(value = BlockLogicFarmland.class)
public abstract class BlockLogicFarmlandLeatherPendantMixin {
    @Expression("? instanceof ?")
    @ModifyExpressionValue(method = "onEntityWalkedOn(Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/entity/Entity;)V", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private boolean onEntityWalkedOn(boolean original, World world, TilePosc pos, Entity entity) {
        if (!original) return false;
        Player player = (Player) entity;
        ItemStack trinketOne = PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_1_SLOT);
        ItemStack trinketTwo = PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_2_SLOT);
        return (trinketOne == null || !trinketOne.getItem().namespaceID.equals(AetherItems.ARMOR_TALISMAN_LEATHER.namespaceID))
            && (trinketTwo == null || !trinketTwo.getItem().namespaceID.equals(AetherItems.ARMOR_TALISMAN_LEATHER.namespaceID));
    }
}
