package teamport.aether.mixin.effect;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.effect.api.EffectContainer;
import teamport.aether.effect.api.IHasEffects;
import teamport.aether.effect.server.AetherEffectsServer;
import turniplabs.halplibe.helper.EnvironmentHelper;

@Mixin(value = Entity.class, remap = false)
public abstract class EntityEffectMixin implements IHasEffects<Entity> {
    @Unique
    private final EffectContainer<Entity> aether$effects = new EffectContainer<>((Entity) (Object) this);

    @Override
    public EffectContainer<Entity> getContainer() { return aether$effects; }

    @Inject(method = "baseTick", at = @At("TAIL"))
    private void aether$tickEffects(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (entity.world == null) return;
        if (entity.world.isClientSide) {
            aether$effects.tickClientTimers();
            return;
        }

        aether$effects.tick();
        if (aether$effects.consumeDirty() && EnvironmentHelper.isServerEnvironment()) {
            AetherEffectsServer.sync(entity);
        }
    }

    @Inject(method = "saveWithoutId", at = @At("TAIL"))
    private void aether$saveEffects(CompoundTag tag, CallbackInfo ci) { aether$effects.save(tag); }

    @Inject(method = "load", at = @At("TAIL"))
    private void aether$loadEffects(CompoundTag tag, CallbackInfo ci) {
        aether$effects.load(tag, (IHasEffects<Entity>) (Object) this);
    }
}
