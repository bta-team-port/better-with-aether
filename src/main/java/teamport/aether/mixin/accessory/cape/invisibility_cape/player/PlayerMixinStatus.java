package teamport.aether.mixin.accessory.cape.invisibility_cape.player;

import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import teamport.aether.item.accessory.AetherStatus;
import teamport.aether.net.message.AetherSyncInvisibilityNetworkMessage;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixinStatus implements AetherStatus {
    @Unique
    private boolean invisible;

    @Unique
    private boolean swetFriendly;

    @Override
    public void aether$setInvisible(boolean invisible) {
        // don't allow the client to have authority over invisibility.
        if (EnvironmentHelper.isClientWorld()) return;
        this.invisible = invisible;

        if (EnvironmentHelper.isServerEnvironment()) {
            NetworkHandler.sendToAllPlayers(new AetherSyncInvisibilityNetworkMessage((Player) (Object) this));
        }
    }

    @Override
    public void aether$setSwetFriendly(boolean friendly) {
        this.swetFriendly = friendly;
    }


    @Override
    public boolean aether$isInvisible() {
        return invisible;
    }

    @Override
    public boolean aether$isSwetFriendly() {
        return swetFriendly;
    }

    @Override
    public void aether$SyncVisibility(boolean invisible) {
        this.invisible = invisible;
    }
}
