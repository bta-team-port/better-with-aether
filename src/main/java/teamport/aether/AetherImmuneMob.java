package teamport.aether;

public interface AetherImmuneMob {
    default boolean canTakeDamageFromSpikes(){
        return true;
    }
    default boolean canTakeDamageFromCactus(){return true;}
}
