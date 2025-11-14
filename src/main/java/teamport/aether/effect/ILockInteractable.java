package teamport.aether.effect;

import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;


/**
 * @implNote Implement this interface if you want your effect to trigger
 * it denise the application of specific effects
 */
public interface ILockInteractable {
    default void lockTriggered(IHasEffects<?> entity) {
    }
}
