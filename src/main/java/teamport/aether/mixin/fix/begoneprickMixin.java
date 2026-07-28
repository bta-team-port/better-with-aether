package teamport.aether.mixin.fix;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(value = Player.class)
public abstract class begoneprickMixin {

    @Shadow
    public UUID uuid;

    @Shadow
    public abstract boolean hurt(Entity attacker, int damage, DamageType type);

    @Unique
    private static final UUID UUID_HOBBLE = UUID.fromString("18fb3279-ce41-40ca-be5c-6017f384f22f");

    /**
     * @reason
     * This is here because this guy is an absolute prick.
     * I do not wish to support them or even see them for that matter.
     * I've ever so kindly (to my sanity) unilateraly decide remove them from playing with this mod.
     */

    @Inject(method = "tick", at = @At("TAIL"))
    private void hobble(CallbackInfo ci) {
        if (uuid.equals(UUID_HOBBLE)) {
            final var thisAs = ((Player) (Object) this);

            this.hurt(thisAs, Integer.MAX_VALUE, DamageType.FIRE);
            thisAs.setHealthRaw(-1);
            thisAs.onDeath(thisAs);
        }
    }
}
