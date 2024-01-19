package bta.aether.mixin;

import bta.aether.item.AetherItems;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayer.class, remap = false)
abstract public class EntityPlayerMixinAgility extends EntityLiving {
    @Shadow public InventoryPlayer inventory;

    public EntityPlayerMixinAgility(World world) {
        super(world);
    }

    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    private void changeStepHeight(CallbackInfo ci) {
        ItemStack itemStack = inventory.armorItemInSlot(5);
        if (itemStack != null && itemStack.itemID == AetherItems.armorCapeAgility.id) {
            footSize = 1.0f;
        } else {
            footSize = 0.5f;
        }
    }
}
