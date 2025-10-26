package teamport.aether.mixin.accessory.cape.invisibilitycape.player;

import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import teamport.aether.items.accessory.AetherInvisibility;
import teamport.aether.net.message.AetherSyncInvisibilityNetworkMessage;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

@Mixin(value = Player.class, remap = false)
public class PlayerMixinInvisibility implements AetherInvisibility {
    @Unique
    public boolean invisible;

    @Unique
    public void aether$setInvisible(boolean invisible) {
        if (EnvironmentHelper.isClientWorld()) {
            // don't allow the client to have authority over invisibility.
            return;
        };

        this.invisible = invisible;

        if (EnvironmentHelper.isServerEnvironment()) {
            NetworkHandler.sendToAllPlayers(new AetherSyncInvisibilityNetworkMessage(Player.class.cast(this)));
        }
    }

    @Unique
    public boolean aether$isInvisible() {
        return invisible;
    }

    @Override
    public void aether$SyncVisibility(boolean invisible) {
        this.invisible = invisible;
    }

}
