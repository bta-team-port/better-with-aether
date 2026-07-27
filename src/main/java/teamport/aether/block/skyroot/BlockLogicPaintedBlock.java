package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicPlanksPainted;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;

import java.util.function.Supplier;

public class BlockLogicPaintedBlock extends BlockLogicPlanksPainted implements IPainted {

    private final Supplier<Block<?>> unpaintedVariant;

    public BlockLogicPaintedBlock(Block<?> block, Supplier<Block<?>> unpaintedVariant) {
        super(block);
        this.unpaintedVariant = unpaintedVariant;
    }

    @Override
    public void removeDye(World world, TilePosc pos) {
        Block<?> block = unpaintedVariant.get();
        world.setBlockTypeNotify(pos, block);
    }
}
