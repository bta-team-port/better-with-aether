package teamport.aether.item.accessory;

public interface AetherStatus {
    void aether$setInvisible(boolean invisible);
    void aether$setSwetFriendly(boolean invisible);

    boolean aether$isInvisible();
    boolean aether$isSwetFriendly();

    // used by the network packet to force invisibility.
    void aether$SyncVisibility(boolean invisible);
}
