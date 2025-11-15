package teamport.aether.mixin.armor.wolf;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Mob.class, remap = false)
public abstract class MobMixinBlastImmunity {
    @Shadow
    public abstract boolean interact(@NonNull Player player);
    @ModifyExpressionValue(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;getHealth()I", ordinal = 0))
    private int negateDamage(int original, Entity attacker, int damage, DamageType type) {
        if (type == null || !type.equals(DamageType.BLAST)) return original;
        Mob mob = (Mob) (Object) this;
        if (!(mob instanceof MobWolf)) return original;
        ArmorMaterial material = ((MobWolf) mob).getArmorMaterial();
        if (material == null || !material.equals(AetherArmorMaterial.OBSIDIAN)) return original;
        return 0;
    }
}
