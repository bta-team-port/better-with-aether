package teamport.aether.mixin.accessory.gloves;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import teamport.aether.items.AetherArmorMaterial;
import teamport.aether.items.accessory.ItemGloves;

import static teamport.aether.AetherMod.ZANITE_MULTIPLIER;
import static teamport.aether.items.accessory.SlotAccessory.GLOVES_SLOT;

@Mixin(value = ContainerInventory.class, remap = false)
public abstract class ContainerInventoryMixinGlovesDamage {

    @ModifyConstant(method = "getDamageVsEntity", constant = @Constant(intValue = 1))
    public int getGloveDamage(int constant, @Local(argsOnly = true) Entity entity) {
        ContainerInventory inv = (ContainerInventory) (Object) this;
        ItemStack stack = inv.armorInventory[GLOVES_SLOT];
        if (stack == null || !(stack.getItem() instanceof ItemGloves)) {
            return constant;
        }
        ItemGloves gloves = (ItemGloves) stack.getItem();
        if (gloves.material == AetherArmorMaterial.ZANITE) {
            float durability_progress = (float) stack.getMetadata() / gloves.material.durability;
            float ending_damage = gloves.damage * ZANITE_MULTIPLIER;
            return Math.round(MathHelper.lerp(gloves.damage, ending_damage, durability_progress));
        }
        return Math.max(gloves.damage, constant);
    }
}
