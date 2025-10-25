package teamport.aether.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.items.AetherHasCustomDamageType;

@Mixin(value = Player.class, remap = false)
public class PlayerMixinDamageTypes {

    @Shadow public ContainerInventory inventory;

    @WrapOperation(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Entity;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z"))
    public boolean replaceDamageTypes(Entity instance, Entity attacker, int baseDamage, DamageType type, Operation<Boolean> original){
        if (attacker instanceof Player) {
            Player player = (Player) (Object) this;
            ItemStack itemstack = player.getCurrentEquippedItem();
            if (itemstack != null) {
                Item item = itemstack.getItem();
                if (item instanceof AetherHasCustomDamageType) {
                    return original.call(instance, attacker, baseDamage, ((AetherHasCustomDamageType) item).getDamageType());
                }
            }
        }
        return original.call(instance, attacker, baseDamage, type);
    }
}
