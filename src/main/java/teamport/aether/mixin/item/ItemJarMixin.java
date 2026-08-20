package teamport.aether.mixin.item;

import net.minecraft.core.entity.animal.MobFireflyCluster;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemJar;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.AetherMod;
import teamport.aether.item.AetherItems;

@Mixin(ItemJar.class)
public abstract class ItemJarMixin {
    @Inject(method = "captureFirefly(Lnet/minecraft/core/entity/animal/MobFireflyCluster;)Lnet/minecraft/core/item/Item;", at = @At("HEAD"), cancellable = true)
    private static void onCaptureFirefly(@NonNull MobFireflyCluster firefly, CallbackInfoReturnable<Item> cir) {
        if (firefly.getColor() == AetherMod.SILVER) {
            cir.setReturnValue(AetherItems.LANTERN_FIREFLY_SILVER);
        }
    }
}
