package teamport.aether.mixin.armor.player.obsidian;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.entity.player.ContainerHelper;
import teamport.aether.item.AetherArmorMaterial;

@Mixin(value = Entity.class, remap = false)
public abstract class PlayerMixinNoKnockbackFling {
    @WrapMethod(method = "fling")
    private void fling(double xd, double yd, double zd, float pushTime, Operation<Void> original) {
        if (!((Entity) (Object) this instanceof Player)) {
            original.call(xd, yd, zd, pushTime);
            return;
        }
        Player player = (Player) (Object) this;
        if (ContainerHelper.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.OBSIDIAN) >= 5) {
            return;
        }
        if (ContainerHelper.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.OBSIDIAN) >= 3) {
            original.call(xd / 4, yd / 4, zd / 4, pushTime / 4);
            return;
        }
        original.call(xd, yd, zd, pushTime);
    }
}
