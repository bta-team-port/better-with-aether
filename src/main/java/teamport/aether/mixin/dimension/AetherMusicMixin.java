package teamport.aether.mixin.dimension;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sound.SoundEngine;
import net.minecraft.client.sound.SoundEvent;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.world.AetherDimension;

@Environment(EnvType.CLIENT)
@Mixin(value = SoundEngine.class)
public abstract class AetherMusicMixin {
    @Shadow
    private Minecraft mc;
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;canBlockSeeTheSky(III)Z"))
    private boolean tickOne(World instance, int x, int y, int z, Operation<Boolean> original) {
        if (this.mc.currentWorld.dimension.id != AetherDimension.getAether().id) return original.call(instance, x, y, z);
        return true;
    }
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundRepository;getRandomSoundFromCategory(Ljava/lang/String;)Lnet/minecraft/client/sound/SoundEvent;"))
    private SoundEvent tickTwo(SoundRepository instance, String s, Operation<SoundEvent> original) {
        if (this.mc.currentWorld.dimension.id != AetherDimension.getAether().id) return original.call(instance, s);
        return original.call(instance, "aether_music.");
    }
}
