package teamport.aether.mixin;

import net.minecraft.core.block.BlockLogicPathDirt;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.blocks.AetherBlocks;

@Mixin(value = BlockLogicPathDirt.class, remap = false)
public class BlockLogicPathDirtMixin {
    @Inject(method = "onNeighborBlockChange", at = @At(value = "HEAD"), cancellable = true)
    public void addNewPathBlock(World world, int x, int y, int z, int blockId, CallbackInfo ci){
        Material material = world.getBlockMaterial(x, y + 1, z);
        int id = world.getBlockId(x, y + 1, z);
        if (material.isSolid() && id != AetherBlocks.FENCEGATE_PLANKS_SKYROOT.id()) {
            world.setBlockWithNotify(x, y, z, Blocks.DIRT.id());
        }
        ci.cancel();
    }
}
