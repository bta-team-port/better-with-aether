package teamport.aether.entity.interfaces;

public interface AetherMobOtherImmunities {
    default boolean canTakeDamageFromSpikes() {
        return false;
    }

    default boolean canTakeDamageFromCactus() {
        return false;
    }
}
