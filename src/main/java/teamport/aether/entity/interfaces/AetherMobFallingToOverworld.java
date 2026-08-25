package teamport.aether.entity.interfaces;

public interface AetherMobFallingToOverworld {

    default boolean canFallToOverworld() { return true; }
    default void onEnteredOverworld() {}
    default void onLeavingAether() {}
}
