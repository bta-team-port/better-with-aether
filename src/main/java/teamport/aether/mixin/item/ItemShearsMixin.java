package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.entity.animal.MobSheep;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolShears;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.animal.sheepuff.MobSheepuff;

import java.util.List;
import java.util.Random;

@Mixin(value = ItemToolShears.class, remap = false)
public abstract class ItemShearsMixin {

    @WrapOperation(method = "onUseByActivator", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"))
    private boolean callOnUseByActivator(List<MobSheep> instance, Operation<Boolean> original, ItemStack itemStack, TileEntityActivator activatorBlock, World world, Random random, int blockX, int blockY, int blockZ, double offX, double offY, double offZ, Direction direction, @Local(name = "box") AABB box) {
        if (Boolean.FALSE.equals(original.call(instance))) return false;
        List<MobSheepuff> entities = world.getEntitiesWithinAABB(MobSheepuff.class, box);
        if (!entities.isEmpty()) entities.get(0).onItemInteract(itemStack);
        return true;
    }
}
