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
public abstract class BlockMixinZaniteAccessories {

    @Inject(method = "harvestBlock", at = @At("HEAD"))
    public void damageZaniteAccessories(World world, EntityPlayer entityplayer, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfo ci) {
        // damages first zanite accessory equipped when breaking a block
        int zanite_item_slot = AccessoryHelper.firstSlotWithAccessory(entityplayer, AetherItems.armorPendantZanite);

        if (zanite_item_slot == -1)
            zanite_item_slot = AccessoryHelper.firstSlotWithAccessory(entityplayer, AetherItems.armorRingZanite);

        if (zanite_item_slot != -1) {
            entityplayer.inventory.damageArmor(1,zanite_item_slot);
        }
    }
}