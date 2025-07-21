package teamport.aether.mixin;

import net.minecraft.client.gui.guidebook.SlotGuidebook;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

@Mixin(value = SlotGuidebook.class, remap = false)
public class SlotGuidebookMixinFix {


    @Inject(method = "showRandomItem", at=@At("HEAD"), cancellable = true)
    public void fixedShowRandomItem(CallbackInfo ci){
        SlotGuidebook slotGuidebook = (SlotGuidebook)(Object)this;
        if (slotGuidebook.symbol != null) {
            Random r = new Random();
            List<ItemStack> list = slotGuidebook.symbol.resolve();
            ItemStack newItem = (ItemStack)list.get(r.nextInt(list.size()));
            if (list.size() > 1) {
                while(newItem == slotGuidebook.item) {
                    newItem = (ItemStack)list.get(r.nextInt(list.size()));
                }
            }
            slotGuidebook.item = newItem;
            ci.cancel();
        }
    }
}
