package teamport.aether.entity;

public interface AetherMobImmuneToSpikes {
    default boolean canTakeDamageFromSpikes() {
        return true;
    }

    default boolean canTakeDamageFromCactus() {
        return true;
    }
}
