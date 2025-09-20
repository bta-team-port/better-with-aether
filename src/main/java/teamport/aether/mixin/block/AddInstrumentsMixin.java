package teamport.aether.mixin.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicNote;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static teamport.aether.AetherMod.*;

@Mixin(value = BlockLogicNote.Instrument.class, remap = false)
public abstract class AddInstrumentsMixin {

    @Inject(method = "getInstrumentFromBlock", at = @At("HEAD"), cancellable = true)
    private static void injectCustomInstruments(Block<?> block, CallbackInfoReturnable<BlockLogicNote.Instrument> cir) {
        BlockLogicNote.Instrument instrument = BLOCK_INSTRUMENTS.get(block.id());
        if (instrument != null) {
            cir.setReturnValue(instrument);
            cir.cancel();
        }
    }
}
