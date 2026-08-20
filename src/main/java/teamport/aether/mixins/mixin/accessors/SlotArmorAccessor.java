package teamport.aether.mixins.mixin.accessors;

import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.player.inventory.slot.SlotArmor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SlotArmor.class)
public interface SlotArmorAccessor {
    @Accessor
    HumanArmorShape getArmorShape();
}
