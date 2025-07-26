package teamport.aether.mixin.accessory;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.accessory.api.TickableWhileWorn;

@Mixin(value = Player.class, remap = false)
public class PlayerMixinTickable {

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        Player player = (Player) ((Object) this);
        for (int i = 0; i < player.inventory.armorInventory.length; i++) {
            ItemStack item = player.inventory.armorInventory[i];
            if (item != null) {
                if (item.getItem() instanceof TickableWhileWorn) {
                    ItemStack newItem = ((TickableWhileWorn) item.getItem()).tickWhileWorn(player, item, i);
                    if (newItem != item) {
                        player.inventory.armorInventory[i] = newItem;
                    }
                }
            }
        }
    }
}
