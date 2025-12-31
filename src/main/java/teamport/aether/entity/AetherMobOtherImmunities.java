package teamport.aether.entity;

public interface AetherMobOtherImmunities {
    default boolean canTakeDamageFromSpikes() {
        return false;
    }

    default boolean canTakeDamageFromCactus() {
        return false;
    }
}
