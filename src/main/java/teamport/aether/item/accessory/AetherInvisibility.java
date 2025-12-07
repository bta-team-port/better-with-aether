package teamport.aether.item.accessory;

public interface AetherInvisibility {
    void aether$setInvisible(boolean invisible);

    boolean aether$isInvisible();

    // used by the network packet to force invisibility.
    void aether$SyncVisibility(boolean invisible);
}
