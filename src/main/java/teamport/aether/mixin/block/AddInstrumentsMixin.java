package teamport.aether.mixin.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicNote;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.blocks.AetherBlocks;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.core.block.BlockLogicNote.Instrument.CELESTA;
import static teamport.aether.AetherMod.*;

@Mixin(value = BlockLogicNote.Instrument.class, remap = false)
public abstract class AddInstrumentsMixin {

    @Unique
    private static final Map<Block<?>, BlockLogicNote.Instrument> BLOCK_INSTRUMENTS = new HashMap<>();

    static {
        BLOCK_INSTRUMENTS.put(AetherBlocks.AERCLOUD_WHITE, FLUTE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.AERCLOUD_BLUE, FLUTE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.AERCLOUD_GOLD, FLUTE);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_PLANKS_SKYROOT, CLICK);

        BLOCK_INSTRUMENTS.put(AetherBlocks.BLOCK_ZANITE, MUSICBOX);
        BLOCK_INSTRUMENTS.put(AetherBlocks.BRICK_ZANITE, MUSICBOX);
        BLOCK_INSTRUMENTS.put(AetherBlocks.SLAB_BRICK_ZANITE, MUSICBOX);
        BLOCK_INSTRUMENTS.put(AetherBlocks.STAIRS_BRICK_ZANITE, MUSICBOX);

        BLOCK_INSTRUMENTS.put(AetherBlocks.BLOCK_AMBER, SAXOPHONE);

        BLOCK_INSTRUMENTS.put(AetherBlocks.QUICKSOIL, SITAR);
        BLOCK_INSTRUMENTS.put(AetherBlocks.GLASS_QUICKSOIL, SITAR);

        BLOCK_INSTRUMENTS.put(AetherBlocks.BLOCK_GRAVITITE, XYLOPHONE);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_HELLFIRE, ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_HELLFIRE_LIGHT, ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.SLAB_CARVED_HELLFIRE, ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.STAIRS_CARVED_HELLFIRE, ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_HELLFIRE_LOCKED, ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED, ORGAN);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC, BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC_LIGHT, BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.SLAB_CARVED_ANGELIC, BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.STAIRS_CARVED_ANGELIC, BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC_LOCKED, BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED, BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC_TRAPPED, BELL);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE, TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE_LIGHT, TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.SLAB_CARVED_STONE, TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.STAIRS_CARVED_STONE, TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE_LOCKED, TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE_LIGHT_LOCKED, TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE_TRAPPED, TRANCE);

        BLOCK_INSTRUMENTS.put(AetherBlocks.ICESTONE, CELESTA);
    }

    @Inject(method = "getInstrumentFromBlock", at = @At("HEAD"), cancellable = true)
    private static void injectCustomInstruments(Block<?> block, CallbackInfoReturnable<BlockLogicNote.Instrument> cir) {
        BlockLogicNote.Instrument instrument = BLOCK_INSTRUMENTS.get(block);
        if (instrument != null) {
            cir.setReturnValue(instrument);
            cir.cancel();
        }
    }
}
