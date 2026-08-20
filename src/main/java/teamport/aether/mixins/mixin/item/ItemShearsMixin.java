package teamport.aether.mixins.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.entity.animal.MobSheep;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolShears;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.joml.primitives.AABBdc;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.animal.sheepuff.MobSheepuff;

import java.util.List;
import java.util.Random;

@Mixin(ItemToolShears.class)
public abstract class ItemShearsMixin {

    @WrapOperation(method = "onUseByActivator(Lnet/minecraft/core/item/ItemStack;Lnet/minecraft/core/world/World;Lnet/minecraft/core/block/entity/TileEntityActivator;Ljava/util/Random;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/util/helper/Direction;DDD)V", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"))
    private boolean callOnUseByActivator(List<MobSheep> instance, @NonNull Operation<Boolean> original, ItemStack selfStack, World world, TileEntityActivator activator, Random random, TilePosc blockPos, Direction direction, double offX, double offY, double offZ, @Local(name = "box") AABBdc box) {
        if (Boolean.FALSE.equals(original.call(instance))) return false;
        List<MobSheepuff> entities = world.getEntitiesWithinAABB(MobSheepuff.class, box);
        if (!entities.isEmpty()) entities.get(0).onItemInteract(selfStack);
        return true;
    }
}
