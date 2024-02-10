package bta.aether.mixin;

import bta.aether.accessory.API.AccessoryHelper;
import bta.aether.item.AetherItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Block.class, remap = false)
public abstract class BlockMixinGoldAccessories {

    @WrapOperation(method = "harvestBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Block;dropBlockWithCause(Lnet/minecraft/core/world/World;Lnet/minecraft/core/enums/EnumDropCause;IIIILnet/minecraft/core/block/entity/TileEntity;)V"))
    public void harvestBlock(Block instance, World world, EnumDropCause cause, int x, int y, int z, int meta, TileEntity tile_ent, Operation<Void> original, @Local EntityPlayer player) {
        // if we use an improper tool, or are already using a silk touch tool, don't change anything
        if (cause != EnumDropCause.IMPROPER_TOOL && cause != EnumDropCause.SILK_TOUCH) {

            // else, damage gold accessories and change dropcause to silk touch
            int gold_item_slot = AccessoryHelper.firstSlotWithAccessory(player, AetherItems.armorPendantGold);

            if (gold_item_slot == -1)
                gold_item_slot = AccessoryHelper.firstSlotWithAccessory(player, AetherItems.armorRingGold);

            if (gold_item_slot != -1) {
                player.inventory.damageArmor(1, gold_item_slot);
                original.call(instance, world, EnumDropCause.SILK_TOUCH, x, y, z, meta, tile_ent);
                return;
            }
        }
        original.call(instance, world, cause, x, y, z, meta, tile_ent);
    }
}