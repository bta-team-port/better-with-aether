package teamport.aether.mixin.accessory.functional.gloves;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import teamport.aether.items.accessory.ItemAccessoryGloves;

import static teamport.aether.items.accessory.SlotAccessory.GLOVES_SLOT;

@Mixin(value = ContainerInventory.class, remap = false)
public abstract class ContainerInventoryMixinGlovesDamage {

    @ModifyConstant(method = "getDamageVsEntity", constant = @Constant(intValue = 1))
    public int getGloveDamage(int constant, @Local(argsOnly = true) Entity entity) {
        ContainerInventory inv = (ContainerInventory) (Object) this;
        if(inv.armorInventory[GLOVES_SLOT] == null){
            return 1;
        }
        ArmorMaterial material = ((ItemAccessoryGloves) inv.armorInventory[GLOVES_SLOT].getItem()).getArmorMaterial();
        float totalProtection = 0;
        for(DamageType damageType: DamageType.values()){
            totalProtection += material.getProtection(damageType);
        }
        int damage = (int)Math.floor(totalProtection);
        return Math.max(damage, 1);
    }
}
