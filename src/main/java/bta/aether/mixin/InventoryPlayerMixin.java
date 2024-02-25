package bta.aether.mixin;

import bta.aether.item.AetherArmorMaterial;
import bta.aether.item.ItemPigSlayer;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.EntityPig;
import net.minecraft.core.entity.monster.EntityPigZombie;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(value = InventoryPlayer.class, remap = false)
public abstract class InventoryPlayerMixin {

    @Shadow public ItemStack[] armorInventory;
    @Shadow public EntityPlayer player;

    @Redirect(
            method = "getTotalProtectionAmount",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/item/material/ArmorMaterial;getProtection(Lnet/minecraft/core/util/helper/DamageType;)F"
            )
    )
    private float changeProtection(ArmorMaterial instance, DamageType damageType, @Local(ordinal = 0) ItemArmor armor, @Local(ordinal = 0) int i) {
        if (instance == AetherArmorMaterial.ZANITE) {
            float durability = (float) armorInventory[i].getMetadata() / ((float) armor.getMaxDamage() * 0.8f);
            durability = MathHelper.clamp(durability, 0.0f, 1.0f);
            return MathHelper.lerp(ArmorMaterial.IRON.getProtection(damageType), ArmorMaterial.GOLD.getProtection(damageType), durability);
        } else {
            return instance.getProtection(damageType);
        }
    }

    @Inject(method = "getDamageVsEntity(Lnet/minecraft/core/entity/Entity;)I", at = @At("HEAD"))
    public void getDamageVsEntity(Entity entity, CallbackInfoReturnable<Integer> cir) {
        ItemStack equippedItem = player.getCurrentEquippedItem();
        if (entity instanceof EntityPig && equippedItem != null && equippedItem.getItem() instanceof ItemPigSlayer) {
            for (int i = 0; i < 20; i++) {
                Random random = new Random();
                double dx = entity.x + (random.nextDouble() * 0.5) - 0.25;
                double dy = entity.y + 0.5 + (random.nextDouble() * 0.5) - 0.25;
                double dz = entity.z + (random.nextDouble() * 0.5) - 0.25;
                double motionX = (random.nextDouble() * 0.1) - 0.05;
                double motionY = (random.nextDouble() * 0.1) - 0.05;
                double motionZ = (random.nextDouble() * 0.1) - 0.05;
                player.world.spawnParticle("flame", dx, dy, dz, motionX, motionY, motionZ);
            }
            ((EntityPig) entity).onDeath(player);
            entity.remove();
        }
        else if (entity instanceof EntityPigZombie && equippedItem != null && equippedItem.getItem() instanceof ItemPigSlayer) {
            for (int i = 0; i < 20; i++) {
                Random random = new Random();
                double dx = entity.x + (random.nextDouble() * 0.5) - 0.25;
                double dy = entity.y + 0.5 + (random.nextDouble() * 0.5) - 0.25;
                double dz = entity.z + (random.nextDouble() * 0.5) - 0.25;
                double motionX = (random.nextDouble() * 0.1) - 0.05;
                double motionY = (random.nextDouble() * 0.1) - 0.05;
                double motionZ = (random.nextDouble() * 0.1) - 0.05;
                player.world.spawnParticle("flame", dx, dy, dz, motionX, motionY, motionZ);
            }
            ((EntityPigZombie) entity).onDeath(player);
            entity.remove();
        }
    }
}
