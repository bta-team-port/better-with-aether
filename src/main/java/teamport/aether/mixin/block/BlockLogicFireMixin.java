package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicFire;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.world.AetherDimension;

@Mixin(value = BlockLogicFire.class, remap = false)
public abstract class BlockLogicFireMixin extends BlockLogic {
    protected BlockLogicFireMixin(Block<?> block, Material material) {
        super(block, material);
    }

    @WrapMethod(method = "onPlacedByWorld")
    private void onPlacedByWorld(World world, TilePosc pos, Operation<Void> original) {
        if (world.dimension == AetherDimension.getAether()) {
            Block<?> below = world.getBlockType(pos.down(new TilePos()));
            boolean infiniteBurn = below != null && below.hasTag(BlockTags.INFINITE_BURN);

            if (!infiniteBurn) {
                world.setBlockType(pos, Blocks.AIR);
                return;
            }
        }

        original.call(world, pos);
    }
}
