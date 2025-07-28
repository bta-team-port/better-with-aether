package teamport.aether.mixin.accessors;

import net.minecraft.client.util.helper.ItemDragHandler;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ItemDragHandler.class, remap = false)
public interface ItemDragHandlerAccessor {
    @Accessor("lastClickSlot")
    Slot getLastClickedSlot();

    @Accessor("draggingItemStack")
    ItemStack getDraggingItemStack();
}
