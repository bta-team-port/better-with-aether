package teamport.aether.mixin.dimension;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicFire;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.world.AetherDimension;

@Mixin(value = BlockLogicFire.class, remap = false)
public abstract class BlockLogicFireMixin extends BlockLogic {

    public BlockLogicFireMixin(Block<?> block, Material material) {
        super(block, material);
    }

    @Override
    public void onBlockPlacedByWorld(World world, int x, int y, int z) {
        if (world.dimension == AetherDimension.AETHER) {
            world.setBlock(x, y, z, 0);
            return;
        }

        super.onBlockPlacedByWorld(world, x, y, z);
    }
}
