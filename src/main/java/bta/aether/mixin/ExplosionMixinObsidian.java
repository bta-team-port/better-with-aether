package bta.aether.mixin;

import bta.aether.item.AetherItems;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Explosion.class, remap = false)
abstract public class ExplosionMixinObsidian {

    @Inject(
            method = "damageEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/entity/Entity;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z"
            )
    )
    private void changeKnockback(CallbackInfo ci, @Local(ordinal = 6) LocalDoubleRef d13, @Local Entity entity) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) entity;
        ItemStack bootsSlot = player.inventory.armorItemInSlot(0);
        ItemStack leggingsSlot = player.inventory.armorItemInSlot(1);
        ItemStack chestplateSlot = player.inventory.armorItemInSlot(2);
        ItemStack helmetSlot = player.inventory.armorItemInSlot(3);

        if (
                helmetSlot == null ||
                        helmetSlot.itemID != AetherItems.armorHelmetObsidian.id ||
                        chestplateSlot == null ||
                        chestplateSlot.itemID != AetherItems.armorChestplateObsidian.id ||
                        leggingsSlot == null ||
                        leggingsSlot.itemID != AetherItems.armorLeggingsObsidian.id ||
                        bootsSlot == null ||
                        bootsSlot.itemID != AetherItems.armorBootsObsidian.id
        ) {
            return;
        }

        d13.set(d13.get() * 0.1f);
    }
}
