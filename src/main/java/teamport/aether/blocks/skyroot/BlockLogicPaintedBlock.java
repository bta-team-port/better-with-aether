package teamport.aether.blocks.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicPlanksPainted;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;

import java.util.function.Supplier;

public class BlockLogicPaintedBlock extends BlockLogicPlanksPainted implements IPainted {

    private final Supplier<Block<?>> unpaintedVariant;

    public BlockLogicPaintedBlock(Block<?> block, Material material, Supplier<Block<?>> unpaintedVariant) {
        super(block);
        this.unpaintedVariant = unpaintedVariant;
    }

    public void removeDye(World world, int x, int y, int z) {
        Block<?> block = unpaintedVariant.get();
        world.setBlockWithNotify(x, y, z, block.id());
    }

    public void setColor(World world, int x, int y, int z, DyeColor color) {
        super.setColor(world, x, y, z, color);
    }
}
