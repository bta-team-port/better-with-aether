package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicNote;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static teamport.aether.AetherMod.BLOCK_INSTRUMENTS;

@Mixin(value = BlockLogicNote.Instrument.class)
public abstract class AddInstrumentsMixin {
    @ModifyReturnValue(method = "getInstrumentFromBlock", at = @At("RETURN"))
    private static BlockLogicNote.@NonNull Instrument injectCustomInstruments(BlockLogicNote.@NonNull Instrument original, @Nullable Block<?> block) {
        if (block == null) return original;
        BlockLogicNote.Instrument instrument = BLOCK_INSTRUMENTS.get(block.id());
        if (instrument != null) return instrument;
        return original;
    }
}
