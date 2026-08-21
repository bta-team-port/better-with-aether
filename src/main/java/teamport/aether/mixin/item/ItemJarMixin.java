package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.entity.animal.MobFireflyCluster;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemJar;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.AetherMod;
import teamport.aether.item.AetherItems;

@Mixin(ItemJar.class)
public abstract class ItemJarMixin {
    @WrapMethod(method = "captureFirefly(Lnet/minecraft/core/entity/animal/MobFireflyCluster;)Lnet/minecraft/core/item/Item;")
    private static Item onCaptureFirefly(MobFireflyCluster firefly, Operation<Item> original) {
        if (firefly.getColor() == AetherMod.SILVER) {
            return AetherItems.LANTERN_FIREFLY_SILVER;
        }
        return original.call(firefly);
    }
}
