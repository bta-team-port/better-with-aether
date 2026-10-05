package teamport.aether.mixin.player;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.entity.animal.MobPig;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.PlayerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.animal.MobAetherAnimalRideable;

@Environment(EnvType.SERVER)
@Mixin(PlayerServer.class)
public abstract class PlayerServerRideableMixin extends Player {

    protected PlayerServerRideableMixin(World world) {
        super(world);
    }

    @Definition(id = "MobPig", type = MobPig.class)
    @Expression("? instanceof MobPig")
    @ModifyExpressionValue(method = "onLivingUpdate", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean allowPortalTravelForAetherMounts(boolean original) {
        return original || this.vehicle instanceof MobAetherAnimalRideable;
    }
}
