package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.Block;
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
public class BlockLogicPistonBaseSteelMixin extends BlockLogicPistonBase {
    @Shadow
    private Entity flungBlock;

    public BlockLogicPistonBaseSteelMixin(Block<?> block, int maxPushedBlocks) {
        super(block, maxPushedBlocks);
    }

    @Inject(method = "canPushLine", at = @At(value = "FIELD", target = "Lnet/minecraft/core/world/World;isClientSide:Z", shift = At.Shift.AFTER), cancellable = true)
    public void makeFloatingBlock(World world, int x, int y, int z, Direction direction, int maxPushedBlocks, CallbackInfoReturnable<Boolean> cir, @Local(name = "block") Block<?> block) {
        if (block.getLogic() instanceof BlockLogicOreGravitite) {
            int xo = x + direction.getOffsetX();
            int yo = y + direction.getOffsetY();
            int zo = z + direction.getOffsetZ();

            EntityFloatingBlock floatingBlock = new EntityFloatingBlock(world, xo + 0.5F, yo + 0.5F, zo + 0.5F, block.id(), 0, null);

            world.entityJoinedWorld(floatingBlock);
            double speed = 1.0F;
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
