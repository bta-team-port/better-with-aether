package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.piston.BlockLogicPistonBase;
import net.minecraft.core.block.piston.BlockLogicPistonBaseSteel;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.blocks.terrain.BlockLogicOreGravitite;
import teamport.aether.entity.floatingBlock.EntityFloatingBlock;

@Mixin(value = BlockLogicPistonBaseSteel.class, remap = false)
public abstract class BlockLogicPistonBaseSteelMixin extends BlockLogicPistonBase {
    @Shadow
    private Entity flungBlock;

    public BlockLogicPistonBaseSteelMixin(Block<?> block, int maxPushedBlocks) {
        super(block, maxPushedBlocks);
    }

    @Inject(method = "canPushLine", at = @At(value = "FIELD", target = "Lnet/minecraft/core/world/World;isClientSide:Z", shift = At.Shift.AFTER), cancellable = true)
    public void makeFloatingBlock(World world, int x, int y, int z, Direction direction, int maxPushedBlocks, CallbackInfoReturnable<Boolean> cir,
                                  @Local(name = "block") Block<?> block, @Local(name = "blockMeta") int blockMeta, @Local(name = "tileEntity") TileEntity tileEntity) {
        if (block.getLogic() instanceof BlockLogicOreGravitite) {
            int xo = x + direction.getOffsetX();
            int yo = y + direction.getOffsetY();
            int zo = z + direction.getOffsetZ();

            EntityFloatingBlock floatingBlock = new EntityFloatingBlock(world, (double) xo + 0.5, (double) yo + 0.5, (double) zo + 0.5, block.id(), blockMeta, tileEntity);
            floatingBlock.hasRemovedBlock = true;

            world.entityJoinedWorld(floatingBlock);
            double speed = 2.0F;
            floatingBlock.fling(
                    (double) direction.getOffsetX() * speed,
                    (double) direction.getOffsetY() * speed,
                    (double) direction.getOffsetZ() * speed,
                    1.0F
            );

            this.flungBlock = floatingBlock;

            cir.setReturnValue(true);
        }
    }
}
