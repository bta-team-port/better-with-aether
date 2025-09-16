package teamport.aether.entity;

public interface AetherImmuneMob {
    default boolean canTakeDamageFromSpikes(){
        return true;
    }
    default boolean canTakeDamageFromCactus(){return true;}
}
