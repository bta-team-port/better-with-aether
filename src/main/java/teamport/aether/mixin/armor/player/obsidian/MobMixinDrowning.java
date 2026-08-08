package teamport.aether.mixin.armor.player.obsidian;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherArmorMaterial;

@Mixin(value = Mob.class, remap = false)
public abstract class MobMixinDrowning extends Entity {
    protected MobMixinDrowning(@Nullable World world) {
        super(world);
    }
    @Inject(method = "moveEntityWithHeading", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Mob;horizontalCollision:Z", ordinal = 0, opcode = Opcodes.GETFIELD))
    private void aether$changeGravity(float moveStrafing, float moveForward, CallbackInfo ci) {
        if (!((Mob) (Object) this instanceof Player)) {
            return;
        }
        Player player = (Player) (Object) this;
        int count = PlayerUtil.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.OBSIDIAN);
        yd -= 0.004 * count;
    }
}
