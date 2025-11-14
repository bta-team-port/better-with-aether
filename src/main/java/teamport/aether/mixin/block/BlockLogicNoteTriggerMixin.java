package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.block.BlockLogicNote;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static teamport.aether.AetherMod.*;

@Mixin(value = BlockLogicNote.class, remap = false)
public abstract class BlockLogicNoteTriggerMixin {
    @Expression("'note.'")
    @ModifyExpressionValue(method = "triggerEvent", at = @At("MIXINEXTRAS:EXPRESSION"))
    public String triggerEvent(String original, World world, int x, int y, int z, int index, int data) {
        if (index == FLUTE.index || index == CLICK.index || index == XYLOPHONE.index || index == BELL.index || index == TRUMPET.index
                || index == ORGAN.index || index == SITAR.index || index == TRANCE.index || index == SAXOPHONE.index || index == MUSICBOX.index) {
            return "aether:note.";
        }
        return original;
    }
}
