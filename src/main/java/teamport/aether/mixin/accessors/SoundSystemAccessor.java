package teamport.aether.mixin.accessors;

import net.minecraft.client.sound.SoundEngine;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import paulscode.sound.SoundSystem;

@Mixin(value = SoundEngine.class, remap = false)
public interface SoundSystemAccessor {
    @Accessor
    SoundSystem getSoundSystem();
}
