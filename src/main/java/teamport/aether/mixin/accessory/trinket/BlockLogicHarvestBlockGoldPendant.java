package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolShears;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherItems;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(value = BlockLogic.class, remap = false)
public abstract class BlockLogicHarvestBlockGoldPendant {
    @WrapOperation(
        method = "onHarvest(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/world/pos/TilePosc;ILnet/minecraft/core/block/entity/TileEntity;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/block/BlockLogic;dropWithCause(Lnet/minecraft/core/world/World;Lnet/minecraft/core/enums/EnumDropCause;Lnet/minecraft/core/world/pos/TilePosc;ILnet/minecraft/core/block/entity/TileEntity;Lnet/minecraft/core/entity/player/Player;)V"
        )
    )
    private void useGoldPendantForSilkTouch(BlockLogic instance, World world, EnumDropCause cause, TilePosc pos,
                                             int data, TileEntity tileEntity, Player player, Operation<Void> original) {
        ItemStack heldStack = player.inventory.getCurrentItem();
        boolean holdingShears = heldStack != null && heldStack.getItem() instanceof ItemToolShears;
        ItemStack pendant = getGoldPendant(player, TRINKET_1_SLOT);
        int pendantSlot = TRINKET_1_SLOT;

        if (pendant == null) {
            pendant = getGoldPendant(player, TRINKET_2_SLOT);
            pendantSlot = TRINKET_2_SLOT;
        }

        if (cause == EnumDropCause.PROPER_TOOL && !holdingShears && pendant != null) {
            original.call(instance, world, EnumDropCause.SILK_TOUCH, pos, data, tileEntity, player);
            PlayerUtil.damageItemArmor(player, pendant, pendantSlot);
            return;
        }

        original.call(instance, world, cause, pos, data, tileEntity, player);
    }

    private static ItemStack getGoldPendant(Player player, int slot) {
        ItemStack stack = PlayerUtil.getArmorOrAccessoryItem(player, slot);
        return stack != null && stack.getItem().id == AetherItems.ARMOR_TALISMAN_GOLD.id ? stack : null;
    }
}
