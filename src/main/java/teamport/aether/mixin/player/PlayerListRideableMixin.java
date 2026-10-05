package teamport.aether.mixin.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.entity.Entity;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import teamport.aether.entity.animal.MobAetherAnimalRideable;

@Environment(EnvType.SERVER)
@Mixin(PlayerList.class)
public abstract class PlayerListRideableMixin {

    @ModifyVariable(method = "sendPlayerToOtherDimension", at = @At(value = "STORE"), name = "mount")
    private Entity keepAetherMountOnDimensionChange(Entity mount, PlayerServer playerServer) {
        if (mount == null && playerServer.vehicle instanceof MobAetherAnimalRideable) {
            return (Entity) playerServer.vehicle;
        }
        return mount;
    }

    @ModifyVariable(method = "playerLoggedOut", at = @At(value = "STORE"), name = "mount")
    private Entity keepAetherMountOnLogout(Entity mount, PlayerServer entityplayermp) {
        if (mount == null && entityplayermp.vehicle instanceof MobAetherAnimalRideable) {
            return (Entity) entityplayermp.vehicle;
        }
        return mount;
    }
}
