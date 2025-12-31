package teamport.aether.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.item.AetherItemTags;

@Mixin(value = EntityItem.class, remap = false)
public abstract class EntityItemMixinImmunities {
    @Shadow
    public ItemStack item;

    @WrapMethod(method = "hurt")
    private boolean preventGoldKeyBurn(Entity entity, int i, DamageType type, Operation<Boolean> original) {
        if (this.item != null && AetherItemTags.isImmuneToType(this.item.getItem(), type)){
            return false;
        }
        return original.call(entity, i, type);
    }
}
