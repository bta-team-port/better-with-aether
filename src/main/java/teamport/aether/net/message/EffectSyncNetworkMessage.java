package teamport.aether.net.message;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import org.jspecify.annotations.NonNull;
import teamport.aether.effect.api.IHasEffects;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

public class EffectSyncNetworkMessage implements NetworkMessage {
    private int entityId;
    private CompoundTag effects;

    public EffectSyncNetworkMessage() { }

    public EffectSyncNetworkMessage(Entity entity) {
        this.entityId = entity.id;
        this.effects = new CompoundTag();
        ((IHasEffects<?>) entity).getContainer().save(this.effects);
    }

    @Override
    public void encodeToUniversalPacket(@NonNull UniversalPacket packet) {
        packet.writeInt(entityId);
        packet.writeCompoundTag(effects == null ? new CompoundTag() : effects);
    }

    @Override
    public void decodeFromUniversalPacket(@NonNull UniversalPacket packet) {
        entityId = packet.readInt();
        effects = packet.readCompoundTag();
    }

    @Override
    public void handleClientEnv(NetworkContext context) {
        if (effects == null || context.player == null || context.player.world == null) return;
        Entity entity = context.player.id == entityId
            ? context.player
            : context.player.world.getEntityByID(entityId);
        if (entity instanceof IHasEffects) {
            IHasEffects<?> hasEffects = (IHasEffects<?>) entity;
            hasEffects.getContainer().load(effects, hasEffects);
        }
    }
}
