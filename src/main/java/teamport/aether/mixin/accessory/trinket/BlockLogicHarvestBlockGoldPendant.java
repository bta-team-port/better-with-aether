package teamport.aether.mixin.accessory.trinket;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.AetherItems;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.items.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(value = BlockLogic.class, remap = false)
public abstract class BlockLogicHarvestBlockGoldPendant {
    @Shadow @Final @NotNull public Block<?> block;

    @Shadow public abstract void dropBlockWithCause(World world, EnumDropCause cause, int x, int y, int z, int meta, TileEntity tileEntity, Player player);

    @Inject(method = "harvestBlock", at = @At("HEAD"), cancellable = true)
    private void aether_harvestBlock(World world, @NotNull Player player, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfo ci) {
        ItemStack heldItemStack = player.inventory.getCurrentItem();
        Item heldItem = heldItemStack != null ? Item.itemsList[heldItemStack.itemID] : null;
        if (heldItem == null) return;

        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        ItemStack goldPendantOne = null;
        ItemStack goldPendantTwo = null;

        if (trinketOne != null) {
            if (trinketOne.getItem().equals(AetherItems.ARMOR_TALISMAN_GOLD)) goldPendantOne = trinketOne;
        }

        if (trinketTwo != null) {
            if (trinketTwo.getItem().equals(AetherItems.ARMOR_TALISMAN_GOLD)) goldPendantTwo = trinketTwo;
        }

        if (goldPendantOne != null || goldPendantTwo != null) {
            player.addStat(block.getStat("stat_mined"), 1);
            if (player.canHarvestBlock(block)) {
                dropBlockWithCause(world, EnumDropCause.SILK_TOUCH, x, y, z, meta, tileEntity, player);
            }

            ci.cancel();
        }
    }
}
