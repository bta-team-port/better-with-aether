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
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Block.class, remap = false)
public abstract class BlockMixinGoldAccessories {

    // these blocks don't need the proper tool to be silk touched
    @Unique
    boolean is_proper_tool_exception(Block block) {
        return block.equals(Block.layerSnow) || block.equals(Block.blockSnow) || block.equals(Block.glowstone);
    }

    // these are blocks which can't have there drops compared automatically, e.g. because they have a random drop
    @Unique
    boolean is_drop_compare_exception(Block block) {
        return block.equals(Block.gravel);
    }

    @WrapOperation(method = "harvestBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Block;dropBlockWithCause(Lnet/minecraft/core/world/World;Lnet/minecraft/core/enums/EnumDropCause;IIIILnet/minecraft/core/block/entity/TileEntity;)V"))
    public void harvestBlock(Block block, World world, EnumDropCause cause, int x, int y, int z, int meta, TileEntity tile_ent, Operation<Void> original, @Local EntityPlayer player) {
        // if we use an improper tool, or are already using a silk touch tool, don't change anything
        if ( ( is_proper_tool_exception(block) || cause != EnumDropCause.IMPROPER_TOOL) && cause != EnumDropCause.SILK_TOUCH) {

            // Find equipped gold ring or pendant
            int gold_item_slot = AccessoryHelper.firstSlotWithAccessory(player, AetherItems.armorPendantGold);

            if (gold_item_slot == -1)
                gold_item_slot = AccessoryHelper.firstSlotWithAccessory(player, AetherItems.armorRingGold);

            // yes we have equipped a gold ring or pendant
            if (gold_item_slot != -1) {

                // test if block has different break result when using silk touch
                ItemStack[] silk_drops = block.getBreakResult(world, EnumDropCause.SILK_TOUCH,x,y,z,meta,tile_ent);
                ItemStack[] regular_drops = block.getBreakResult(world, EnumDropCause.PROPER_TOOL, x, y, z, meta, tile_ent);
                boolean drops_equal = true;

                // some blocks, such as fire return null for drops
                if (silk_drops == null || regular_drops == null) {
                    drops_equal = silk_drops == null && regular_drops == null;
                }
                // if the array of drops is different length, then they have different drops
                else if (silk_drops.length != regular_drops.length)
                    drops_equal = false;
                // else check if any item in the array is different, then they have different drops

                else {
                    for (int i = 0; i < silk_drops.length; i++) {
                        if (!silk_drops[i].isItemEqual(regular_drops[i])) {
                            drops_equal = false;
                            break;
                        }
                    }
                }

                if (is_drop_compare_exception(block))
                    drops_equal = false;

                if (!drops_equal) {
                    player.inventory.damageArmor(1, gold_item_slot);
                    original.call(block, world, EnumDropCause.SILK_TOUCH, x, y, z, meta, tile_ent);
                    return;
                }
            }
        }
        original.call(block, world, cause, x, y, z, meta, tile_ent);
    }
}