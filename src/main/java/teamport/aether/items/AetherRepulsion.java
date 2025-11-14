package teamport.aether.items;

public interface AetherRepulsion {
    void aether$setRepulsion(boolean repulsion);

    boolean aether$isrepulse();

    // used by the network packet to force invisibility.
    void aether$SyncRepulsion(boolean repulsion);
}
