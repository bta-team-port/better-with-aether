package teamport.aether.mixin.accessors;

import net.minecraft.core.player.inventory.slot.SlotArmor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = SlotArmor.class)
public interface SlotArmorAccessor {
    @Accessor
    int getArmorType();
}
