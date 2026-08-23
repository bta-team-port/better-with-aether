package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.block.BlockLogicFluid;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.block.AetherBlocks;
import teamport.aether.world.AetherDimension;

@Mixin(ItemBucket.class)
public abstract class LavaBucketMixin {

    @Unique
    private static final ItemBucket.BucketState AEROGEL_STATE = new ItemBucket.BucketState("aerogel", false, 0, 0, AetherBlocks.AEROGEL, null, true, "fire.ignite", BlockLogicFluid::fizz, null);

    @WrapOperation(method = "tryPlaceFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemBucket;getBucketState(Lnet/minecraft/core/util/collection/NamespaceID;)Lnet/minecraft/core/item/ItemBucket$BucketState;"))
    private ItemBucket.BucketState redirectLavaStateToAerogel(NamespaceID id, Operation<ItemBucket.BucketState> original, ItemStack itemStack, @NonNull World world) {
        if (world.getDimension() == AetherDimension.getAether() && ItemBucket.STATE_LAVA.equals(id)) {
            return AEROGEL_STATE;
        }
        return original.call(id);
    }

    @WrapOperation(method = "onUseByActivator", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemBucket;getBucketState(Lnet/minecraft/core/util/collection/NamespaceID;)Lnet/minecraft/core/item/ItemBucket$BucketState;"))
    private ItemBucket.BucketState redirectActivatorLavaStateToAerogel(NamespaceID id, Operation<ItemBucket.BucketState> original, ItemStack selfStack, @NonNull World world) {
        if (world.getDimension() == AetherDimension.getAether() && ItemBucket.STATE_LAVA.equals(id)) {
            return AEROGEL_STATE;
        }
        return original.call(id);
    }
}
