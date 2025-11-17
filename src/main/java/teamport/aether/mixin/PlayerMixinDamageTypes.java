package teamport.aether.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.AetherHasCustomDamageType;
import teamport.aether.items.accessory.ItemGloves;

import static teamport.aether.items.accessory.SlotAccessory.GLOVES_SLOT;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixinDamageTypes {
    @Shadow
    public ContainerInventory inventory;
    @WrapOperation(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Entity;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z"))
    private boolean replaceDamageTypes(Entity instance, Entity attacker, int baseDamage, DamageType type, Operation<Boolean> original) {
        if (attacker instanceof Player) {
            Player player = (Player) (Object) this;
            ItemStack itemstack = player.getCurrentEquippedItem();
            if (itemstack != null) {
                Item item = itemstack.getItem();
                if (item instanceof AetherHasCustomDamageType) {
                    return original.call(instance, attacker, baseDamage, ((AetherHasCustomDamageType) item).getDamageType());
                }
            }
            if (itemstack == null && player.inventory.armorInventory[GLOVES_SLOT] != null) {
                ItemStack maybeGlovesStack = player.inventory.armorInventory[GLOVES_SLOT];
                Item maybeGlovesItem = maybeGlovesStack.getItem();
                if (maybeGlovesItem instanceof ItemGloves) {
                    if (maybeGlovesItem instanceof AetherHasCustomDamageType) {
                        return original.call(instance, attacker, baseDamage, ((AetherHasCustomDamageType) maybeGlovesItem).getDamageType());
                    }
                }
            }
        }
        return original.call(instance, attacker, baseDamage, type);
    }
    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;getCurrentEquippedItem()Lnet/minecraft/core/item/ItemStack;", shift = At.Shift.AFTER))
    private void addedEffectsWithGloves(Entity entity, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        ItemStack itemstack = player.getCurrentEquippedItem();
        ItemStack gloves = player.inventory.armorInventory[GLOVES_SLOT];
        if(itemstack == null && gloves != null && gloves.getItem() instanceof ItemGloves && entity instanceof Mob){
                gloves.hitEntity((Mob) entity, player);
            }

    }
}
