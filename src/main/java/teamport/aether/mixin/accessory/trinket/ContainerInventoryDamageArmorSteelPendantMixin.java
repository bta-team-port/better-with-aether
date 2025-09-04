package teamport.aether.mixin.accessory.trinket;

import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ContainerInventory.class, remap = false)
public abstract class ContainerInventoryDamageArmorSteelPendantMixin {
//    @Shadow public ItemStack[] armorInventory;
//
//    @Shadow public Player player;
//
//    @WrapOperation(method = "damageArmor(I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;damageItem(ILnet/minecraft/core/entity/Entity;)V"))
//    public void avoidDamageI(ItemStack instance, int damage, Entity entityPlayer, Operation<Void> original){
//        ContainerHelper.preventItemDamage(instance, damage, (Player)entityPlayer, original);
//    }
//
//    @WrapOperation(method = "damageArmor(II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;damageItem(ILnet/minecraft/core/entity/Entity;)V"))
//    public void avoidDamageII(ItemStack instance, int damage, Entity entityPlayer, Operation<Void> original){
//        ContainerHelper.preventItemDamage(instance, damage, (Player)entityPlayer, original);
//    }
}
