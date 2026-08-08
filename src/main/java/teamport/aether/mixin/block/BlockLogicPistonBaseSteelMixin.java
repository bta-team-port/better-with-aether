package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.piston.BlockLogicPistonBase;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityFallingBlock;
import net.minecraft.core.world.IVehicle;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.block.terrain.BlockLogicOreGravitite;
import teamport.aether.entity.floating_block.EntityFloatingBlock;

@Mixin(value = BlockLogicPistonBase.class, remap = false)
public abstract class BlockLogicPistonBaseSteelMixin {
    @WrapOperation(method = "flingBlock(Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/util/helper/Direction;)V", at = @At(value = "NEW", target = "(Lnet/minecraft/core/world/World;DDDIILnet/minecraft/core/block/entity/TileEntity;)Lnet/minecraft/core/entity/EntityFallingBlock;"))
    private EntityFallingBlock makeFloatingBlockOne(World world, double x, double y, double z, int blockId, int blockMeta, TileEntity tileEntity, Operation<EntityFallingBlock> original, @Local Block<?> block, @Share("entityFloatingBlock") LocalRef<EntityFloatingBlock> entityFloatingBlock) {
        if (!(block.getLogic() instanceof BlockLogicOreGravitite)) return original.call(world, x, y, z, blockId, blockMeta, tileEntity);
        EntityFloatingBlock floatingBlock = new EntityFloatingBlock(world, x, y, z, blockId, blockMeta, tileEntity);
        floatingBlock.setHasRemovedBlock(true);
        entityFloatingBlock.set(floatingBlock);
        return original.call(world, x, y, z, blockId, blockMeta, tileEntity);
    }
    @WrapOperation(method = "flingBlock(Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/util/helper/Direction;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Entity;startRiding(Lnet/minecraft/core/world/IVehicle;)V"))
    private void makeFloatingBlockTwo(Entity instance, IVehicle vehicle, Operation<Void> original, @Share("entityFloatingBlock") LocalRef<EntityFloatingBlock> entityFloatingBlock) {
        EntityFloatingBlock floatingBlock = entityFloatingBlock.get();
        if (floatingBlock != null) {
            original.call(instance, floatingBlock);
            return;
        }
        original.call(instance, vehicle);
    }
    @WrapOperation(method = "flingBlock(Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/util/helper/Direction;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;entityJoinedWorld(Lnet/minecraft/core/entity/Entity;)Z"))
    private boolean makeFloatingBlockThree(World instance, Entity entity, Operation<Boolean> original, @Share("entityFloatingBlock") LocalRef<EntityFloatingBlock> entityFloatingBlock) {
        EntityFloatingBlock floatingBlock = entityFloatingBlock.get();
        if (floatingBlock != null) return original.call(instance, floatingBlock);
        return original.call(instance, entity);
    }
    @WrapOperation(method = "flingBlock(Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/util/helper/Direction;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/EntityFallingBlock;fling(DDDF)V"))
    private void makeFloatingBlockFour(EntityFallingBlock instance, double xd, double yd, double zd, float pushTime, Operation<Void> original, @Share("entityFloatingBlock") LocalRef<EntityFloatingBlock> entityFloatingBlock) {
        EntityFloatingBlock floatingBlock = entityFloatingBlock.get();
        if (floatingBlock != null) {
            floatingBlock.fling(xd, yd, zd, pushTime);
            return;
        }
        original.call(instance, xd, yd, zd, pushTime);
    }
}
