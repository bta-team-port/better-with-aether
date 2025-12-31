package teamport.aether.mixin.accessors;

import net.minecraft.core.player.inventory.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Slot.class, remap = false)
public interface SlotAccessor {
    @Accessor
    int getSlot();
}
