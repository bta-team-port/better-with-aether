package teamport.aether.mixins.mixin;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.menu.MenuInventoryCreative;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherCreativeContents;

import java.util.List;

@Mixin(MenuInventoryCreative.class)
public abstract class SortCreativeInventoryMixin {

    @Shadow
    public static List<ItemStack> creativeContents;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void rebuildCreativeList(CallbackInfo ci) {
        creativeContents.clear();
        AetherCreativeContents.populate(creativeContents);
    }


}
