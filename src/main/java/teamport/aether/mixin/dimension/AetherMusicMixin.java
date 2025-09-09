package teamport.aether.mixin.dimension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.sound.*;
import net.minecraft.core.sound.SoundCategory;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Random;

import static teamport.aether.AetherMod.LOGGER;

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

    @Inject(method = "tick", at= @At(value = "INVOKE", target = "Ljava/util/concurrent/locks/Lock;lock()V", shift = At.Shift.AFTER), cancellable = true)
    public void tick(CallbackInfo ci) {
        if (!(this.isLoaded() && SoundCategoryHelper.getEffectiveVolume(SoundCategory.MUSIC, this.options) != 0.0F))
            return;

        if (this.mc.currentWorld.dimension.id == AetherDimension.AetherDimensionID) {
            ci.cancel();
        }

        if (this.ticksBeforeMusic > 0) {
            --this.ticksBeforeMusic;
            return;
        }

        // okay, firstly I will do horrible hacks to get the soundsystem via reflection.
        // so that we don't have to sort out the dependency mess that would be compiling against paulscode.

        // I feel like i'm writing C 🥲
        Object paulsCode;
        Method backgroundMusic;
        Method setPitch;
        Method setVolume;
        Method play;

       try {
           Field paulsField = SoundEngine.class.getDeclaredField("soundSystem");
           paulsField.setAccessible(true);

           paulsCode = paulsField.get(this);
           backgroundMusic = paulsCode.getClass().getDeclaredMethod("backgroundMusic", String.class, URL.class, String.class, boolean.class);
           setPitch = paulsCode.getClass().getDeclaredMethod("setPitch", String.class, float.class);
           setVolume = paulsCode.getClass().getDeclaredMethod("setVolume", String.class, float.class);
           play = paulsCode.getClass().getDeclaredMethod("play", String.class);
       }

       catch (Exception ignored) {
           LOGGER.error("Failed to commandeer sound system!");
           Thread.dumpStack();
           return;
       }

       try {
           SoundEvent redirect = SoundRepository.SOUNDS.getSoundEvent("aether:music.aether");
           if (redirect == null) return;

           SoundEntry entry = redirect.getRandomEntry();
           if (entry == null) return;

           this.ticksBeforeMusic = this.random.nextInt(6000) + 6000;

           backgroundMusic.invoke(paulsCode, "BgMusic", entry.getURL(), entry.name, false);
           setPitch.invoke(paulsCode, "BgMusic", entry.pitch);
           setVolume.invoke(paulsCode, "BgMusic", SoundCategoryHelper.getEffectiveVolume(SoundCategory.MUSIC, this.options) * entry.volume);
           play.invoke(paulsCode, "BgMusic");
       }

       catch (Exception ignored) {}

    }
}
