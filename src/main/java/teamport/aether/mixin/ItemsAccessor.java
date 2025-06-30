package teamport.aether.mixin;

import net.minecraft.core.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Items.class, remap = false)
public interface ItemsAccessor {
    @Invoker
    static void invokeInitStats() {}
}
