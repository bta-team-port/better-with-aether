package teamport.aether.entity;

public interface AetherMobImmuneToSpikes {
    default boolean canTakeDamageFromSpikes() {
        return false;
    }

    default boolean canTakeDamageFromCactus() {
        return false;
    }
}
