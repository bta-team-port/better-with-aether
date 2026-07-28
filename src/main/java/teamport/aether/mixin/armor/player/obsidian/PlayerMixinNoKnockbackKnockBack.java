package teamport.aether.mixin.armor.player.obsidian;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherArmorMaterial;

@Mixin(value = Mob.class, remap = false)
public abstract class PlayerMixinNoKnockbackKnockBack {
    @WrapMethod(method = "knockBack")
    private void knockBack(Entity entity, int i, double d, double d1, Operation<Void> original) {
        if (!((Mob) (Object) this instanceof Player)) {
            original.call(entity, i, d, d1);
            return;
        }
        Player player = (Player) (Object) this;
        if (PlayerUtil.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.OBSIDIAN) >= 5) {
            return;
        }
        if (PlayerUtil.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.OBSIDIAN) >= 3) {
            original.call(entity, i, d / 2, d1 / 2);
            return;
        }
        original.call(entity, i, d, d1);
    }
}
