package bta.aether.mixin;

import bta.aether.accessory.API.AccessoryHelper;
import bta.aether.item.AetherItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Block.class, remap = false)
public abstract class BlockMixinGoldAccessories {

    @Inject(method = "harvestBlock", at = @At("HEAD"))
    public void damageGoldAccessories(World world, EntityPlayer entityplayer, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfo ci) {
        // damages first gold accessory equipped when breaking a block
        int gold_item_slot = AccessoryHelper.firstSlotWithAccessory(entityplayer, AetherItems.armorPendantGold);

        if (gold_item_slot == -1)
            gold_item_slot = AccessoryHelper.firstSlotWithAccessory(entityplayer, AetherItems.armorRingGold);

        if (gold_item_slot != -1) {
            entityplayer.inventory.armorInventory[gold_item_slot].damageItem(1, entityplayer);
            if (entityplayer.inventory.armorInventory[gold_item_slot].stackSize <= 0) {
                entityplayer.inventory.armorInventory[gold_item_slot] = null;
            }
        }
    }
}
