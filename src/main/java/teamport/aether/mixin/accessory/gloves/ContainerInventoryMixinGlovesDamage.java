package teamport.aether.mixin.accessory.gloves;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.items.AetherArmorMaterial;
import teamport.aether.items.accessory.ItemGloves;

import static teamport.aether.AetherMod.ZANITE_MULTIPLIER;
import static teamport.aether.items.accessory.SlotAccessory.GLOVES_SLOT;

@Mixin(value = ContainerInventory.class, remap = false)
public abstract class ContainerInventoryMixinGlovesDamage {
    @ModifyExpressionValue(method = "getDamageVsEntity", at = @At(value = "CONSTANT", args = "intValue=1"))
    public int getGloveDamage(int original, @Local(argsOnly = true) Entity entity) {
        ContainerInventory inv = (ContainerInventory) (Object) this;
        ItemStack stack = inv.armorInventory[GLOVES_SLOT];
        if (stack == null || !(stack.getItem() instanceof ItemGloves)) {
            return original;
        }
        ItemGloves gloves = (ItemGloves) stack.getItem();
        ArmorMaterial material = gloves.getArmorMaterial();
        if (material!= null && material == AetherArmorMaterial.ZANITE) {
            float durabilityProgress = (float) stack.getMetadata() / material.durability;
            float endingDamage = gloves.getDamage() * ZANITE_MULTIPLIER;
            return Math.round(MathHelper.lerp(gloves.getDamage(), endingDamage, durabilityProgress));
        }
        return Math.max(gloves.getDamage(), original);
    }
}
