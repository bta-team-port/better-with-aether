package teamport.aether.effect;

import teamport.aether.effect.api.IHasEffects;

public interface ILockInteractable {
    default void lockTriggered(IHasEffects<?> entity) {
    }
}
