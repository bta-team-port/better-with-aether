package teamport.aether.mixins.mixin.accessors;

import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.core.player.inventory.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MenuAbstract.class)
public interface MenuAbstractAccessor {
    @Invoker
    void invokeAddSlot(Slot slot);
}
