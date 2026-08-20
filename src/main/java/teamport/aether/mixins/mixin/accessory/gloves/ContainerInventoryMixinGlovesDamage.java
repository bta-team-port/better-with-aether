package teamport.aether.mixins.mixin.accessory.gloves;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherArmorMaterial;
import teamport.aether.item.accessory.gloves.ItemGloves;

import static teamport.aether.AetherMod.ZANITE_MULTIPLIER;
import static teamport.aether.item.accessory.SlotAccessory.GLOVES_SLOT;

@Mixin(Player.class)
public abstract class ContainerInventoryMixinGlovesDamage {
    @ModifyExpressionValue(
        method = "attackTargetEntityWithCurrentItem(Lnet/minecraft/core/entity/Entity;)V",
        at = @At(value = "CONSTANT", args = "intValue=1", ordinal = 0)
    )
    private int getGloveDamage(int original) {
        Player player = (Player) (Object) this;
        ItemStack stack = PlayerUtil.getArmorOrAccessoryItem(player, GLOVES_SLOT);
        if (stack == null || !(stack.getItem() instanceof ItemGloves gloves)) {
            return original;
        }
        ArmorMaterial material = gloves.getArmorMaterial();
        if (material!= null && material == AetherArmorMaterial.ZANITE) {
            float durabilityProgress = (float) stack.getMetadata() / material.durability;
            float endingDamage = gloves.getDamage() * ZANITE_MULTIPLIER;
            return Math.round(MathHelper.lerp(gloves.getDamage(), endingDamage, durabilityProgress));
        }
        return Math.max(gloves.getDamage(), original);
    }
}
