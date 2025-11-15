package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolShears;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.AetherItems;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.items.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(value = BlockLogic.class, remap = false)
public abstract class BlockLogicHarvestBlockGoldPendant {
    @Shadow
    @Final
    @NonNull
    public Block<?> block;
    /// spoof the check
    @WrapOperation(method = "harvestBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/Item;isSilkTouch()Z"))
    private boolean golPendantEquipped(Item instance, Operation<Boolean> original, @Local(argsOnly = true) Player player) {
        ItemStack heldItemStack = player.inventory.getCurrentItem();
        Item heldItem = heldItemStack != null ? Item.itemsList[heldItemStack.itemID] : null;
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        boolean goldInSlot1 = trinketOne != null && trinketOne.getItem().id == AetherItems.ARMOR_TALISMAN_GOLD.id;
        boolean goldInSlot2 = trinketTwo != null && trinketTwo.getItem().id == AetherItems.ARMOR_TALISMAN_GOLD.id;
        return ((goldInSlot1) || (goldInSlot2) || original.call(instance)) && !(heldItem instanceof ItemToolShears);
    }
    ///  only one takes damage and only if the check succeed
    @Inject(method = "harvestBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/BlockLogic;dropBlockWithCause(Lnet/minecraft/core/world/World;Lnet/minecraft/core/enums/EnumDropCause;IIIILnet/minecraft/core/block/entity/TileEntity;Lnet/minecraft/core/entity/player/Player;)V"),
        slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;canHarvestBlock(Lnet/minecraft/core/block/Block;)Z"), to = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Block;hasTag(Lnet/minecraft/core/data/tag/Tag;)Z")))
    private void damageGoldPendant(World world, Player player, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfo ci) {
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        if (trinketOne != null && trinketOne.getItem().id == AetherItems.ARMOR_TALISMAN_GOLD.id) {
            trinketOne.damageItem(1, player);
            return;
        }
        if (trinketTwo != null && trinketTwo.getItem().id == AetherItems.ARMOR_TALISMAN_GOLD.id) {
            trinketTwo.damageItem(1, player);
        }
    }
    // make it apply when player has no item equipped (e.i barefist)
    @WrapOperation(method = "harvestBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;canHarvestBlock(Lnet/minecraft/core/block/Block;)Z", ordinal = 1))
    private boolean catchNullItemOne(Player instance, Block<?> block, Operation<Boolean> original, World world, Player player, int x, int y, int z, int meta, TileEntity tileEntity, @Local Item heldItem, @Share("exists") LocalBooleanRef exists, @Share("goldInSlot6") LocalBooleanRef goldInSlot6Ref) {
        boolean canHarvestBlock = original.call(instance, block);
        exists.set(false);
        goldInSlot6Ref.set(false);
        if (heldItem != null) return canHarvestBlock;
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        boolean goldInSlot6 = trinketOne != null && trinketOne.getItem().id == AetherItems.ARMOR_TALISMAN_GOLD.id;
        boolean goldInSlot7 = trinketTwo != null && trinketTwo.getItem().id == AetherItems.ARMOR_TALISMAN_GOLD.id;
        if ((goldInSlot6 || goldInSlot7) && canHarvestBlock) {
            exists.set(true);
            if (goldInSlot6) goldInSlot6Ref.set(true);
        return true;
        }
        return canHarvestBlock;
    }
    @WrapOperation(method = "harvestBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/BlockLogic;dropBlockWithCause(Lnet/minecraft/core/world/World;Lnet/minecraft/core/enums/EnumDropCause;IIIILnet/minecraft/core/block/entity/TileEntity;Lnet/minecraft/core/entity/player/Player;)V", ordinal = 2))
    private void catchNullItemTwo(BlockLogic instance, World world, EnumDropCause cause, int x, int y, int z, int meta, TileEntity tileEntity, Player player, Operation<Void> original, @Share("exists") LocalBooleanRef exists, @Share("goldInSlot6") LocalBooleanRef goldInSlot6Ref) {
        original.call(instance, world, exists.get() ? EnumDropCause.SILK_TOUCH : cause, x, y, z, meta, tileEntity, player);
        if (exists.get()) {
            if (goldInSlot6Ref.get()) {
                player.inventory.armorInventory[TRINKET_1_SLOT].damageItem(1, player);
                return;
            }
            player.inventory.armorInventory[TRINKET_2_SLOT].damageItem(1, player);
        }
    }
}
