package teamport.aether.mixin.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import teamport.aether.world.AetherDimension;

@Mixin(value = EntityLightning.class, remap = false)
public abstract class LightningBoltSoundMixin extends Entity {

    protected LightningBoltSoundMixin(@Nullable World world) {
        super(world);
    }

    @ModifyArg(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;playSoundEffect(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/sound/SoundCategory;DDDLjava/lang/String;FF)V", ordinal = 0), index = 6)
    private float quietThunder(float volume) {
        if (this.world.dimension == AetherDimension.getAether()) {
            return volume * 0.001F;
        }
        return volume;
    }
}
