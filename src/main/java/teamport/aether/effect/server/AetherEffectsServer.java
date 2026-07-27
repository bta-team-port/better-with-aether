package teamport.aether.effect.server;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.server.entity.EntityTrackerEntryImpl;
import net.minecraft.server.world.WorldServer;
import teamport.aether.net.message.EffectSyncNetworkMessage;
import turniplabs.halplibe.helper.network.NetworkHandler;

@Environment(EnvType.SERVER)
public final class AetherEffectsServer {
    private AetherEffectsServer() { }

    public static void sync(Entity entity) {
        if (entity.world == null || entity.world.isClientSide) return;

        EffectSyncNetworkMessage message = new EffectSyncNetworkMessage(entity);
        if (entity.world instanceof WorldServer) {
            WorldServer world = (WorldServer) entity.world;
            EntityTrackerEntryImpl entry = world.mcServer.getEntityTracker(world.dimension.id).trackedEntityHashTable.get(entity.id);
            if (entry != null) {
                NetworkHandler.sendToPlayers(entry.trackedPlayers, message);
                if (entity instanceof Player) NetworkHandler.sendToPlayer((Player) entity, message);
                return;
            }
        }

        NetworkHandler.sendToAllAround(entity.x, entity.y, entity.z, 32.0D, entity.world.dimension.id, message);
    }
}
