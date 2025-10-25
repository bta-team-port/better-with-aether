package teamport.aether.mixin.block;

import net.minecraft.core.block.BlockLogicNote;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.helper.ParticleMaker;

import static teamport.aether.AetherMod.*;

@Mixin(value = BlockLogicNote.class, remap = false)
public abstract class BlockLogicNoteTriggerMixin {
    @Inject(method = "triggerEvent", at = @At("HEAD"), cancellable = true)
    public void triggerEvent(World world, int x, int y, int z, int index, int data, CallbackInfo ci) {
        float f = (float) Math.pow(2.0, (double) (data - 12) / 12.0);
        String soundKey = BlockLogicNote.Instrument.getInstrumentFromIndex(index).soundKey;
        String soundEvent;

        if (index == FLUTE.index || index == CLICK.index || index == XYLOPHONE.index || index == BELL.index || index == TRUMPET.index
                || index == ORGAN.index || index == SITAR.index || index == TRANCE.index || index == SAXOPHONE.index || index == MUSICBOX.index) {
            soundEvent = "aether:note." + soundKey;
        } else {
            soundEvent = "note." + soundKey;
        }

        world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, (double) x + 0.5, (double) y + 0.5, (double) z + 0.5, soundEvent, 3.0F, f);
        ParticleMaker.spawnParticle(world, "note", (double) x + 0.5, (double) y + 1.2, (double) z + 0.5, 0.0, 0.0, 0.0, data);
        ci.cancel();
    }
}
