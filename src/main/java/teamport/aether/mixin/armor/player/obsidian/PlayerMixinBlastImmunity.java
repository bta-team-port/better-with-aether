package teamport.aether.mixin.armor.player.obsidian;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.helper.ContainerHelper;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixinBlastImmunity {
    @Shadow
    public ContainerInventory inventory;
    @ModifyExpressionValue(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z"))
    private boolean negateDamage(boolean original, Entity attacker, int damage, DamageType type) {
        if (type == null || type.equals(DamageType.BLAST) || ContainerHelper.countArmorPiecesOfMaterial(this.inventory, AetherArmorMaterial.OBSIDIAN) < 5) {
            return original;
        }
        this.inventory.damageArmor((int) Math.ceil((double) damage / (double) 4.0F));
        return false;
    }
}
