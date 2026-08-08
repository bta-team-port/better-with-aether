package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.entity.IArmorWearing;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherArmorMaterial;
import teamport.aether.item.accessory.SlotAccessory;

@Mixin(IArmorWearing.class)
public interface ContainerInventoryGetProtectionOfPendantMixin {
    @ModifyReturnValue(method = "getTotalProtectionAmount(Lnet/minecraft/core/util/helper/DamageType;)F", at = @At("RETURN"))
    private float includePendantProtection(float original, DamageType damageType) {
        if (!((Object) this instanceof Player player)) {
            return original;
        }

        float protection = original;
        for (ItemStack stack : ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory()) {
            if (stack == null || !(stack.getItem() instanceof IArmorItem<?> armor)) {
                continue;
            }
            ArmorMaterial material = armor.getArmorMaterial();
            if (material != null) {
                float materialProtection = material.getProtection(damageType);
                if (material == AetherArmorMaterial.ZANITE) {
                    float durabilityProgress = (float) stack.getMetadata() / material.durability;
                    materialProtection = MathHelper.lerp(
                        materialProtection,
                        AetherArmorMaterial.ZANITE_BROKEN.getProtection(damageType),
                        durabilityProgress
                    );
                }
                protection += materialProtection * armor.getArmorPieceProtectionPercentage();
            }
        }
        return protection;
    }

    @Inject(method = "damageArmor(I)V", at = @At("TAIL"))
    private void damageAccessoryArmor(int damage, CallbackInfo ci) {
        if (!((Object) this instanceof Player player) || player.world.isClientSide) {
            return;
        }

        ItemStack[] accessories = ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory();
        for (int index = 0; index < accessories.length; ++index) {
            ItemStack stack = accessories[index];
            if (stack == null || !(stack.getItem() instanceof IArmorItem<?> armor) || armor.getArmorMaterial() == null) {
                continue;
            }
            PlayerUtil.damageItemArmor(player, damage, stack, SlotAccessory.GLOVES_SLOT + index);
        }
    }
}
