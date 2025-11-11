package teamport.aether.mixin.dimension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.sound.*;
import net.minecraft.core.sound.SoundCategory;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystem;
import teamport.aether.world.AetherDimension;

import java.util.Random;

@Mixin(value = SoundEngine.class, remap = false)
public abstract class AetherMusicMixin {

    @Shadow
    private Minecraft mc;

    @Shadow
    private @Nullable GameSettings options;

    @Shadow
    protected abstract boolean isLoaded();

    @Shadow
    public int ticksBeforeMusic;

    @Shadow
    @Final
    private Random random;

    @Shadow
    private static @Nullable SoundSystem soundSystem;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci) {

        if (this.mc.currentWorld.dimension.id != AetherDimension.AETHER.id) return;

        ci.cancel();

        if (!this.isLoaded() || soundSystem == null || SoundCategoryHelper.getEffectiveVolume(SoundCategory.MUSIC, this.options) == 0.0F) {
            return;
        }

        if (soundSystem.playing("BgMusic") || soundSystem.playing("Streaming")) return;

        if (this.ticksBeforeMusic > 0) {
            --this.ticksBeforeMusic;
            return;
        }

        SoundEvent redirect = SoundRepository.SOUNDS.getRandomSoundFromCategory("aether:music.");
        if (redirect == null) return;

        SoundEntry entry = redirect.getRandomEntry();
        if (entry == null) return;

        this.ticksBeforeMusic = this.random.nextInt(6000) + 6000;

        soundSystem.backgroundMusic("BgMusic", entry.getURL(), entry.name, false);
        soundSystem.setPitch("BgMusic", entry.pitch);
        soundSystem.setVolume("BgMusic", SoundCategoryHelper.getEffectiveVolume(SoundCategory.MUSIC, this.options) * entry.volume);
        soundSystem.play("BgMusic");
    }
}
