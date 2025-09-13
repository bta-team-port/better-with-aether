package teamport.aether;

public interface AetherImmuneMob {
    default boolean canTakeDamageFromSpikes(){
        return true;
    }
}
