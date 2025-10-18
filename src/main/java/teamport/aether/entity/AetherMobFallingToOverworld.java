package teamport.aether.entity;

public interface AetherMobFallingToOverworld {

    default boolean canFallToOverworld() { return true; }
    default void onEnteredOverworld() {}
    default void onLeavingAether() {}
}
