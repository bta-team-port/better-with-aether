package teamport.aether.mixin.accessory;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = MenuAbstract.class, remap = false)
public abstract class MenuInventoryMixinAddSlotAdjSlotTwo {
    @WrapMethod(method = "getHotbarSlotId")
    public int getHotbarSlotId(int number, Operation<Integer> original) {
        if ((MenuAbstract) (Object) this instanceof MenuInventory) return 27 + 8 + number;
        return original.call(number);
    }
}
