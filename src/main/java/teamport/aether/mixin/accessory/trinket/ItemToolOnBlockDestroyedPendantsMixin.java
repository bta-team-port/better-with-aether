package teamport.aether.mixin.accessory.trinket;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.item.tool.ItemToolShears;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.items.AetherItems;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.items.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(value = ItemTool.class, remap = false)
public abstract class ItemToolOnBlockDestroyedPendantsMixin extends Item {

    public ItemToolOnBlockDestroyedPendantsMixin(String translationKey, String namespaceId, int id) {
        super(translationKey, namespaceId, id);
    }

    @Shadow public abstract boolean isSilkTouch();

    @Inject(method = "onBlockDestroyed", at = @At("HEAD"), cancellable = true)
    private void aether_onBlockDestroyedSteelPendant(@NotNull World world, ItemStack stack, int i, int x, int y, int z, Side side, Mob mob, CallbackInfoReturnable<Boolean> cir) {
        Player player = mob instanceof Player ? (Player)mob : null;
        Block<?> block = Blocks.blocksList[i];

        if (player == null || !player.getGamemode().consumeBlocks()) return;
        if (block == null) return;

        // First, we make a bunch of placeholder variables to use.
        // A bunch of ItemStacks for pendants, and a boolean whether to process.
        ItemStack goldPendantOne = null;
        ItemStack goldPendantTwo = null;
        ItemStack diamondPendantOne = null;
        ItemStack diamondPendantTwo = null;
        ItemStack steelPendantOne = null;
        ItemStack steelPendantTwo = null;
        ItemStack zanitePendantOne = null;
        ItemStack zanitePendantTwo = null;
        boolean shouldProcess = false;

        // Now we check the first trinket slot for diamond and gold pendants
        // and set the variables as so.
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        if (trinketOne != null) {
            if (trinketOne.getItem().equals(AetherItems.ARMOR_TALISMAN_GOLD)) {
                goldPendantOne = trinketOne;
                shouldProcess = true;
            }
            if (trinketOne.getItem().equals(AetherItems.ARMOR_TALISMAN_DIAMOND)) {
                diamondPendantOne = trinketOne;
                shouldProcess = true;
            }
            if (trinketOne.getItem().equals(AetherItems.ARMOR_TALISMAN_STEEL)) {
                steelPendantOne = trinketOne;
                shouldProcess = true;
            }
            if (trinketOne.getItem().equals(AetherItems.ARMOR_TALISMAN_ZANITE)) {
                zanitePendantOne = trinketOne;
                shouldProcess = true;
            }
        }

        // Then we do the same for the second trinket slot.
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        if (trinketTwo != null) {
            if (trinketTwo.getItem().equals(AetherItems.ARMOR_TALISMAN_GOLD)) {
                goldPendantTwo = trinketTwo;
                shouldProcess = true;
            }
            if (trinketTwo.getItem().equals(AetherItems.ARMOR_TALISMAN_DIAMOND)) {
                diamondPendantTwo = trinketTwo;
                shouldProcess = true;
            }
            if (trinketTwo.getItem().equals(AetherItems.ARMOR_TALISMAN_STEEL)) {
                steelPendantTwo = trinketTwo;
                shouldProcess = true;
            }
            if (trinketTwo.getItem().equals(AetherItems.ARMOR_TALISMAN_ZANITE)) {
                zanitePendantTwo = trinketTwo;
                shouldProcess = true;
            }
        }

        // If we shouldn't process, we just return early.
        if (!shouldProcess) return;

        // We also check for steel pendants.
        // These act like unbreaking for other pendants. (and all damageable items)
        boolean hasSteelProtection = steelPendantOne != null || steelPendantTwo != null;
        boolean steelProtected = false;
        boolean steelToolProtected = false;
        if (hasSteelProtection) {
            int random = itemRand.nextInt(4);
            int itemRandom = itemRand.nextInt(4);
            steelProtected = (steelPendantOne != null && steelPendantTwo != null) ? random < 2 : random == 0;
            steelToolProtected = (steelPendantOne != null && steelPendantTwo != null) ? itemRandom < 2 : itemRandom == 0;
        }

        // Quick zanite checks, just to damage em.
        if (!steelProtected) {
            if (zanitePendantOne != null) zanitePendantOne.damageItem(1, player);
            if (zanitePendantTwo != null) zanitePendantTwo.damageItem(1, player);
        }

        // Now we run the diamond pendant code, aka a haste effect.
        // This has a special case for shears. We also check whether
        // the tool has silk touch, can set it 'canProcess' to true if so.
        boolean diamondCanProcess = false;
        if (diamondPendantOne != null || diamondPendantTwo != null) {
            ItemStack heldItemStack = player.inventory.getCurrentItem();
            Item heldItem = heldItemStack != null ? Item.itemsList[heldItemStack.itemID] : null;

            if (heldItem != null) {
                if (heldItem.isSilkTouch() && player.canHarvestBlock(block)) diamondCanProcess = true;
                else if (heldItem instanceof ItemToolShears && (block.hasTag(BlockTags.SHEARS_DO_SILK_TOUCH) || block.hasTag(BlockTags.MINEABLE_BY_SHEARS))) {
                    ItemToolShears heldShears = (ItemToolShears) heldItem;
                    heldShears.onBlockSheared(player, heldItemStack);
                    diamondCanProcess = true;
                }

                if (!diamondCanProcess) {
                    if (diamondPendantOne != null && !steelProtected) diamondPendantOne.damageItem(1, player);
                    if (diamondPendantTwo != null && !steelProtected) diamondPendantTwo.damageItem(1, player);
                }
            }
        }

        // Finally, we check for gold pendants and whether diamond can process.
        // If so, drop the block with silk touch and damage attempt
        // to damage the pendants.
        if ((goldPendantOne != null || goldPendantTwo != null) || diamondCanProcess) {
            if (!steelProtected) {
                if (goldPendantOne != null) goldPendantOne.damageItem(1, player);
                if (goldPendantTwo != null) goldPendantTwo.damageItem(1, player);

                if (diamondCanProcess) {
                    if (diamondPendantOne != null) diamondPendantOne.damageItem(1, player);
                    if (diamondPendantTwo != null) diamondPendantTwo.damageItem(1, player);
                }
            }
        }

        if (trinketOne != null && trinketOne.stackSize <= 0) player.inventory.armorInventory[TRINKET_1_SLOT] = null;
        if (trinketTwo != null && trinketTwo.stackSize <= 0) player.inventory.armorInventory[TRINKET_2_SLOT] = null;

        if (steelToolProtected) {
            if (steelPendantOne != null) steelPendantOne.damageItem(1, player);
            if (steelPendantTwo != null) steelPendantTwo.damageItem(1, player);
            cir.cancel();
        }
    }
}
