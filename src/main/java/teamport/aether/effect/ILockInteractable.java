package teamport.aether.effect;

import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;

public interface ILockInteractable {
    default void lockTriggered(IHasEffects<?> entity) {
    }
}
