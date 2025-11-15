package teamport.aether.mixin.accessory.trinket;

import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import teamport.aether.items.AetherRepulsion;
import teamport.aether.net.message.AetherSyncRepulsionNetworkMessage;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixinRepulsion implements AetherRepulsion {
    @Unique
    private boolean repulsion;
    @Override
    public void aether$setRepulsion(boolean repulsion) {
        if (EnvironmentHelper.isClientWorld()) {
            // don't allow the client to have authority over repulsion.
            return;
        }
        this.repulsion = repulsion;
        if (EnvironmentHelper.isServerEnvironment()) {
            NetworkHandler.sendToAllPlayers(new AetherSyncRepulsionNetworkMessage((Player) (Object) this));
        }
    }
    @Override
    public boolean aether$isRepulse() {
        return repulsion;
    }
    @Override
    public void aether$SyncRepulsion(boolean repulsion) {
        this.repulsion = repulsion;
    }
}
