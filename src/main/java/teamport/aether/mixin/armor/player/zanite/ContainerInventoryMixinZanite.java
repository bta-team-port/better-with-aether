package teamport.aether.mixin.armor.player.zanite;

import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = ContainerInventory.class, remap = false)
public abstract class ContainerInventoryMixinZanite{

    @Shadow
    public ItemStack[] armorInventory;

    // TODO figure a way out to get rid of mixin, in case this will be needed later
    @Inject(method = "getTotalProtectionAmount", at = @At("HEAD"), cancellable = true)
    public void injectCustomArmorProtection(DamageType damageType, CallbackInfoReturnable<Float> cir) {

        float protectionPercentage = 0.0F;

        for(int i = 0; i < this.armorInventory.length; ++i) {
            ItemStack itemStack = this.armorInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof IArmorItem) {
                IArmorItem armor = (IArmorItem)itemStack.getItem();
                if (armor.getArmorPiece() == i) {
                    ArmorMaterial material = armor.getArmorMaterial();
                    if (material == null) {
                        continue;
                    }
                    float protection = material.getProtection(damageType);
                    float percent = armor.getArmorPieceProtectionPercentage();

                    // protection value go from iron to gold
                    if (material.equals(AetherArmorMaterial.ZANITE) ){
                        float durability_progress = (float) itemStack.getMetadata() / material.durability;
                        float end_protection = ArmorMaterial.GOLD.getProtection(damageType);
                        protection = (protection * (1 - durability_progress) + end_protection * durability_progress);
                    }
                    protectionPercentage += protection * percent;
                }
            }
        }
        cir.setReturnValue(protectionPercentage);
    }
}
